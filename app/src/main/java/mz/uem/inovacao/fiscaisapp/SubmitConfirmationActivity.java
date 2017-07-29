package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Validacao;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.MyCameraUtils;

public class SubmitConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup formPicture;

    private ImageView imageViewPicture;
    private ViewGroup layoutPictureHint;
    private Spinner spinnerEstado;
    private EditText editTextDescricao;
    private Button buttonSave;

    private MyCameraUtils myCameraUtils;
    private Uri pictureUri;
    private MaterialDialog savingProgressDialog;

    private Validacao validacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_confirmation);

        formPicture = (ViewGroup) findViewById(R.id.formPicture);
        formPicture.setOnClickListener(this);

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
        layoutPictureHint = (ViewGroup) findViewById(R.id.layoutPictureHint);

        spinnerEstado = (Spinner) findViewById(R.id.spinnerConfirmation);
        editTextDescricao = (EditText) findViewById(R.id.editTextDescription);

        buttonSave = (Button) findViewById(R.id.buttonConfirm);
        buttonSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == formPicture) {

            myCameraUtils = new MyCameraUtils(this);
            myCameraUtils.showPickMediaDialog();
            return;
        }

        if (view == buttonSave) {

            showSavingProgressDialog();
            createValidacaoFromFields();
            uploadPicture(); //kickstarts cascade of save events. check listeners
        }
    }

    private void showSavingProgressDialog() {

        savingProgressDialog = new MaterialDialog.Builder(this)
                .title("Salvando Validação")
                .content("Por favor aguarde...")
                .cancelable(false)
                .progress(true, 0).build();

        savingProgressDialog.show();
    }

    private void hideSavingProgressDialog() {
        savingProgressDialog.dismiss();
        savingProgressDialog = null;
    }

    private void showSaveResultDialog(final boolean wasSuccessful, String errorMsg) {

        String title, content;

        if (wasSuccessful) {
            title = "Sucesso";
            content = "A validação foi salva com sucesso";
        } else {
            title = "Erro";
            content = errorMsg;
        }
        new MaterialDialog.Builder(this).title(title).content(content)
                .neutralText("OK").neutralColorRes((R.color.black))
                .onNeutral(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (wasSuccessful) {

                            finish();
                        }

                    }
                })
                .build().show();

    }

    private void uploadPicture() {

        if (pictureUri != null) {

            Toast.makeText(this, "Salvando imagem...", Toast.LENGTH_SHORT).show();
            Cloud.uploadMedia(pictureUri, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                    Toast.makeText(SubmitConfirmationActivity.this, "Upload com sucesso: " + downloadUrl,
                            Toast.LENGTH_SHORT).show();

                    validacao.setFotoUrl(downloadUrl);
                    saveValidacao();

                }

            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SubmitConfirmationActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                    hideSavingProgressDialog();
                    showSaveResultDialog(false, "Erro ao carregar imagem. Tente novamente");
                    e.printStackTrace();
                }

            }, new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                }
            });

        } else {//user didn't choose or create picture, so save without it

            Toast.makeText(SubmitConfirmationActivity.this, "Sem imagem... Saltando", Toast.LENGTH_SHORT).show();
            saveValidacao();
        }
    }

    private void createValidacaoFromFields() {

        String descricao = editTextDescricao.getText().toString();
        Date data = new Date();
        String estado = (String) spinnerEstado.getSelectedItem();

        validacao = new Validacao(data, descricao, estado, null, Cache.pedidoValidacao);

    }

    private void saveValidacao() {

        Cloud.saveValidacao(validacao, new SaveObjectListener() {

            @Override
            public void success(Object object) {

                hideSavingProgressDialog();
                showSaveResultDialog(true, null);

            }

            @Override
            public void error(String error) {

                hideSavingProgressDialog();
                showSaveResultDialog(false, "Erro ao salvar validaçao. Tente novamente");
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }

        return false;
    }
}
