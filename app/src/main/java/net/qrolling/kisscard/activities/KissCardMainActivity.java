package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;

public class KissCardMainActivity extends Activity {

    private Button btnStudy;
    private Button btnNewCard;
    private final DbHelper db = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiss_card_main);
        initialiseUIComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_kiss_card, menu);
        return true;
    }

    private void initialiseUIComponents() {
        initialiseButtonStudy();
        initialiseButtonAdd();
    }

    private void initialiseButtonAdd() {
        btnNewCard = findViewById(R.id.newcard);
        btnNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KissCardMainActivity.this, AddCardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initialiseButtonStudy() {
        btnStudy = findViewById(R.id.study);
        btnStudy.setEnabled(isCardListAvailable());
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KissCardMainActivity.this, CardListActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public boolean isCardListAvailable() {
        return db.getKissCardCursor().getCount() > 0;
    }
}