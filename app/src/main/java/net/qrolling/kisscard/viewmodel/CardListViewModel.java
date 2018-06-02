package net.qrolling.kisscard.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import net.qrolling.kisscard.dal.CardRepository;
import net.qrolling.kisscard.dto.KissCard;

import java.util.List;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
public class CardListViewModel extends AndroidViewModel {
    private LiveData<List<KissCard>> cards;
    private CardRepository mRepository;

    public CardListViewModel(Application application) {
        super(application);
        mRepository = new CardRepository(application);
        cards = mRepository.allCards();
    }

    private void loadCards() {
        cards = mRepository.allCards();
        // Do an asynchronous operation to fetch cards.
    }

    public LiveData<List<KissCard>> getAllCards() {
        if (cards == null) {
            cards = new MutableLiveData<>();
            loadCards();
        }
        return cards;
    }

    public void insert(KissCard card) {
        mRepository.insert(card);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteCard(KissCard card) {
        mRepository.deleteKissCard(card);
        cards.getValue().remove(card);
    }
}
