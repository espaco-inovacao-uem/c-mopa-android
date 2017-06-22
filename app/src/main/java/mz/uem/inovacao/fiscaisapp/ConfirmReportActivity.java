package mz.uem.inovacao.fiscaisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ConfirmReportActivity extends AppCompatActivity {

    private RecyclerView recyclerViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_report);

        this.recyclerViewResults = (RecyclerView) findViewById(R.id.recyclerViewResults);

    }
}
