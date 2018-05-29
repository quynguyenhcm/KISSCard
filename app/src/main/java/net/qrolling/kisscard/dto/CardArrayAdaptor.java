package net.qrolling.kisscard.dto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qrolling.kisscard.R;

import java.util.List;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 22/05/18.
 */
public class CardArrayAdaptor extends RecyclerView.Adapter<CardArrayAdaptor.WordViewHolder> {
    private final LayoutInflater mInflater;
    private List<KissCard> mCards; // Cached copy of card

    public CardArrayAdaptor(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_row, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mCards != null) {
            KissCard current = mCards.get(position);
            holder.term.setText(current.getTerm());
            holder.defintion.setText(current.getDefinition());
            holder.id.setText(String.valueOf(current.getId()));
        } else {
            // Covers the case of data not being ready yet.
            holder.term.setText(R.string.no_word);
        }
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
        private final TextView id;

        //        @BindView(R.id.card_term)
        private final TextView term;

        //        @BindView(R.id.card_definition)
        private final TextView defintion;


        private WordViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.card_id);
            term = itemView.findViewById(R.id.card_term);
            defintion = itemView.findViewById(R.id.card_definition);
        }
    }
}
