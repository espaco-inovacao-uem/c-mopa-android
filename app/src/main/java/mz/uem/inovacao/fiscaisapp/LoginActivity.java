package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup formLogin, formChangePassword;
    private Button buttonContinue;

    private EditText editTextPhoneNumber, editTextPassword, editTextNewPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        formLogin = (ViewGroup) findViewById(R.id.loginForm);
        formChangePassword = (ViewGroup) findViewById(R.id.changePasswordForm);

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);

        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmNewPassword);

    }

    @Override
    public void onClick(View view) {

        //login clicked

        if (formLogin.getVisibility() == View.VISIBLE) { //button login

            String phoneNumber = editTextPhoneNumber.getText().toString();
            String password = editTextPassword.getText().toString();

            if (login(phoneNumber, password)) {

                if (isFirstLogin()) {

                    formLogin.setVisibility(View.GONE);
                    formChangePassword.setVisibility(View.VISIBLE);
                } else {
                    openMainActivity();

                }
            } else {

                toast("Dados de login incorrectos. Tente novamente");
            }


        } else { // button save new password

            String newPassword = editTextNewPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (newPassword.equals(confirmPassword)) {

                saveNewPassword();
                openMainActivity();

                toast("Palavra-passe actualizada com sucesso");
            } else {

                toast("Introduza a mesma palavra-passe nos dois campos");
            }
        }


    }

    /* CLOUD OPERATIONS*/

    private boolean login(String phoneNumber, String password) {

        return true;
    }

    private void saveNewPassword() {

    }

    private boolean isFirstLogin() {

        Random random = new Random();
        return random.nextBoolean();

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

    private void saveToUserSession() {

    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private static final String SESSION_PHONE_NUMBER = "SESSION_PHONE_NUMBER";
    private static final String SESSION_PASSWORD = "SESSION_PASSWORD";
    private static final String LAST_LOGIN_DATE = "LAST_LOGIN_DATE";

}
