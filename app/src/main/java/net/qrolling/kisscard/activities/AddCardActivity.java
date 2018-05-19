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
    DbHelper db = new DbHelper(this);
    EditText Term, Definition;
    Button Submitdatabtn, Showdatabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Term = findViewById(R.id.cardTerm);
        Definition = findViewById(R.id.cardDefinition);

        Submitdatabtn = findViewById(R.id.btnSave);
        Showdatabtn = findViewById(R.id.btnShow);
        Submitdatabtn.setOnClickListener(this);
        Showdatabtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            db.insertCard(Term.getText().toString(), Definition.getText().toString());
        } else if (v.getId() == R.id.btnShow) {
            Intent intent = new Intent(this, CardListActivity.class);
            startActivity(intent);
        }
    }
}