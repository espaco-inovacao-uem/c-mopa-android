package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View layoutNewReport, layoutConfirmReport, layoutHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Initializing cloud", Toast.LENGTH_LONG).show();
        Cloud.initialize(new InitializeAppListener() {

            @Override
            public void success() {
                Toast.makeText(MainActivity.this, "Cloud: Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(String error) {
                Toast.makeText(MainActivity.this, "Cloud: Falha" + error, Toast.LENGTH_LONG).show();
            }
        });


        layoutNewReport = findViewById(R.id.layoutNewReport);
        layoutConfirmReport = findViewById(R.id.layoutConfirmReport);
        layoutHistory = findViewById(R.id.layoutHistory);

        layoutNewReport.setOnClickListener(this);
        layoutConfirmReport.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.layoutNewReport:{
                openNewReportActivity();
                break;
            }
            case R.id.layoutConfirmReport:{
                openConfirmActivity();
                break;
            }
            case R.id.layoutHistory:{
                break;
            }
        }
    }

    private void openNewReportActivity(){
        Intent intent = new Intent(this, NewReportActivity.class);
        startActivity(intent);
    }

    private void openConfirmActivity(){
        Intent intent = new Intent(this, ConfirmReportActivity.class);
        startActivity(intent);
    }

    private void openHistoryActivity(){
        Intent intent = new Intent(this, NewReportActivity.class);
        startActivity(intent);
    }
}
