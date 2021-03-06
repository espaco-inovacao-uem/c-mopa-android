package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mz.uem.inovacao.fiscaisapp.dreamfactory.model.RecordsResponse;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.listeners.SignInListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;
import mz.uem.inovacao.fiscaisapp.utils.BCrypt;

import java.util.List;

class SignInTask extends AsyncTask<Void, RecordsResponse, RecordsResponse> {
    private String errorMsg;
    private SignInListener signInListener;
    private String userName;
    private String password;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignInListener(SignInListener signInListener){
        this.signInListener = signInListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected RecordsResponse doInBackground(Void... params) {
        Log.d("Cloud","SignInTask doInBackground() started");

        DbsApi dbApi = new DbsApi();
        dbApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        dbApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        dbApi.addHeader("X-DreamFactory-Api-Key", AppConstants.API_KEY);
        dbApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);
        try {
            Log.d("Cloud","Entra no try");
            RecordsResponse records = dbApi.getRecords(AppConstants.USER_TABLE,null,"username%20%3D%20"+userName,-1,-1,null,null,false,false,null,null,true,null);
            //log(records.toString());
            return records;
        } catch (Exception e) {
            Log.d("Cloud","Entra no catch");
            e.printStackTrace();
            errorMsg = e.getMessage();
        }

        Log.d("Cloud","SignInTask doInBackground() finished");
        return null;
    }
    @Override
    protected void onPostExecute(RecordsResponse records) {

        Log.d("Cloud","SignInTask onPostExecute() started");
        if(records == null) {
            signInListener.error(AppConstants.ERROR_NO_INTERNET_CONNECTION);
            return;
        }

        if(records.getRecord() != null){
            //User user = (User) records.getRecord().get(0);

           ObjectMapper mapper = new ObjectMapper();
           List<?> users = mapper.convertValue(records.getRecord(), new TypeReference<List<User>>() { });
           if(users.size() > 0){
               User user = (User) users.get(0);
               //Log.d("ola",user.getUsername()+"");

               Log.d("Cloud","SignInTask starting BCrypt decryption");
               if(BCrypt.checkpw(password, user.getPassword())){
                   //Log.d("Pass certa",user.getUsername()+"");
                   //Cloud.setCurrenteDador(user);
                   Log.d("Cloud","SignInTask finished BCrypt decryption");
                   signInListener.success(user);

               }
               else {
                   Log.d("Cloud","SignInTask onPostExecute() finished");
                   signInListener.error(AppConstants.ERROR_WRONG_PASSWORD);
               }

           }else{
               Log.d("Cloud","SignInTask onPostExecute() finished");
               signInListener.error(AppConstants.ERROR_USER_NOT_FOUND);
           }

        }
    }
}