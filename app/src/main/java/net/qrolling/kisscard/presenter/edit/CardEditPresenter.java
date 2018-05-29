package net.qrolling.kisscard.presenter.edit;

import net.qrolling.kisscard.dto.KissCard;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 27/05/18.
 */
public interface CardEditPresenter {
    void populateCard(KissCard card);

    void saveCard(KissCard card);
}
