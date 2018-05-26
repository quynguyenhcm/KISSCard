package net.qrolling.kisscard.activities;

import android.app.Activity;

import net.qrolling.kisscard.dal.DbHelper;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 24/05/18.
 */
public class DbInteractionActivity extends Activity {
    private final DbHelper DB = new DbHelper(this);

    protected DbHelper getDb() {
        return DB;
    }
}
