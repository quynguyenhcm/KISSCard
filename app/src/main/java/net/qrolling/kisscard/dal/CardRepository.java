package net.qrolling.kisscard.dal;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import net.qrolling.kisscard.dto.KissCard;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
@Singleton
public class CardRepository {
    private final CardDao cardDao;
    //    private final Executor executor;
    private LiveData<List<KissCard>> mAllCards;

    public CardRepository(Application application) {
        KissCardDB db = KissCardDB.getDatabase(application);
        cardDao = db.cardDao();
        mAllCards = cardDao.loadAll();
    }
//
//    @Inject
//    public CardRepository(Application application, CardDao cardDao, Executor executor) {
//        KissCardDB db = KissCardDB.getDatabase(application);
//        this.cardDao = cardDao;
//        this.executor = executor;
//    }

    public LiveData<List<KissCard>> allCards() {
        return mAllCards;
    }

    public void insert(KissCard card) {
        new insertAsyncTask(cardDao).execute(card);
    }

    public void deleteAll() {
        new deleteAllKissCardsAsyncTask(cardDao).execute();
    }

    // Need to run off main thread
    public void deleteKissCard(KissCard word) {
        new deleteKissCardAsyncTask(cardDao).execute(word);
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<KissCard, Void, Void> {

        private CardDao mAsyncTaskDao;

        insertAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(KissCard... params) {
            mAsyncTaskDao.save(params[0]);
            return null;
        }
    }

    /**
     * Delete all words from the database (does not delete the table)
     */
    private static class deleteAllKissCardsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDao mAsyncTaskDao;

        deleteAllKissCardsAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     * Delete a single word from the database.
     */
    private static class deleteKissCardAsyncTask extends AsyncTask<KissCard, Void, Void> {
        private CardDao mAsyncTaskDao;

        deleteKissCardAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(KissCard... params) {
            mAsyncTaskDao.deleteCard(params[0]);
            return null;
        }
    }
}
