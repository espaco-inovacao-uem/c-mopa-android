package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;

import mz.uem.inovacao.fiscaisapp.dreamfactory.api.FilesApi;
import mz.uem.inovacao.fiscaisapp.dreamfactory.client.ApiException;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.FileRequest;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.FileResponse;
import mz.uem.inovacao.fiscaisapp.listeners.SaveFileListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import java.io.File;

/**
 * Created by SaNogueira on 09/03/2015.
 */
public class SaveFileTask extends AsyncTask<Object, FileResponse, FileResponse> {
    private String errorMsg;
    private SaveFileListener saveFileListener;
    private File file;
    private String fileName ;
    private String url;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SaveFileListener getSaveFileListener() {
        return saveFileListener;
    }

    public void setSaveFileListener(SaveFileListener saveFileListener) {
        this.saveFileListener = saveFileListener;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    @Override
    protected FileResponse doInBackground(Object... params) {
//			FilesApiDeprecated fileApi = new FilesApiDeprecated();
        FilesApi fileApi = new FilesApi();
        fileApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        fileApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        fileApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);

        // where to upload on server
        String containerName = AppConstants.CONTAINER_NAME;
        String filePathOnServer = AppConstants.FOLDER_NAME + "/";


        FileRequest request = new FileRequest();
        request.setName(fileName);  // this will be stored file name on server
        request.setPath(file.getAbsolutePath());
        request.setContent_type("png");

        url = "ourmoz/"+fileName;

        try {
            FileResponse resp = fileApi.createFile(containerName, filePathOnServer, false, request);

            return resp;

        } catch (ApiException e) {
            e.printStackTrace();
            errorMsg = e.getMessage();

        }

        return null;
    }
    @Override
    protected void onPostExecute(FileResponse resp) {

        if(resp != null){ // success
            //Log.d("path",resp.getPath());
            saveFileListener.success(url);

        }else{ // some error show dialog
           // Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            saveFileListener.error("falha");
        }
    }
}

