package net.qrolling.kisscard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;
import net.qrolling.kisscard.utils.Validator;

public class AddCardActivity extends Activity implements View.OnClickListener {
    private final DbHelper db = new DbHelper(this);
    private EditText txtTerm, txtDefinition;
    private Button btnSave, btnSaveAndContinue;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        setContentView(R.layout.activity_add_card);
        initialiseUIComponent();
        registerEventHandler();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            if (isValidCard()) {
                saveNewCard();
                showCardList();
            }
        } else if (v.getId() == R.id.btnSaveAndContinue) {
            if (isValidCard()) {
                saveNewCard();
                reset();
            }
        }
    }

    private void registerEventHandler() {
        btnSave.setOnClickListener(this);
        btnSaveAndContinue.setOnClickListener(this);
    }

    private void initialiseUIComponent() {
        txtTerm = findViewById(R.id.cardTerm);
        txtDefinition = findViewById(R.id.cardDefinition);
        btnSave = findViewById(R.id.btnSave);
        btnSaveAndContinue = findViewById(R.id.btnSaveAndContinue);
    }
    
    private void showCardList() {
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveNewCard() {
        db.insertCard(txtTerm.getText().toString(), txtDefinition.getText().toString());
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