package mz.uem.inovacao.fiscaisapp.listeners;

import java.util.List;

import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;

/**
 * Created by MauroBanze on 7/21/17.
 */

public interface GetFiscalListener {

    void onSuccess(Fiscal fiscal, Equipa equipa);
    void onError();


}
