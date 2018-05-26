package net.qrolling.kisscard.dto;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 24/05/18.
 */
public class CardLisHolder {

    private static final CardLisHolder DATA_HOLDER = new CardLisHolder();
    Map<String, WeakReference<ArrayList<KissCard>>> data = new HashMap<>();
    public static final String CARD_LIST = "cardList";

    public void saveList(ArrayList<KissCard> cards) {
        data.put(CARD_LIST, new WeakReference<>(cards));
    }

    public ArrayList<KissCard> getCards() {
        WeakReference<ArrayList<KissCard>> cardsRef = data.get(CARD_LIST);
        return cardsRef.get();
    }

    public void removeCard(int position) {
        ArrayList<KissCard> cards = getCards();
        cards.remove(position);
    }

    public static CardLisHolder getInstance() {
        return DATA_HOLDER;
    }

    public int size() {
        return getCards().size();
    }

    public int getIndex(KissCard card) {
        return getCards().indexOf(card);
    }
}
