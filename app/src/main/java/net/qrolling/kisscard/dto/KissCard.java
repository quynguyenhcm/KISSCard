package net.qrolling.kisscard.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

public class KissCard implements Parcelable {
    private int id;
    private String term;
    private String definition;

    public KissCard() {
        id = 0;
        term = "";
        definition = "";
    }

    private KissCard(Parcel in) {
        id = in.readInt();
        term = in.readString();
        definition = in.readString();
    }

    public KissCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
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

}
