package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;

import net.danlew.android.joda.JodaTimeAndroid;

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

import static mz.uem.inovacao.fiscaisapp.database.Cache.equipa;
import static mz.uem.inovacao.fiscaisapp.database.Cache.fiscal;
import static mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil.mapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private ViewGroup formLogin, formChangePassword;
    private Button buttonContinue;

    private EditText editTextPhoneNumber, editTextPassword, editTextNewPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        Toast.makeText(this, "Initializing cloud", Toast.LENGTH_LONG).show();
        Cloud.initialize(new InitializeAppListener() {

            @Override
            public void success() {
                Toast.makeText(LoginActivity.this, "Dreamfactory: inicializado com sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(String error) {
                Toast.makeText(LoginActivity.this, "Dreamfactory: Erro ao inicializar" + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        //login clicked

        if (formLogin.getVisibility() == View.VISIBLE) { //button login

            String phoneNumber = editTextPhoneNumber.getText().toString();
            String password = editTextPassword.getText().toString();

            login(phoneNumber, password);

        } else { // button save new password

            String newPassword = editTextNewPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (newPassword.equals(confirmPassword)) {

                saveNewPassword(newPassword, updatePasswordListener);

            } else {

                toast("Introduza a mesma palavra-passe nos dois campos");
            }
        }


    }

    /* CLOUD OPERATIONS*/

    private void login(String phoneNumber, String password) {

        Cloud.signIn(phoneNumber, password, signInListener);
    }

    private SignInListener signInListener = new SignInListener() {


        @Override
        public void success(User user) {

            Cache.user = user;
            fetchEquipaFiscal();

            if (isFirstLogin(user)) {

                formLogin.setVisibility(View.GONE);
                formChangePassword.setVisibility(View.VISIBLE);

            } else {

                saveToUserSession(user);
                openMainActivity();
                finish();
            }
        }

        @Override
        public void error(String error) {

            toast("Erro ao fazer login: " + error);
        }
    };

    private UpdateObjectListener updatePasswordListener = new UpdateObjectListener() {

        @Override
        public void success(long id) {

            saveToUserSession(Cache.user);

            openMainActivity();
            toast("Palavra-passe actualizada com sucesso");
            finish();
        }

        @Override
        public void error(String error) {
            toast("Erro ao actualizar password" + error);
        }
    };

    private void saveNewPassword(String newPassword, UpdateObjectListener updatePasswordListener) {

        //update password e password_expire fields
        Cloud.updatePassword(Cache.user, newPassword, updatePasswordListener);
    }

    private boolean isFirstLogin(User user) {

        return user.isPasswordExpired();

    }

    private void fetchEquipaFiscal() {

        Cloud.getFiscalFromUser(Cache.user, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                fiscal = (Fiscal) lista.get(0);

                Log.d("Fiscal", fiscal.getApelido());

                List<?> equipasHash = fiscal.getServerEquipas();//lista de equipas. na Pratica é apenas 1.
                Log.d("Fiscal", "equipas: " + equipasHash);

                List<?> equipas = mapper.convertValue(equipasHash, new TypeReference<List<Equipa>>() {
                });

                equipa = (Equipa) equipas.get(0);
                Log.d("Equipa", equipa + "");
            }

            @Override
            public void error(String error) {

                Log.e("Fiscal", error);
                Toast.makeText(LoginActivity.this, "Erro ao buscar FISCAL:" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* UI OPERATIONS */
    private void displayErrorMessage() {

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
