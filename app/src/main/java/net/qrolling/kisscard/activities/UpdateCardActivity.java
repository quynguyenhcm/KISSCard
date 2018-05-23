package net.qrolling.kisscard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.dto.KissCard;
import net.qrolling.kisscard.utils.Validator;

public class UpdateCardActivity extends Activity implements View.OnClickListener {
    private final DbHelper db = new DbHelper(this);
    private EditText txtTerm, txtDefinition;
    private Button btnUpdate;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        setContentView(R.layout.activity_update_card);
        initialiseUIComponent();
        populateCard();
        registerEventHandler();
    }

    private void populateCard() {
        KissCard card = getIntent().getParcelableExtra("card");
        txtDefinition.setText(card.getDefinition());
        txtTerm.setText(card.getTerm());
        txtTerm.setTag(card.getId());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate && isValidCard()) {
            updateCard();
        }
    }

    private void registerEventHandler() {
        btnUpdate.setOnClickListener(this);
    }

    private void initialiseUIComponent() {
        txtTerm = findViewById(R.id.cardTerm);
        txtDefinition = findViewById(R.id.cardDefinition);
        btnUpdate = findViewById(R.id.btnUpdate);
    }


    private void showCardList() {
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
    }

    private void updateCard() {
        db.updateCard(Integer.parseInt(txtTerm.getTag().toString()), txtTerm.getText().toString(), txtDefinition.getText().toString());
        showCardList();
    }

    private boolean isValidCard() {
        return validator.isNotNull(txtTerm, getResources().getString(R.string.lbl_term))
                & validator.isNotNull(txtDefinition, getResources().getString(R.string.lbl_definition));
    }
}
