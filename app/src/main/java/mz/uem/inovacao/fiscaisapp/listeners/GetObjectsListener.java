package mz.uem.inovacao.fiscaisapp.listeners;

import java.util.List;

/**
 * Created by Felix Barros on 20/02/2015.
 */
public interface GetObjectsListener {

    void success(List<?> lista);
    void error(String error);

}
