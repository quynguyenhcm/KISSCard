package net.qrolling.kisscard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dal.DbHelper;

public class EditCardActivity extends Activity implements View.OnClickListener {
    private DbHelper db;
    private EditText txtTerm, txtDefinition;
    private Button btnUpdate;
    private String definition;
    private String term;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        db = new DbHelper(this);
        initialiseUIComponent();
        populateCard();
        registerEventHandler();
    }

    private void populateCard() {
        definition = (String) getIntent().getExtras().get("definition");
        txtDefinition.setText(definition);
        id = (Integer) getIntent().getExtras().get("id");
        term = (String) getIntent().getExtras().get("term");
        txtTerm.setText(term);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
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
        db.updateCard(id, txtTerm.getText().toString(), txtDefinition.getText().toString());
        showCardList();
    }
}
