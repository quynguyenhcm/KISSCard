package net.qrolling.kisscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dto.CardLisHolder;
import net.qrolling.kisscard.dto.KissCard;
import net.qrolling.kisscard.utils.Validator;

public class AddCardActivity extends DbInteractionActivity implements View.OnClickListener {
    private EditText txtTerm, txtDefinition;
    private Button btnSave, btnCancel;
    private Validator validator;
    private KissCard card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        setContentView(R.layout.activity_add_card);

        initialiseUIComponent();
        registerEventHandler();

        initialiseCard();
        populateCardData();
    }

    private void populateCardData() {
        txtTerm.setText(card.getTerm());
        txtDefinition.setText(card.getDefinition());
        txtTerm.setTag(card.getId());
    }

    private void initialiseCard() {
        card = getIntent().getParcelableExtra("card");
        if (card == null) {
            card = new KissCard();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            updateCardFromView();
            saveCard();
            if (card.isNew()) {
                showCardList();
            } else {
                showCard(card);
            }
        } else if (v.getId() == R.id.btnCancel) {
            cancel();
        }
    }

    private void updateCardFromView() {
        card.setDefinition(txtDefinition.getText().toString());
        card.setTerm(txtTerm.getText().toString());
    }

    private void cancel() {
        if (card.isNew()) {
            showCardList();
        } else {
            viewCard(card);
        }
        finish();
    }

    private void viewCard(KissCard card) {
        Intent intent = new Intent(AddCardActivity.this, ViewCardActivity.class);
        intent.putExtra("card", card);
        startActivity(intent);
    }

    private void saveCard() {
        if (isValidCard()) {
            saveCardToDb();
            refreshCardHolder();
        }
    }

    private void refreshCardHolder() {
        CardLisHolder.getInstance().saveList(getDb().getAllCards());
    }

    private void saveCardToDb() {
        getDb().saveCard(card);
    }

    private void showCard(KissCard card) {
        Intent intent = new Intent(AddCardActivity.this, ViewCardActivity.class);
        intent.putExtra("card", card);
        startActivity(intent);
        finish();
    }

    private void registerEventHandler() {
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initialiseUIComponent() {
        txtTerm = findViewById(R.id.cardTerm);
        txtDefinition = findViewById(R.id.cardDefinition);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
    
    private void showCardList() {
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
        finish();
    }

    private void reset() {
        txtDefinition.setText("");
        txtTerm.setText("");
    }

    private boolean isValidCard() {
        return validator.isNotNull(txtTerm, getResources().getString(R.string.lbl_term))
                & validator.isNotNull(txtDefinition, getResources().getString(R.string.lbl_definition));
    }
}