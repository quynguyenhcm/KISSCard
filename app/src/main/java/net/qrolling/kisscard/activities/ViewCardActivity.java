package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.dto.KissCard;

import java.util.ArrayList;

public class ViewCardActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    private GestureDetector gestureDetector;

    private final DbHelper db = new DbHelper(this);
    private int selectedPosition;
    private int listSize;

    private boolean isShowingDefintion;

    private TextView txtCard;
    private Button btnUpdate, btnDelete;

    private String definition;
    private String term;
    private Integer id;
    private ArrayList<KissCard> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(ViewCardActivity.this, new GestureListener());
        initialiseUIComponent();
        initialiseCardList();
        selectedPosition = getIntent().getIntExtra("selectedPosition", 0);
        populateCard(selectedPosition);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardView) {
            flipCard(term, definition);
        } else if (view.getId() == R.id.btnUpdateCard) {
            startUpdateActivity();
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
        if (db.rowcount() > 0) {
            backToCardList();
        }
        finish();
    }

    private void backToCardList() {
        Intent i = new Intent(ViewCardActivity.this, CardListActivity.class);
        startActivity(i);
    }

    private void deleteCardFromDatabase() {
        db.deleteCard(id);
    }

    private void startUpdateActivity() {
        Intent intent = new Intent(ViewCardActivity.this, UpdateCardActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("definition", definition);
        intent.putExtra("term", term);
        startActivity(intent);
        finish();
    }

    private void populateCard(int selectedPosition) {
        KissCard selectedCard = cards.get(selectedPosition);
        definition = selectedCard.getDefinition();
        term = selectedCard.getTerm();
        id = selectedCard.getId();
        txtCard.setText(term);
    }

    private void initialiseUIComponent() {
        setContentView(R.layout.activity_card_view);

        txtCard = findViewById(R.id.cardView);
        txtCard.setOnTouchListener(this);
        txtCard.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdateCard);
        btnUpdate.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    private void flipCard(String term, String definition) {
        if (!isShowingDefintion) {
            txtCard.setText(definition);
        } else {
            txtCard.setText(term);
        }
        isShowingDefintion = !isShowingDefintion;
    }

    private DialogInterface.OnClickListener getDeleteConfirmListener() {
        DialogInterface.OnClickListener deleteConfirmListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                switch (selection) {
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

    private void initialiseCardList() {
        cards = getIntent().getParcelableArrayListExtra("cardList");
        listSize = cards.size();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onClick();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                } else {
                    onClick();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    private void onSwipeTop() {
        showPreviousCard();
    }

    private void onSwipeRight() {
        showNextCard();
    }

    private void onSwipeLeft() {
        showPreviousCard();
    }

    private void showPreviousCard() {
        if (selectedPosition > 0) {
            populateCard(--selectedPosition);
        } else {
            Toast.makeText(ViewCardActivity.this, "No more card to show", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNextCard() {
        if (selectedPosition < listSize - 1) {
            populateCard(++selectedPosition);
        } else {
            Toast.makeText(ViewCardActivity.this, "No more card to show", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSwipeBottom() {
        showNextCard();
    }

    private void onClick() {
        flipCard(term, definition);
    }
}
