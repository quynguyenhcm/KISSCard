package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dto.CardArrayAdaptor;
import net.qrolling.kisscard.dto.CardLisHolder;
import net.qrolling.kisscard.dto.KissCard;

public class CardListActivity extends DbInteractionActivity {

    private ListView listView;
    private Button btnAddNew, btnStudy;
    private final int FIRST_CARD_POSITION = 0;
    private final CardLisHolder cardLisHolder = CardLisHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        initialiseUI();
        registerEventHandler();
        queryCardList();
        populateCardList();
    }

    private void queryCardList() {
        //if (!cardLisHolder.isInitialised()) {
        cardLisHolder.saveList(getDb().getAllCards());
        //}
    }

    private void registerEventHandler() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                viewCard(position);
            }
        });
        btnStudy.setVisibility(View.VISIBLE);

        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCard(FIRST_CARD_POSITION);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CardListActivity.this, AddCardActivity.class);
                startActivity(i);
            }
        });
    }

    private void viewCard(int position) {
        Intent intent = new Intent(CardListActivity.this, ViewCardActivity.class);
//        intent.putParcelableArrayListExtra("cardList", cards);
        intent.putExtra("selectedPosition", position);
        intent.putExtra("card", cardLisHolder.getCards().get(position));
        startActivity(intent);
    }

    private void populateCardList() {
        ArrayAdapter<KissCard> adaptor = new CardArrayAdaptor(this, cardLisHolder.getCards());
        listView.setAdapter(adaptor);
    }

    private void initialiseUI() {
        listView = findViewById(R.id.cards_list);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnStudy = findViewById(R.id.btnStudy);
        btnStudy.setEnabled(isCardListAvailable());
    }

    public boolean isCardListAvailable() {
        return getDb().getKissCardCursor().getCount() > 0;
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        populateCardList();
    }
}
