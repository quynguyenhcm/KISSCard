package net.qrolling.kisscard.dto;

import android.os.Binder;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 19/05/18.
 */
public class KissCardBinder extends Binder {

    private final KissCard kissCard;

    public KissCardBinder(KissCard kissCard) {
        this.kissCard = kissCard;
    }

    public KissCard getKissCard() {
        return kissCard;
    }
}
