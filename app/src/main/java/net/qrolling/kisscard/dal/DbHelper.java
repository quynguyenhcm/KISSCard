package net.qrolling.kisscard.dal;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.qrolling.kisscard.dto.KissCard;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "KissCard";
    // tasks table name
    private static final String TABLE_KISS_CARDS = "flashcards";

    // Kiss Card Table Columns names
    public static final String KEY_ID = "_id";//Should be "_id" so that the column can used with Cursor, otherwise we can use alias to "_id"
    public static final String KEY_TERM = "term";
    public static final String KEY_DEFINITION = "definition";

    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KISS_CARDS);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_KISS_CARDS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TERM
                + " TEXT, " + KEY_DEFINITION + " TEXT)";
        db.execSQL(sql);
        addSampleCards();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KISS_CARDS);
        // Create tables again
        onCreate(db);
    }

    // Adding new card
    public void addKissCard(KissCard card) {
        insertCard(card.getTerm(), card.getDefinition());
    }

    public Cursor getCardCursor() {
        String selectQuery = "SELECT  * FROM " + TABLE_KISS_CARDS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public void insertCard(String term, String defintion) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TERM, term);
        values.put(KEY_DEFINITION, defintion);
        db.insert(TABLE_KISS_CARDS, null, values);
    }

    public void updateCard(int id, String term, String defintion) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TERM, term);
        values.put(KEY_DEFINITION, defintion);
        String where = KEY_ID + "=?";
        db.update(TABLE_KISS_CARDS, values, where, new String[]{String.valueOf(id)});
    }

    public void deleteCard(int id) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String where = KEY_ID + "=?";
        db.delete(TABLE_KISS_CARDS, where, new String[]{String.valueOf(id)});
    }

    private void addSampleCards() {
        KissCard q1 = new KissCard("Banana", "A kind of fruit that is good for health");
        this.addKissCard(q1);
        KissCard q2 = new KissCard("Orange", "A kind of fruit that is rich of Vitamin C");
        this.addKissCard(q2);
    }
}
