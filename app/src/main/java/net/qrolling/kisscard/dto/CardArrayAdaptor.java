package net.qrolling.kisscard.dto;

import android.content.Context;
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

    public CardArrayAdaptor(Context context, ArrayList<KissCard> cards) {
        super(context, 0, cards);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        KissCard card = getItem(position);
        ViewCache viewCache;

        if (listItem == null) {
            viewCache = new ViewCache();
            listItem = LayoutInflater.from(mContext).inflate(R.layout.card_row, parent, false);
            viewCache.id = listItem.findViewById(R.id.card_id);
            viewCache.definition = listItem.findViewById(R.id.card_definition);
            viewCache.term = listItem.findViewById(R.id.card_term);
            listItem.setTag(viewCache);
        } else {
            viewCache = (ViewCache) listItem.getTag();
        }

        viewCache.id.setText(Integer.toString(card.getId()));
        viewCache.term.setText(card.getTerm());
        viewCache.term.setTag(position);
        viewCache.definition.setText(card.getDefinition());
        return listItem;
    }

    private static class ViewCache {
        TextView id;
        TextView term;
        TextView definition;
    }
}
