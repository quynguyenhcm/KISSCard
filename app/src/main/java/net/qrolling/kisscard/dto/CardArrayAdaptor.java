package net.qrolling.kisscard.dto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.qrolling.kisscard.R;

import java.util.ArrayList;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 22/05/18.
 */
public class CardArrayAdaptor extends ArrayAdapter<KissCard> {
    private Context mContext;

//    private List<KissCard> kissCards;

    public CardArrayAdaptor(Context context, ArrayList<KissCard> cards) {
        super(context, 0, cards);
        mContext = context;
//        kissCards = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        // Get the data item for this position
        KissCard card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (listItem == null) {
            viewHolder = new ViewHolder();
            listItem = LayoutInflater.from(mContext).inflate(R.layout.card_row, parent, false);
            viewHolder.id = (TextView) listItem.findViewById(R.id.atermId);
            viewHolder.definition = (TextView) listItem.findViewById(R.id.adefinition);
            viewHolder.term = (TextView) listItem.findViewById(R.id.aterm);
            // Cache the viewHolder object inside the fresh view
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItem.getTag();
        }

        viewHolder.id.setText(Integer.toString(card.getID()));
        viewHolder.term.setText(card.getTerm());
        viewHolder.term.setTag(position);
        viewHolder.definition.setText(card.getDefinition());
//        term.setTag(card.getID(), card);
        // Attach the click event handler
        viewHolder.term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                KissCard kissCard = getItem(position);
               
            }
        });
        // Return the completed view to render on screen
        return listItem;
    } // View lookup cache

    private static class ViewHolder {
        TextView id;
        TextView term;
        TextView definition;
    }

    private void populateKissCardToIntent(Cursor kissCard, Intent intent) {
        intent.putExtra("id", kissCard.getInt(0));
        intent.putExtra("definition", kissCard.getString(2));
        intent.putExtra("term", kissCard.getString(1));
    }
}
