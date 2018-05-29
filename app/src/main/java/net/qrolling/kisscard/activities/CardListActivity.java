package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.qrolling.kisscard.R;
import net.qrolling.kisscard.dto.CardArrayAdaptor;
import net.qrolling.kisscard.dto.KissCard;
import net.qrolling.kisscard.viewmodel.CardListViewModel;

public class CardListActivity extends AppCompatActivity {
    private CardListViewModel mWordViewModel;

    public static final int NEW_CARD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CardArrayAdaptor adapter = new CardArrayAdaptor(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup the WordViewModel
        mWordViewModel = ViewModelProviders.of(this).get(CardListViewModel.class);
        // Get all the words from the database
        // and associate them to the adapter
        mWordViewModel.getAllCards().observe(this, cards -> {
            // Update the cached copy of the cards in the adapter.
            adapter.setCards(cards);
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CardListActivity.this, AddCardActivity.class);
            startActivityForResult(intent, NEW_CARD_ACTIVITY_REQUEST_CODE);
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int swipedPosition = viewHolder.getAdapterPosition();
                        CardArrayAdaptor adapter = (CardArrayAdaptor) recyclerView.getAdapter();
                        adapter.pendingRemoval(swipedPosition);
                        final int position = viewHolder.getAdapterPosition();
                        DialogInterface.OnClickListener dialogClickListener = getOnClickListener(position);
                        confirmDelete(dialogClickListener);
                    }

                    private void confirmDelete(DialogInterface.OnClickListener dialogClickListener) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(CardListActivity.this);
                        ab.setMessage(R.string.message_delete_confirmation)
                                .setPositiveButton(R.string.message_yes, dialogClickListener)
                                .setNegativeButton(R.string.message_no, dialogClickListener)
                                .show();
                    }

                    @NonNull
                    private DialogInterface.OnClickListener getOnClickListener(int position) {
                        return new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selection) {
                                switch (selection) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        deleteCard();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        recyclerView.getAdapter().notifyItemChanged(selection);
                                        break;
                                }
                            }

                            private void deleteCard() {
                                KissCard myCard = adapter.getCardAtPosition(position);
                                Toast.makeText(CardListActivity.this,
                                        getString(R.string.delete_card_preamble) + " " +
                                                myCard.getTerm(), Toast.LENGTH_LONG).show();

                                // Delete the word
                                mWordViewModel.deleteCard(myCard);
                            }
                        };
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data
            mWordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new word in the NewWordActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.
     *
     * @param requestCode -- ID for the request
     * @param resultCode  -- indicates success or failure
     * @param data        -- The Intent sent back from the NewWordActivity,
     *                    which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CARD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            KissCard card = (KissCard) data.getParcelableExtra(AddCardActivity.EXTRA_REPLY);
            // Save the data
            mWordViewModel.insert(card);
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
