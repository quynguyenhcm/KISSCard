package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.dto.KissCursorCardAdaptor;

public class CardListActivity extends ListActivity {

    private KissCursorCardAdaptor kissCursorCardAdaptor;
    private final DbHelper db = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        initWordList();
    }

    private void initWordList() {
        Cursor kissCardCursor = db.getKissCardCursor();
        kissCursorCardAdaptor = new KissCursorCardAdaptor(this, kissCardCursor);
        setListAdapter(kissCursorCardAdaptor);
    }

    public void onListItemClick(ListView cardList, View v, int position, long id) {
        Cursor kissCard = (SQLiteCursor) cardList.getItemAtPosition(position);
        Intent intent = new Intent(CardListActivity.this, ViewCardActivity.class);
        populateKissCardToIntent(kissCard, intent);
        startActivity(intent);
        finish();
    }

    private void populateKissCardToIntent(Cursor kissCard, Intent intent) {
        intent.putExtra("id", kissCard.getInt(0));
        intent.putExtra("definition", kissCard.getString(2));
        intent.putExtra("term", kissCard.getString(1));
    }
}
