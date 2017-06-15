package mz.uem.inovacao.fiscaisapp.cloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import mz.uem.inovacao.fiscaisapp.dreamfactory.api.FilesApi;
import mz.uem.inovacao.fiscaisapp.dreamfactory.client.ApiException;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.FileResponse;
import mz.uem.inovacao.fiscaisapp.listeners.GetFileListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;


/**
 * Created by SaNogueira on 17/03/2015.
 */
public class GetFileTask extends AsyncTask<Void, FileResponse ,FileResponse> {

    private String filePath ;
    private GetFileListener getFileListener;

    public void setGetFileListener(GetFileListener getFileListener) {
        this.getFileListener = getFileListener;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    protected FileResponse doInBackground(Void... params) {

        FilesApi fileApi = new FilesApi();
        fileApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        fileApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        fileApi.setBasePath(Cloud.dsp_url);
        String containerName = AppConstants.CONTAINER_NAME;
        String filePathOnServer = AppConstants.FOLDER_NAME;
        try {
            FileResponse resp = fileApi.getFile(AppConstants.CONTAINER_NAME,filePath, true,true,false);

            // parse file list
            return resp;
        } catch (ApiException e) {
            e.printStackTrace();
            //errorMsg = e.getMessage();
        }
        return null;

    }

    @Override
    protected void onPostExecute(FileResponse fileResponse) {

        if(fileResponse.getContent() != null){
            byte[] bitmapdata = fileResponse.getContent();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            getFileListener.success(bitmap);
        }

        else{
            getFileListener.error("Falha");
        }

    }
}
