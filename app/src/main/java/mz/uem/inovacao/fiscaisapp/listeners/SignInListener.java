package mz.uem.inovacao.fiscaisapp.listeners;


import mz.uem.inovacao.fiscaisapp.entities.User;

/**
 * Created by Felix Barros on 11/02/2015.
 */
public interface SignInListener {

    void success(User user);
    void error(String error);

}
