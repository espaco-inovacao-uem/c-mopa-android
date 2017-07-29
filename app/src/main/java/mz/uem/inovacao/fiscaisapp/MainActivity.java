package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mz.uem.inovacao.fiscaisapp.database.Cache;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View layoutNewReport, layoutConfirmReport, layoutHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutNewReport = findViewById(R.id.layoutNewReport);
        layoutConfirmReport = findViewById(R.id.layoutConfirmReport);
        layoutHistory = findViewById(R.id.layoutHistory);

        layoutNewReport.setOnClickListener(this);
        layoutConfirmReport.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.layoutNewReport: {
                openNewReportActivity();
                break;
            }
            case R.id.layoutConfirmReport: {
                openConfirmActivity();
                break;
            }
            case R.id.layoutHistory: {
                openHistoryActivity();
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.action_logout) {

            Cache.clearAll();

            openLoginActivity();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openNewReportActivity() {
        Intent intent = new Intent(this, NewReportActivity.class);
        startActivity(intent);
    }

    private void openConfirmActivity() {
        Intent intent = new Intent(this, MyRequestsActivity.class);
        startActivity(intent);
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
