package mz.uem.inovacao.fiscaisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"Begin",Toast.LENGTH_LONG).show();
        Cloud.initialize(new InitializeAppListener() {
            @Override
            public void success() {
                Toast.makeText(MainActivity.this,"Sucesso",Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(String error) {
                Toast.makeText(MainActivity.this,"Falha"+error,Toast.LENGTH_LONG).show();
            }
        });

    }
}
