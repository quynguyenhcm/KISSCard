package net.qrolling.kisscard.presenter.view;

import net.qrolling.kisscard.dto.KissCard;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
public interface CardViewPresenter {
    void showCard(KissCard card);

    void showNextCard(KissCard card);

    void showPreviousCard(KissCard card);
}
