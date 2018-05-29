package net.qrolling.kisscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dto.KissCard;
import net.qrolling.kisscard.utils.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCardActivity extends AppCompatActivity {
    @BindView(R.id.cardTerm)
    EditText txtTerm;

    @BindView(R.id.cardDefinition)
    EditText txtDefinition;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    private Validator validator;

    public static final String EXTRA_REPLY = "net.qrolling.kisscard.activities.REPLY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
validator = new Validator(this);
        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (isValidCard()) {
//                    // No word was entered, set the result accordingly.
//                    setResult(RESULT_CANCELED, replyIntent);
//                } else {
                    // Get the new word that the user entered.
                    KissCard card = new KissCard(txtTerm.getText().toString(), txtDefinition.getText().toString());
                    // Put the new word in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, card);
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }


    private boolean isValidCard() {
        return validator.isNotNull(txtTerm, getResources().getString(R.string.lbl_term))
                & validator.isNotNull(txtDefinition, getResources().getString(R.string.lbl_definition));
    }
}