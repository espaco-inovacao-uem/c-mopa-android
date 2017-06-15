package mz.uem.inovacao.fiscaisapp.listeners;

import android.graphics.Bitmap;

/**
 * Created by SaNogueira on 17/03/2015.
 */
public interface GetFileListener {

    void success(Bitmap bitmap);
    void error(String error);

}
