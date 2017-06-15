package mz.uem.inovacao.fiscaisapp.listeners;

/**
 * Created by Felix Barros on 11/02/2015.
 */
public interface SaveObjectListener {

    void success(Object object);

    //Recebe uma String de erro
    void error(String error);
}
