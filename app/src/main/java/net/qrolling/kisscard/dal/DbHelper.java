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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "KissCard";
    // tasks table name
    private static final String TABLE_KISS_CARDS = "flashcards";

    // Kiss Card Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_TERM = "term";
    public static final String KEY_DEFINITION = "definition";

    private SQLiteDatabase dbase;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KISS_CARDS);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_KISS_CARDS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TERM
                + " TEXT, " + KEY_DEFINITION + " TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }

    private void addAllQuestions() {

        KissCard kissCard;

    }

    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (Exception e) {
            return null;
        }
        return text.toString();
    }

    private void addQuestions() {
        KissCard q1 = new KissCard("Banana", "A kind of fruit that is good for health");
        this.addQuestion(q1);
        KissCard q2 = new KissCard("Orange", "A kind of fruit that is rich of Vitamin C");
        this.addQuestion(q2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KISS_CARDS);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(KissCard quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TERM, quest.getTerm());
        values.put(KEY_DEFINITION, quest.getDefinition());
        // Inserting Row
        dbase.insert(TABLE_KISS_CARDS, null, values);
    }

    public Cursor getAllRawCards() {
        String selectQuery = "SELECT  * FROM " + TABLE_KISS_CARDS;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        return cursor;
    }

    public List<KissCard> getAllTerms() {
        List<KissCard> cardList = new ArrayList<>();
        Cursor cursor = getAllRawCards();
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

    public int rowcount() {
        int row = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_KISS_CARDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row = cursor.getCount();
        return row;
    }
}
