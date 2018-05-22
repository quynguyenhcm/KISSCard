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

import java.util.ArrayList;

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

    public ArrayList<KissCard> getAllCards() {
        ArrayList<KissCard> cardList = new ArrayList<>();
        Cursor cursor = getKissCardCursor();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KissCard quest = new KissCard();
                quest.setID(cursor.getInt(0));
                quest.setTerm(cursor.getString(1));
                quest.setDefinition(cursor.getString(2));
                cardList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return cardList;
    }

    public Cursor getKissCardCursor() {
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
        String where = KEY_ID + "=?";
        db.delete(TABLE_KISS_CARDS, where, new String[]{String.valueOf(id)});
    }

    public int rowcount() {
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_KISS_CARDS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
