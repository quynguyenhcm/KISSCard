package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.dto.CardArrayAdaptor;
import net.qrolling.kisscard.dto.KissCard;

import java.util.ArrayList;

public class CardListActivity extends Activity {

    private final DbHelper db = new DbHelper(this);
    private ListView listView;
    private final ArrayList<KissCard> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_adaptor);
        cards.addAll(db.getAllCards());
        populateCardList();
        registerEventHandler();
    }

    private void registerEventHandler() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CardListActivity.this, ViewCardActivity.class);
                intent.putParcelableArrayListExtra("cardList", cards);
//                intent.putExtra("selectedCard", (KissCard) listView.getItemAtPosition(i));
                intent.putExtra("selectedPosition", position);
                startActivity(intent);
                finish();
            }
        });
    }

    private void populateCardList() {
        ArrayAdapter<KissCard> adaptor = new CardArrayAdaptor(this, cards);
        listView = findViewById(R.id.cards_list);
        listView.setAdapter(adaptor);
    }
}
