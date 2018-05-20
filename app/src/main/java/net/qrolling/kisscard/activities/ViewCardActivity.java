package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.utils.OnSwipeListener;

public class ViewCardActivity extends Activity implements View.OnClickListener {

    private final DbHelper db = new DbHelper(this);

    private boolean isShowingDefintion;
    private TextView cardView;
    private TextView txtCard;
    private Button btnUpdate, btnDelete;

    private String definition;
    private String term;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        initialiseUIComponent();
        registerEventHandler();
        populateCard();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardView) {
            flipCard(term, definition);
        } else if (view.getId() == R.id.btnUpdateCard) {
            initUpdateIntent();
        } else if (view.getId() == R.id.btnDelete) {
            confirmDelete();
        }
    }

    private void confirmDelete() {
        DialogInterface.OnClickListener dialogClickListener = getDeleteConfirmListener();
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage(R.string.message_delete_confirmation)
                .setPositiveButton(R.string.message_yes, dialogClickListener)
                .setNegativeButton(R.string.message_no, dialogClickListener)
                .show();
    }

    private void deleteCard() {
        deleteCardFromDatabase();
        backToCardList();
    }

    private void backToCardList() {
        Intent i = new Intent(ViewCardActivity.this, CardListActivity.class);
        startActivity(i);
    }

    private void deleteCardFromDatabase() {
        db.deleteCard(id);
    }

    private void initUpdateIntent() {
        Intent intent = new Intent(ViewCardActivity.this, EditCardActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("definition", definition);
        intent.putExtra("term", term);
        startActivity(intent);
    }

    private void populateCard() {
        cardView = findViewById(R.id.cardView);
        definition = (String) getIntent().getExtras().get("definition");
        term = (String) getIntent().getExtras().get("term");
        id = (Integer) getIntent().getExtras().get("id");
        cardView.setText(definition);
    }

    private void registerEventHandler() {
        txtCard.setOnClickListener(this);
        txtCard.setOnTouchListener(new OnSwipeListener(ViewCardActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(ViewCardActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(ViewCardActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(ViewCardActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(ViewCardActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void initialiseUIComponent() {
        txtCard = findViewById(R.id.cardView);
        btnUpdate = findViewById(R.id.btnUpdateCard);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void flipCard(String term, String definition) {
        isShowingDefintion = !isShowingDefintion;
        if (!isShowingDefintion) {
            cardView.setText(term);
        } else {
            cardView.setText(definition);
        }
    }

    private DialogInterface.OnClickListener getDeleteConfirmListener() {
        DialogInterface.OnClickListener deleteConfirmListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteCard();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        return deleteConfirmListener;
    }

}
