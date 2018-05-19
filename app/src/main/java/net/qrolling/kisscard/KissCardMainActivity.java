package net.qrolling.kisscard;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class KissCardMainActivity extends Activity {

    Button btnStudy;
    Button btnNewCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiss_card_main);

        btnStudy = findViewById(R.id.study);
        btnNewCard = findViewById(R.id.newcard);

        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KissCardMainActivity.this, CardListActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

}