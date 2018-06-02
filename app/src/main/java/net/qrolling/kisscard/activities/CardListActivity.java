package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import net.qrolling.kisscard.utils.SwipeUtil;
import net.qrolling.kisscard.viewmodel.CardListViewModel;

public class CardListActivity extends AppCompatActivity {
    private CardListViewModel mCardViewModel;

    public static final int NEW_CARD_ACTIVITY_REQUEST_CODE = 1;
    // Setup the RecyclerView
    RecyclerView recyclerView;
    CardArrayAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new CardArrayAdaptor(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup the WordViewModel
        mCardViewModel = ViewModelProviders.of(this).get(CardListViewModel.class);
        // Get all the words from the database
        // and associate them to the adapter
        mCardViewModel.getAllCards().observe(this, cards -> {
            // Update the cached copy of the cards in the adapter.
            adapter.setCards(cards);
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CardListActivity.this, AddCardActivity.class);
            startActivityForResult(intent, NEW_CARD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CardArrayAdaptor(this);
        recyclerView.setAdapter(adapter);

        setSwipeForRecyclerView();
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
                    new AlertDialog.Builder(CardListActivity.this)
                .setMessage(R.string.clear_data_toast_text_confirm)
                .setPositiveButton(R.string.message_delete, (dialog, which) ->  mCardViewModel.deleteAll())
                .setNegativeButton(R.string.message_cancel, (dialog, which) -> Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show())
                .setOnCancelListener(dialogInterface -> Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show())
                .create().show();
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
            KissCard card = data.getParcelableExtra(AddCardActivity.EXTRA_REPLY);
            // Save the data
            mCardViewModel.insert(card);
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void setSwipeForRecyclerView() {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, CardListActivity.this) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                CardArrayAdaptor adapter = (CardArrayAdaptor) recyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition, mCardViewModel);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                CardArrayAdaptor adapter = (CardArrayAdaptor) recyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        //set swipe label
        swipeHelper.setLeftSwipeLable(getResources().getString(R.string.archived_label));
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(CardListActivity.this, R.color.colorPrimaryDark));

    }
}
