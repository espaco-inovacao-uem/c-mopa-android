package mz.uem.inovacao.fiscaisapp.listeners;

import android.support.v4.app.Fragment;

/**
 * Created by Mauro Banze on 18/03/2015.
 */
public interface OnFragmentReadyListener {

    /**
     * To be called by a Fragment and implemented by an Activity so the fragment can notify the activity that the
     * it is ready to be worked with.
     * The fragment passed itself as a parameter to allow the activity to listen for diferent fragments
     */
    void onFragmentReady(Fragment fragment);
}
