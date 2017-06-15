package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;

import mz.uem.inovacao.fiscaisapp.dreamfactory.model.RecordResponse;
import mz.uem.inovacao.fiscaisapp.listeners.UpdateObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;


/**
 * Created by Felix Barros on 12/02/2015.
 */
public class UpdateObjectTask extends AsyncTask<Void, Void, RecordResponse> {
    private String errorMsg;
    private UpdateObjectListener updateObjectListener;
    private Object cloudObject;
    private int code;

    public void setCode(int code){
        this.code = code ;
    }


    public void setUpdateObjectListener(UpdateObjectListener updateObjectListener) {
        this.updateObjectListener = updateObjectListener;
    }

    public void setCloudObject(Object cloudObject) {
        this.cloudObject = cloudObject;
    }



    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    @Override
    protected RecordResponse doInBackground(Void... params) {

        DbsApi sqlApi = new DbsApi();
        sqlApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);
        sqlApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        sqlApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        try {


            RecordResponse record = sqlApi.updateRecord(cloudObject.getClass().getSimpleName().toLowerCase(), ""+code, cloudObject, null, null, null, null);
            return record;

        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return null;
    }
    @Override
    protected void onPostExecute(RecordResponse record) {

        if(record != null){

            updateObjectListener.success(record.getCode());

        }else {
            //Toast.makeText(ToDoDemoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            updateObjectListener.error(errorMsg);
        }
    }
}