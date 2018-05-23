package net.qrolling.kisscard.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 23/05/18.
 */
public class CardList implements Parcelable {
    ArrayList<KissCard> kissCards;

    public CardList(ArrayList<KissCard> kissCards) {
        this.kissCards = kissCards;
    }

    protected CardList(Parcel in) {
        this.kissCards = in.readArrayList(KissCard.class.getClassLoader());
    }

    public static final Creator<CardList> CREATOR = new Creator<CardList>() {
        @Override
        public CardList createFromParcel(Parcel in) {
            return new CardList(in);
        }

        @Override
        public CardList[] newArray(int size) {
            return new CardList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(kissCards);
    }
}
