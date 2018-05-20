package net.qrolling.kisscard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;

public class AddCardActivity extends Activity implements View.OnClickListener {
    private final DbHelper db = new DbHelper(this);
    private EditText txtTerm, txtDefinition;
    private Button btnSave, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        initialiseUIComponent();
        registerEventHandler();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            saveNewTerm();
        } else if (v.getId() == R.id.btnShow) {
            showCardList();
        }
    }

    private void registerEventHandler() {
        btnSave.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    private void initialiseUIComponent() {
        txtTerm = findViewById(R.id.cardTerm);
        txtDefinition = findViewById(R.id.cardDefinition);
        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
    }


    private void showCardList() {
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
    }

    private void saveNewTerm() {
        db.insertCard(txtTerm.getText().toString(), txtDefinition.getText().toString());
    }
}