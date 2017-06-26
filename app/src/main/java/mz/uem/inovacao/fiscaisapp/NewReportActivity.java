package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import mz.uem.inovacao.fiscaisapp.utils.MyCameraUtils;

public class NewReportActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup formBasicInfo, formPicture, formDescription;

    /*FormBasicInfo views*/
    private Spinner spinnerDistrict, spinnerNeighborhood, spinnerReportType, spinnerHouseBlock,
            spinnerContainer;

    /*FormPicture views*/
    private ImageView imageViewPicture;
    private ViewGroup layoutPictureHint;

    /*FormDescription views*/
    private EditText editTextDescription;

    private Button buttonNext;

    private MyCameraUtils myCameraUtils;
    private Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        updateTitle("Reportar - Passo 1");

        findViews();

    }

    private void findViews() {

        formBasicInfo = (ViewGroup) findViewById(R.id.formBasicInfo);
        formPicture = (ViewGroup) findViewById(R.id.formPicture);
        formDescription = (ViewGroup) findViewById(R.id.formDescription);

        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerNeighborhood = (Spinner) findViewById(R.id.spinnerNeighborhood);
        spinnerReportType = (Spinner) findViewById(R.id.spinnerReportType);
        spinnerHouseBlock = (Spinner) findViewById(R.id.spinnerHouseBlock);
        spinnerContainer = (Spinner) findViewById(R.id.spinnerContainer);

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
        layoutPictureHint = (ViewGroup) findViewById(R.id.layoutPictureHint);

        editTextDescription = (EditText) findViewById(R.id.editTextDescription);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        formPicture.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
    }

    /**
     * @return true if successful, false if reached end of forms
     */
    private boolean nextForm() {

        if (isVisible(formBasicInfo)) {
            hide(formBasicInfo);
            show(formPicture);
            updateTitle("Reportar - Passo 2");
            return true;

        } else if (isVisible(formPicture)) {
            hide(formPicture);
            show(formDescription);
            updateTitle("Reportar - Passo 3");
            updateMainButton("Salvar");
            return true;

        } else {//TODO: Incluir um 4o passo, que é o passo de sucesso ao gravar ou tentar novamente
            return false;
        }
    }

    private boolean isVisible(View v) {
        return v.getVisibility() == View.VISIBLE;
    }

    private void show(View v) {
        v.setVisibility(View.VISIBLE);
    }

    private void hide(View v) {

        v.setVisibility(View.GONE);
    }

    private void updateTitle(String newTitle) {

        getSupportActionBar().setTitle(newTitle);
    }

    private void updateMainButton(String newLabel) {

        buttonNext.setText(newLabel);
    }

    @Override
    public void onClick(View view) {

        if (view == buttonNext) {

            boolean success = nextForm();
            if (!success) {//reached end of form, should save
                Toast.makeText(this, "Fim do processo: Salvar report", Toast.LENGTH_SHORT).show();
            }
        } else if (view == formPicture) {

            myCameraUtils = new MyCameraUtils(this);
            myCameraUtils.showPickMediaDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (myCameraUtils.isResultInOurDirectory()) {

                pictureUri = Uri.fromFile(myCameraUtils.getPictureTaken());

            } else {

                pictureUri = data.getData();
            }

            showPicture();
        } else {
            Toast.makeText(this, "Falha ao retornar imagem", Toast.LENGTH_SHORT).show();
        }

    }

    private void showPicture() {

        layoutPictureHint.setVisibility(View.GONE);
        imageViewPicture.setVisibility(View.VISIBLE);

        Glide.with(this).load(pictureUri).fitCenter().
                into(imageViewPicture);
    }

    @Override
    public void onBackPressed() {

        //TODO: Implement going back to previous forms
        super.onBackPressed();
    }
}
