package mz.uem.inovacao.fiscaisapp.mopa;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;

/**
 * Created by MauroBanze on 6/26/17.
 */

public interface OccorrenciasResponseListener {

    void onSuccess(ArrayList<Ocorrencia> ocorrencias);
    void onError();
}
