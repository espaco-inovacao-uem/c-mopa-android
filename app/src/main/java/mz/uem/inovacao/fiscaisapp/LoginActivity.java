package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignInListener;
import mz.uem.inovacao.fiscaisapp.listeners.UpdateObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import static mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil.mapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private ViewGroup formLogin, formChangePassword;
    private Button buttonContinue;

    private EditText editTextPhoneNumber, editTextPassword, editTextNewPassword, editTextConfirmPassword;

    private MaterialDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Iniciar sessão");
        initializeCloud();

        formLogin = (ViewGroup) findViewById(R.id.loginForm);
        formChangePassword = (ViewGroup) findViewById(R.id.changePasswordForm);

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);
        buttonContinue.setOnLongClickListener(this);

        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmNewPassword);

    }

    private void initializeCloud() {

        showProgressDialog("Inicializando app...");
        Cloud.initialize(new InitializeAppListener() {

            @Override
            public void success() {

                Toast.makeText(LoginActivity.this, "Conectado com sucesso", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }

            @Override
            public void error(String error) {

                hideProgressDialog();
                showInitializeErrorDialog();
                Log.e("Dreamfactory", error);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (formLogin.getVisibility() == View.VISIBLE) { //button login

            String phoneNumber = editTextPhoneNumber.getText().toString();
            String password = editTextPassword.getText().toString();

            showProgressDialog("Iniciando sessão");
            login(phoneNumber, password);

        } else { // button save new password

            String newPassword = editTextNewPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (newPassword.equals(confirmPassword)) {

                showProgressDialog("Salvando palavra-passe");
                saveNewPassword(newPassword);

            } else {

                toast("Introduza a mesma palavra-passe nos dois campos");
            }
        }


    }

    /* CLOUD OPERATIONS*/

    /**
     * Triggers async flow
     *
     * @param phoneNumber
     * @param password
     */
    private void login(String phoneNumber, String password) {

        Cloud.signIn(phoneNumber, password, signInListener);
    }

    private SignInListener signInListener = new SignInListener() {


        @Override
        public void success(User user) {

            hideProgressDialog();

            Cache.user = user;

            if (user.isFirstLogin()) {

                formLogin.setVisibility(View.GONE);
                formChangePassword.setVisibility(View.VISIBLE);

            } else {

                showProgressDialog("Buscando sua equipa...");
                fetchFiscalAndEquipa();
                /*saveToUserSession(user);
                openMainActivity();
                finish();*/
            }
        }

        @Override
        public void error(String error) {

            hideProgressDialog();
            showLoginErrorDialog(error);
            Log.e("Login erro", error);
        }
    };

    /**
     * Part of async flow
     */
    private void saveNewPassword(String newPassword) {

        //update password e password_expire fields
        Cloud.updatePassword(Cache.user, newPassword, updatePasswordListener);
    }

    private UpdateObjectListener updatePasswordListener = new UpdateObjectListener() {

        @Override
        public void success(long id) {

            hideProgressDialog();
            showPasswordUpdatedDialog();

        }

        @Override
        public void error(String error) {

            hideProgressDialog();
            toast("Erro ao actualizar password" + error);
        }
    };

    /**
     * Method triggers async flow.
     */
    private void fetchFiscalAndEquipa() {

        Cloud.getFiscalFromUser(Cache.user, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                //extract fiscal object returned from server
                Cache.fiscal = (Fiscal) lista.get(0);

                List<?> equipasHash = Cache.fiscal.getServerEquipas();//lista de equipas. na Pratica é apenas 1.
                List<?> equipas = mapper.convertValue(equipasHash, new TypeReference<List<Equipa>>() {
                });

                List<?> equipasHash2 = Cache.fiscal.getServerEquipas2();
                List<?> equipas2 = mapper.convertValue(equipasHash2, new TypeReference<List<Equipa>>() {
                });

                if (equipas.isEmpty() && equipas2.isEmpty()) {// sem equipa

                    hideProgressDialog();
                    showNoEquipaAssignedErrorDialog();

                } else {

                    if (!equipas.isEmpty()) {
                        Cache.equipa = (Equipa) equipas.get(0);
                    } else {
                        Cache.equipa = (Equipa) equipas2.get(0);
                    }

                    Log.d("Equipa", Cache.equipa + "");

                    hideProgressDialog();
                    openMainActivity();
                    finish();
                }

            }

            @Override
            public void error(String error) {

                Log.e("Fiscal", error);
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, "Erro ao buscar FISCAL:" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* UI OPERATIONS */

    private void showProgressDialog(final String msg) {

        loginProgressDialog = new MaterialDialog.Builder(this)
                .content(msg)
                .progress(true, 0)
                .cancelable(false)
                .build();

        loginProgressDialog.show();
    }

    private void hideProgressDialog() {

        loginProgressDialog.dismiss();
        loginProgressDialog = null;
    }

    private void showInitializeErrorDialog() {

        new MaterialDialog.Builder(this)
                .title("Erro ao iniciar app")
                .content("Verifique a sua conexão à internet e tente novamente")
                .positiveText("Tentar novamente")
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        initializeCloud();
                    }
                })
                .negativeText("Sair da aplicação")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        finish();
                    }
                })
                .build().show();
    }

    private void showLoginErrorDialog(String errorMessage) {

        String dialogMessage = "";
        if (errorMessage.equals(AppConstants.ERROR_NO_INTERNET_CONNECTION))
            dialogMessage = "Verifique a sua conexão à internet";
        else
            dialogMessage = "Numero de telefone ou palavra-passe errados. Tente novamente.";

        new MaterialDialog.Builder(this)
                .title("Erro ao iniciar sessão")
                .content(dialogMessage)
                .positiveText("Ok")
                .build().show();

    }

    private void showNoEquipaAssignedErrorDialog() {

        new MaterialDialog.Builder(this)
                .title("Não pode continuar")
                .content("Não existe uma equipa associada. Contacte o administrador do MOPA")
                .positiveText("Sair da aplicação")
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .build().show();
    }

    private void showPasswordUpdatedDialog() {

        new MaterialDialog.Builder(this)
                .title("Sucesso")
                .content("Palavra-passe actualizada com sucesso")
                .positiveText("Continuar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        showProgressDialog("Buscando a sua equipa");
                        fetchFiscalAndEquipa();
                    }
                })
                .build().show();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /*LOCAL DB OPERATIONS*/

    /**
     * Uses SharedPreferences to determine whether user should login o
     */
    private void isUserSessionValid() {


    }

    private void saveToUserSession(User user) {

    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private static final String SESSION_PHONE_NUMBER = "SESSION_PHONE_NUMBER";
    private static final String SESSION_PASSWORD = "SESSION_PASSWORD";
    private static final String LAST_LOGIN_DATE = "LAST_LOGIN_DATE";

    @Override
    public boolean onLongClick(View view) {

        //DEBUG only option, pra que possa fazer login sem conexao com server
        openMainActivity();
        finish();
        return true;
    }
}
