package net.qrolling.kisscard.dto;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 19/05/18.
 */
public class KissCursorCardAdaptor extends CursorAdapter {
    public KissCursorCardAdaptor(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.term, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Determine fields on view to populate in inflated template
        TextView vTerm = (TextView) view.findViewById(R.id.term);
        TextView vTermId = (TextView) view.findViewById(R.id.termId);
        TextView vDefinition = (TextView) view.findViewById(R.id.definition);
        if (cursor.getPosition() % 2 == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        // Extract properties from cursor
        String term = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.KEY_TERM));
        String definition = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.KEY_DEFINITION));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.KEY_ID));

        // Populate fields with extracted properties
        vTerm.setText(term);
        vTermId.setText(String.valueOf(id));
        vDefinition.setText(definition);
    }
}
