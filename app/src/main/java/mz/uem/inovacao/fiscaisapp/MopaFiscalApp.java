package mz.uem.inovacao.fiscaisapp;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by MauroBanze on 7/26/17.
 */

public class MopaFiscalApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
