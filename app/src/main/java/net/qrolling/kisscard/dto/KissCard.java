package net.qrolling.kisscard.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */
@Entity(tableName = "card_table")
public class KissCard implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "term")
    private String term;

    @ColumnInfo(name = "definition")
    private String definition;
    private static final int NEW_CARD_ID = -1;

    public KissCard() {
//        this(KissCard.NEW_CARD_ID, null, null);
    }

    public KissCard(@NonNull String term, @NonNull String definition) {
        this.term = term;
        this.definition = definition;
    }
//
//    public KissCard(int id, @NonNull String term, @NonNull String definition) {
//        this.id = id;
//        this.term = term;
//        this.definition = definition;
//    }

    private KissCard(Parcel in) {
        id = in.readInt();
        term = in.readString();
        definition = in.readString();
    }

    public static final Creator<KissCard> CREATOR = new Creator<KissCard>() {
        @Override
        public KissCard createFromParcel(Parcel in) {
            return new KissCard(in);
        }

        @Override
        public KissCard[] newArray(int size) {
            return new KissCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(term);
        parcel.writeString(definition);
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public boolean isNew() {
        return this.id == KissCard.NEW_CARD_ID;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KissCard kissCard = (KissCard) o;
        return id == kissCard.id &&
                Objects.equals(term, kissCard.term) &&
                Objects.equals(definition, kissCard.definition);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(id, term, definition);
    }
}
