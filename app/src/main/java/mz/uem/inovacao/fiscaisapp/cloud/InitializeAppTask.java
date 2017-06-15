package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;
import android.util.Log;

import mz.uem.inovacao.fiscaisapp.dreamfactory.api.UserApi;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.Login;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.Session;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Felix Barros on 14/02/2015.
 */
public class InitializeAppTask extends AsyncTask<Void, Void, String> {

    private InitializeAppListener initializeAppListener;

    public void setInitializeAppListener(InitializeAppListener initializeAppListener) {
        this.initializeAppListener = initializeAppListener;
    }

    @Override
    protected String doInBackground(Void... params) {

        UserApi userApi = new UserApi();
        userApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        userApi.addHeader("X-DreamFactory-Api-Key", AppConstants.API_KEY);
        //headerParams.put("X-DreamFactory-Session-Token", sessionToken);
        userApi.setBasePath(AppConstants.DOMAIN+"/api/v2");
        Cloud.dsp_url = AppConstants.DOMAIN+"/api/v2" ;
        Login login = new Login();
        login.setEmail(AppConstants.EMAIL);
        login.setPassword(AppConstants.PWD);
        Log.e("No init","ops");
        try {

            Session session =	userApi.login(login);

            String session_id = session.getSession_id();
            Cloud.session = session_id;
            Log.i("Token",session_id);

        } catch (Exception e) {

            return e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //progressDialog.cancel();


        if (result !=null){
            String errorMsg = "";
            try {
                JSONObject jObj = new JSONObject(result);
                JSONArray jArray = jObj.getJSONArray("error");
                JSONObject obj = jArray.getJSONObject(0);
                errorMsg = obj.getString("message");
                //signInListener.error(errorMsg);
            } catch (JSONException e) {
                //errorMsg = result;
                if(result.contains("Unable to resolve host"))
                    initializeAppListener.error("404");
                else
                    initializeAppListener.error(e.getMessage());
            }
        }
        else {


            initializeAppListener.success();
        }
    }
    @Override
    protected void onPreExecute() {

    }
}
