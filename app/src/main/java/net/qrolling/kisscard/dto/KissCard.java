package net.qrolling.kisscard.dto;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

public class KissCard implements Parcelable {
    private int id;
    private String term;
    private String definition;
    private static final int NEW_CARD_ID = -1;

    public KissCard() {
        this(KissCard.NEW_CARD_ID, null, null);
    }

    public KissCard(String term, String definition) {
        this(KissCard.NEW_CARD_ID, term, definition);
    }

    public KissCard(int id, String term, String definition) {
        this.id = id;
        this.term = term;
        this.definition = definition;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
