package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mz.uem.inovacao.fiscaisapp.dreamfactory.model.RecordsResponse;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import java.util.List;

class GetObjectsTask extends AsyncTask<Void, RecordsResponse, RecordsResponse> {
    private String errorMsg;
    private String objectClassName;
    private GetObjectsListener getObjectsListener;
    private TypeReference typeReference;
    private FilterBuilder filterBuilder;
    private int limit;
    private String order;


    public GetObjectsTask() {

        limit = -1;
        order = "";
    }

    public void setFilterBuilder(FilterBuilder filterBuilder) {
        this.filterBuilder = filterBuilder;
    }

    public void setGetObjectsListener(GetObjectsListener getObjectsListener) {
        this.getObjectsListener = getObjectsListener;
    }

    public void setTypeReference(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    public void setObjectClassName(String objectClassName) {
        this.objectClassName = objectClassName;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected RecordsResponse doInBackground(Void... params) {
        DbsApi dbApi = new DbsApi();
        dbApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        dbApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        dbApi.addHeader("X-DreamFactory-Api-Key", AppConstants.API_KEY);
        dbApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);
        try {
            RecordsResponse records = dbApi.getRecords(objectClassName.toLowerCase(), null, filterBuilder.build()
                    , limit, -1, order, null, false, false, null, null, true, "*");
            //log(records.toString());
            return records;
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(RecordsResponse records) {
        if (records != null) {

            ObjectMapper mapper = new ObjectMapper();

            List<?> objects = mapper.convertValue(records.getRecord(), typeReference);

            if (records.getRecord().size() >= 0) {
                getObjectsListener.success(objects);
                return;
            }
            getObjectsListener.error("");

        } else {
            getObjectsListener.error("Falha");
        }
    }
}