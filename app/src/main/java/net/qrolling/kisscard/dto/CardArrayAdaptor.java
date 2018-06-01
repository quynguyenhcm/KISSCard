package net.qrolling.kisscard.dto;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.viewmodel.CardListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 22/05/18.
 */
public class CardArrayAdaptor extends RecyclerView.Adapter<CardArrayAdaptor.WordViewHolder> {
    private final LayoutInflater mInflater;
    private List<KissCard> mCards; // Cached copy of card
    private List<KissCard> itemsPendingRemoval;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<KissCard, Runnable> pendingRunnables = new HashMap<>();

    public CardArrayAdaptor(Context context) {
        mInflater = LayoutInflater.from(context);
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_row, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        KissCard current;
        if (mCards != null) {
            current = mCards.get(position);

            if (itemsPendingRemoval.contains(current)) {
                /** {show swipe layout} and {hide regular layout} */
                holder.regularLayout.setVisibility(View.GONE);
                holder.swipeLayout.setVisibility(View.VISIBLE);
                holder.undo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoOpt(current);
                    }
                });
            } else {
                /** {show regular layout} and {hide swipe layout} */
                holder.regularLayout.setVisibility(View.VISIBLE);
                holder.swipeLayout.setVisibility(View.GONE);
                holder.term.setText(current.getTerm());
                holder.defintion.setText(current.getDefinition());
                holder.id.setText(String.valueOf(current.getId()));
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.term.setText(R.string.no_word);
        }
    }

    private void undoOpt(KissCard card) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(card);
        pendingRunnables.remove(card);
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(card);
        // this will rebind the row in "normal" state
        notifyItemChanged(mCards.indexOf(card));
    }

    public void pendingRemoval(int position, CardListViewModel mCardViewModel) {

        final KissCard data = mCards.get(position);
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = () -> remove(mCards.indexOf(data), mCardViewModel);
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(data, pendingRemovalRunnable);
        }
    }

    public void remove(int position, CardListViewModel mCardViewModel) {
        KissCard data = mCards.get(position);
        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data);
        }
        if (mCards.contains(data)) {
            mCards.remove(position);
            notifyItemRemoved(position);
        }
        mCardViewModel.deleteCard(data);
        notifyItemChanged(position);
    }

    public boolean isPendingRemoval(int position) {
        KissCard data = mCards.get(position);
        return itemsPendingRemoval.contains(data);
    }

    /**
     * Associate a list of words with this adapter
     */

    public void setCards(List<KissCard> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCards has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCards != null)
            return mCards.size();
        else return 0;
    }

    /**
     * Get the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position
     * @return The word at the given position
     */
    public KissCard getCardAtPosition(int position) {
        return mCards.get(position);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.card_id)
        TextView id;

        //        @BindView(R.id.card_term)
        TextView term;

        //        @BindView(R.id.card_definition)
        TextView defintion;

        public LinearLayout regularLayout;
        public LinearLayout swipeLayout;
        public TextView listItem;
        public TextView undo;

        private WordViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.card_id);
            term = itemView.findViewById(R.id.card_term);
            defintion = itemView.findViewById(R.id.card_definition);
            regularLayout = itemView.findViewById(R.id.regularLayout);
            listItem = itemView.findViewById(R.id.list_item);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            undo = itemView.findViewById(R.id.undo);
        }
    }
}
