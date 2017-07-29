package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.RecordsResponse;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Felix Barros on 12/02/2015.
 */
public class SaveObjectTask extends AsyncTask<Void, Void, RecordsResponse> {
    private String errorMsg;
    private SaveObjectListener saveObjectListener;
    private Object cloudObject;
    private String objectClassName;
    private TypeReference typeReference;

    public void setTypeReference(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    public void setObjectClassName(String objectClassName) {
        this.objectClassName = objectClassName;
    }

    public void setSaveObjectListener(SaveObjectListener saveObjectListener) {
        this.saveObjectListener = saveObjectListener;
    }

    public void setCloudObject(Object cloudObject) {
        this.cloudObject = cloudObject;
    }



    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    @Override
    protected RecordsResponse doInBackground(Void... params) {

        DbsApi dbApi = new DbsApi();
        dbApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        dbApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        dbApi.addHeader("X-DreamFactory-Api-Key", AppConstants.API_KEY);
        dbApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);
        try {

            /*
            String classeName = cloudObject.getClass().getSimpleName().toLowerCase() ;
            switch (classeName){
                case "activity":
                    Activity activity = (Activity) cloudObject ;
                    ActivityRequest a = new ActivityRequest(activity.isanounmous(),activity.getPicture(),activity.getDescription(),activity.getUserCode(),activity.getLocation_code(),activity.getOperationDate());
                    cloudObject = a ;
                    break;
                default:
            }
            */

            RecordsResponse recordsResponse = new RecordsResponse();
            List<Object> objects = new ArrayList<>();
            objects.add(cloudObject);
            recordsResponse.setRecord(objects);



            Log.e("Nome da clasee", cloudObject.getClass().getSimpleName().toLowerCase());
            RecordsResponse code = dbApi.createRecord(cloudObject.getClass().getSimpleName().toLowerCase(), recordsResponse.getRecord(), null, null, null,"*");
            return code;

        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return null;
    }
    @Override
    protected void onPostExecute(RecordsResponse record) {

        if(record != null){
            Log.e("O id",record.getRecord().get(0)+"");
            saveObjectListener.success(record.getRecord().get(0));

        }else {
            //Toast.makeText(ToDoDemoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            saveObjectListener.error(errorMsg);
        }
    }
}