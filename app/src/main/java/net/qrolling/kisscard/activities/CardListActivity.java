package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.dto.CardArrayAdaptor;
import net.qrolling.kisscard.dto.KissCard;
import net.qrolling.kisscard.dto.KissCursorCardAdaptor;

import java.util.ArrayList;

public class CardListActivity extends Activity {

    private KissCursorCardAdaptor kissCursorCardAdaptor;
    private final DbHelper db = new DbHelper(this);
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_adaptor);

        initWordList();
    }

    private void initWordList() {
//        Cursor kissCardCursor = db.getKissCardCursor();
//        kissCursorCardAdaptor = new KissCursorCardAdaptor(this, kissCardCursor);
//        setListAdapter(kissCursorCardAdaptor);
        ArrayList<KissCard> cards = db.getAllCards();
        ArrayAdapter<KissCard> adaptor = new CardArrayAdaptor(this, cards);
        //setListAdapter(adaptor);
        listView = (ListView) findViewById(R.id.cards_list);
        listView.setAdapter(adaptor);
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
