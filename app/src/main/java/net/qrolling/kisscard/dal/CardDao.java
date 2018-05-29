package net.qrolling.kisscard.dal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import net.qrolling.kisscard.dto.KissCard;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
@Dao
public interface CardDao {
    @Insert(onConflict = REPLACE)
    void save(KissCard card);

    @Query("SELECT * FROM card_table WHERE id = :id")
    LiveData<KissCard> load(int id);

    @Query("SELECT * FROM card_table")
    LiveData<List<KissCard>> loadAll();

    @Query("UPDATE card_table SET term = :term , definition = :definition WHERE id = :id")
    void updateCard(int id, String term, String definition);

    @Delete
    void deleteCard(KissCard card);


    @Query("DELETE FROM card_table")
    void deleteAll();
}

