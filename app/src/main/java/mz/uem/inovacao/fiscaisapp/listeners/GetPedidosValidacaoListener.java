package mz.uem.inovacao.fiscaisapp.listeners;

import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;

/**
 * Created by MauroBanze on 7/26/17.
 */

public interface GetPedidosValidacaoListener {

    void onSuccess(Fiscal fiscal, Equipa equipa);
    void onError();
}
