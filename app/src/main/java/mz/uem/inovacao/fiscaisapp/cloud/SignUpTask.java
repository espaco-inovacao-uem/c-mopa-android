package mz.uem.inovacao.fiscaisapp.cloud;

import android.os.AsyncTask;
import android.util.Log;

import mz.uem.inovacao.fiscaisapp.dreamfactory.model.RecordsResponse;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.listeners.SignUpListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;


/**
 * Created by Felix Barros on 12/02/2015.
 */
public class SignUpTask extends AsyncTask<Void, Void, RecordsResponse> {
    private String errorMsg;
    private SignUpListener signUpListener;
    private User user;

    public void setUser(User user){
        this.user = user;
    }

    public void setSignUpListener(SignUpListener signUpListener){
        this.signUpListener = signUpListener;
    }

    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    @Override
    protected RecordsResponse doInBackground(Void... params) {

        DbsApi sqlApi = new DbsApi();
        sqlApi.setBasePath(AppConstants.DSP_URL + AppConstants.DSP_URL_SUFIX);
        sqlApi.addHeader("X-DreamFactory-Application-Name", AppConstants.APP_NAME);
        sqlApi.addHeader("X-DreamFactory-Session-Token", Cloud.session);
        try {
				/* You cant pass an id in this method just yet hmmm*/

            RecordsResponse id = sqlApi.createRecord(AppConstants.USER_TABLE, user, null, null, null,null);

           // log(resultRecord.toString());
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error",e.getMessage()) ;
            errorMsg = e.getMessage();
        }
        return null;
    }
    @Override
    protected void onPostExecute(RecordsResponse record) {
        //progressDialog.cancel();
        Log.d("Super man","right") ;
        if(record != null){

            signUpListener.success(record.getRecord().get(0));

        }else {
            //Toast.makeText(ToDoDemoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            Log.d("RegistUserTask","Hiper falhanco") ;
            signUpListener.error(errorMsg);
        }
    }
}