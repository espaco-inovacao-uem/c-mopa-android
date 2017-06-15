package mz.uem.inovacao.fiscaisapp.listeners;

/**
 * Created by SaNogueira on 19/03/2015.
 */
public interface UpdateObjectListener {

    void success(long id);
    void error(String error);
}
