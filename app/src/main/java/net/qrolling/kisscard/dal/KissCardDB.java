package net.qrolling.kisscard.dal;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import net.qrolling.kisscard.dto.KissCard;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
@Database(entities = {KissCard.class}, version = 2)
public abstract class KissCardDB extends RoomDatabase {
    public abstract CardDao cardDao();

    private static KissCardDB INSTANCE;

    public static KissCardDB getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (KissCardDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            KissCardDB.class, "card_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CardDao mDao;

        // Initial data set
        private static String[] words = {"dolphin", "crocodile", "cobra", "elephant", "goldfish",
                "tiger", "snake"};

        PopulateDbAsync(KissCardDB db) {
            mDao = db.cardDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.loadAll().getValue() == null || mDao.loadAll().getValue().size() < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    KissCard card = new KissCard(words[i], words[i]);
                    mDao.save(card);
                }
            }
            return null;
        }
    }
}
