package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Categoria;
import mz.uem.inovacao.fiscaisapp.entities.Contentor;
import mz.uem.inovacao.fiscaisapp.entities.Distrito;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.mopa.ApiMOPA;
import mz.uem.inovacao.fiscaisapp.utils.MyCameraUtils;

public class NewReportActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup formBasicInfo, formPicture, formDescription;

    private ArrayList<Categoria> categorias;
    private ArrayList<Distrito> distritos;
    private ArrayList<Contentor> contentores;
    private Ocorrencia ocorrencia;

    /*FormBasicInfo views*/
    private Spinner spinnerDistrito, spinnerBairro, spinnerCategoria,
            spinnerContentor;

    private EditText editTextQuarteirao;

    /*FormPicture views*/
    private ImageView imageViewPicture;
    private ViewGroup layoutPictureHint;

    /*FormDescription views*/
    private EditText editTextDescricao;

    private Button buttonNext;

    private MyCameraUtils myCameraUtils;
    private Uri pictureUri;

    private MaterialDialog savingProgressDialog;
    private ApiMOPA mopa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        updateTitle("Reportar - Passo 1");

        findViews();

        mopa = new ApiMOPA(this);

        requestDistritos();
        requestCategories();
    }

    private void findViews() {

        formBasicInfo = (ViewGroup) findViewById(R.id.formBasicInfo);
        formPicture = (ViewGroup) findViewById(R.id.formPicture);
        formDescription = (ViewGroup) findViewById(R.id.formDescription);

        spinnerDistrito = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerBairro = (Spinner) findViewById(R.id.spinnerNeighborhood);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerReportType);
        editTextQuarteirao = (EditText) findViewById(R.id.spinnerHouseBlock);
        spinnerContentor = (Spinner) findViewById(R.id.spinnerContainer);

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
        layoutPictureHint = (ViewGroup) findViewById(R.id.layoutPictureHint);

        editTextDescricao = (EditText) findViewById(R.id.editTextDescription);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        formPicture.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        spinnerDistrito.setOnItemSelectedListener(listenerSpinnerDistrito);
        spinnerBairro.setOnItemSelectedListener(listenerSpinnerBairro);
        spinnerCategoria.setOnItemSelectedListener(listenerSpinnerCategorias);
    }

    private void requestDistritos() {

        mopa.fetchDistritos(new ApiMOPA.GetResponseListener<Distrito>() {

            @Override
            public void onSuccess(ArrayList<Distrito> distritos) {

                NewReportActivity.this.distritos = distritos;

                ArrayAdapter<Distrito> adapter = new ArrayAdapter<>(NewReportActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, distritos);

                spinnerDistrito.setAdapter(adapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void requestCategories() {

        mopa.fetchCategories(new ApiMOPA.GetResponseListener<Categoria>() {

            @Override
            public void onSuccess(ArrayList<Categoria> categorias) {

                NewReportActivity.this.categorias = categorias;

                ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(NewReportActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, categorias);

                spinnerCategoria.setAdapter(adapter);
            }

            @Override
            public void onError() {


            }
        });
    }

    private void requestContentores(String bairro) {

        mopa.fetchContentores(bairro, new ApiMOPA.GetResponseListener<Contentor>() {

            @Override
            public void onSuccess(ArrayList<Contentor> contentores) {

                NewReportActivity.this.contentores = contentores;

                ArrayAdapter<Contentor> adapter = new ArrayAdapter<>(NewReportActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, contentores);

                spinnerContentor.setAdapter(adapter);
            }

            @Override
            public void onError() {

            }
        });
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

                showSavingProgressDialog();
                createOcorrenciaFromFields();
                uploadPicture();//kickstarts cascade of save events. check listeners

            }
        } else if (view == formPicture) {

            myCameraUtils = new MyCameraUtils(this);
            myCameraUtils.showPickMediaDialog();
        }
    }

    private void createOcorrenciaFromFields() {

        Distrito distrito = distritos.get(spinnerDistrito.getSelectedItemPosition());
        String bairro = distrito.getBairros().get(spinnerBairro.getSelectedItemPosition());
        Categoria categoria = categorias.get(spinnerCategoria.getSelectedItemPosition());
        //Date data = DateTime.now().toDate();
        String descricao = editTextDescricao.getText().toString();
        String estado = "valido";

        Toast.makeText(this, DateTime.now().toString(), Toast.LENGTH_SHORT).show();
        if (categoria.requiresContentor()) {

            Contentor contentor = (Contentor) spinnerContentor.getSelectedItem();
            ocorrencia = new Ocorrencia(distrito, bairro, categoria.getNome(), contentor.getNome(),
                    DateTime.now().toString(), descricao, estado, Cache.equipa);
        } else {
            String quarteirao = editTextQuarteirao.getText().toString();
            ocorrencia = new Ocorrencia(distrito, bairro, categoria.getNome(), null
                    , DateTime.now().toString(), descricao + ". Quarteirao: " + quarteirao, estado, Cache.equipa);
        }


    }

    private Categoria getSelectedCategoria() {

        return categorias.get(spinnerCategoria.getSelectedItemPosition());
    }

    private Contentor getSelectedContentor() {

        return contentores.get(spinnerContentor.getSelectedItemPosition());
    }


    private void saveOcorrencia() {

        new ApiMOPA(NewReportActivity.this).saveOcorrencia(ocorrencia,
                getSelectedCategoria(), getSelectedContentor(), Cache.fiscal, new ApiMOPA.SaveResponseListener<Ocorrencia>() {

                    @Override
                    public void onSuccess(Ocorrencia mopaOcorrencia) {

                        Toast.makeText(NewReportActivity.this, "Salvo no MOPA. Salvando no nosso server",
                                Toast.LENGTH_SHORT).show();

                        ocorrencia.setCodigo(mopaOcorrencia.getCodigo());
                        Cloud.saveOcorrencia(ocorrencia, cloudResponseListener);

                        Log.d("MOPA", "codigo= " + ocorrencia.getCodigo());
                    }

                    @Override
                    public void onError() {
                        hideSavingProgressDialog();
                        showSaveResultDialog(false, "Erro ao salvar ocorrencia no Mopa. Tente novamente");
                    }
                });

    }

    private SaveObjectListener cloudResponseListener = new SaveObjectListener() {

        @Override
        public void success(Object object) {
            hideSavingProgressDialog();
            showSaveResultDialog(true, null);
        }

        @Override
        public void error(String error) {
            hideSavingProgressDialog();
            showSaveResultDialog(false, "Erro ao salvar ocorrencia no nosso server. Tente novamente");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (myCameraUtils.isResultInOurDirectory()) {

                File compressedImage = myCameraUtils.compressImage(myCameraUtils.getPictureTaken());
                pictureUri = Uri.fromFile(compressedImage);

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

    private void showSavingProgressDialog() {

        savingProgressDialog = new MaterialDialog.Builder(this)
                .title("Salvando ocorrência")
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
            content = "A ocorrência foi salva com sucesso";
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
                .canceledOnTouchOutside(false)
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

                    Toast.makeText(NewReportActivity.this, "Upload com sucesso: " + downloadUrl, Toast.LENGTH_SHORT).show();

                    ocorrencia.setUrlImagem(downloadUrl);
                    saveOcorrencia();
                }

            }, new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(NewReportActivity.this, "Failure", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(NewReportActivity.this, "Sem imagem... Saltando", Toast.LENGTH_SHORT).show();
            saveOcorrencia();
        }
    }

    @Override
    public void onBackPressed() {

        //TODO: Implement going back to previous forms
        super.onBackPressed();
    }

    AdapterView.OnItemSelectedListener listenerSpinnerDistrito = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Distrito distrito = distritos.get(position);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewReportActivity.this,
                    R.layout.support_simple_spinner_dropdown_item, distrito.getBairros());

            spinnerBairro.setAdapter(adapter);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener listenerSpinnerBairro = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            Distrito distrito = distritos.get(spinnerDistrito.getSelectedItemPosition());
            String bairro = distrito.getBairros().get(spinnerBairro.getSelectedItemPosition());

            requestContentores(bairro);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener listenerSpinnerCategorias = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Categoria categoria = categorias.get(position);

            if (categoria.requiresContentor()) {

                spinnerContentor.setVisibility(View.VISIBLE);
                editTextQuarteirao.setVisibility(View.GONE);

            } else {

                spinnerContentor.setVisibility(View.GONE);
                editTextQuarteirao.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
