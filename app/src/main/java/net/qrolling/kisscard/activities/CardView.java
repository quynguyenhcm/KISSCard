package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.qrolling.kisscard.R;

public class CardView extends Activity {

    boolean isShowingDefinition;
    TextView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        cardView = findViewById(R.id.cardView);
        final String definition = (String) getIntent().getExtras().get("definition");
        final String term = (String) getIntent().getExtras().get("term");
        Integer id = (Integer) getIntent().getExtras().get("id");

        cardView.setText(definition);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(term, definition);
            }
        });
    }

    private void flipCard(String term, String definition) {
        isShowingDefinition = !isShowingDefinition;
        if (isShowingDefinition) {
            cardView.setText(definition);
        } else {
            cardView.setText(term);
        }
    }
}
