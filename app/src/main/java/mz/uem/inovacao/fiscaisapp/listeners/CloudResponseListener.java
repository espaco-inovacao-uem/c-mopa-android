package mz.uem.inovacao.fiscaisapp.listeners;

import java.util.ArrayList;


/**
 * Created by MauroBanze on 8/22/17.
 */

public interface CloudResponseListener<E> {

    void success(ArrayList<E> lista);
    void error(String error);
}
