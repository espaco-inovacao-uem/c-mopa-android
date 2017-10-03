package mz.uem.inovacao.fiscaisapp.cloud;

import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil;
import mz.uem.inovacao.fiscaisapp.entities.Alocacao;
import mz.uem.inovacao.fiscaisapp.entities.Distrito;
import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.entities.Validacao;
import mz.uem.inovacao.fiscaisapp.listeners.CloudResponseListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignInListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignUpListener;
import mz.uem.inovacao.fiscaisapp.listeners.UpdateObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.BCrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil.mapper;

public class Cloud {

    public static String session;
    public static String dsp_url;
    public static boolean novo = false;
    private static int mensagensSize = 0;

    public static void initialize(InitializeAppListener initializeAppListener) {
        InitializeAppTask initializeAppTask = new InitializeAppTask();
        initializeAppTask.setInitializeAppListener(initializeAppListener);
        initializeAppTask.execute();

    }

    public static void signIn(String userName, String password, SignInListener listener) {

        SignInTask signInTask = new SignInTask();
        signInTask.setSignInListener(listener);
        signInTask.setUserName(userName);
        signInTask.setPassword(password);
        signInTask.execute();
    }

    public static void signUp(User user, SignUpListener signUpListener) {

        SignUpTask signUpTask = new SignUpTask();
        signUpTask.setSignUpListener(signUpListener);
        signUpTask.setUser(user);
        signUpTask.execute();
    }

    public static void saveObject(Object cloudObject, SaveObjectListener saveObjectListener) {

        SaveObjectTask saveObjectTask = new SaveObjectTask();
        saveObjectTask.setCloudObject(cloudObject);
        saveObjectTask.setSaveObjectListener(saveObjectListener);
        saveObjectTask.execute();
    }

    public static void getObject() {

    }

    public static void getObjects(String objectClassName, FilterBuilder filterBuilder, TypeReference typeReference, GetObjectsListener getObjectsListener) {
        GetObjectsTask getObjectsTask = new GetObjectsTask();
        getObjectsTask.setObjectClassName(objectClassName);
        getObjectsTask.setTypeReference(typeReference);
        getObjectsTask.setFilterBuilder(filterBuilder);
        getObjectsTask.setGetObjectsListener(getObjectsListener);
        getObjectsTask.execute();
    }


    public static void saveFile(File imageFile, String name, SaveFileListener saveFileListener) {
        SaveFileTask saveFileTask = new SaveFileTask();
        saveFileTask.setFile(imageFile);
        saveFileTask.setFileName(name);
        saveFileTask.setSaveFileListener(saveFileListener);
        saveFileTask.execute();

    }

    public static void getFile(String filePath, GetFileListener getFileListener) {

        GetFileTask getFileTask = new GetFileTask();
        getFileTask.setFilePath(filePath);
        getFileTask.setGetFileListener(getFileListener);
        getFileTask.execute();

    }


    public static void updateObject(Object object, int objectCode, UpdateObjectListener updateObjectListener) {
        UpdateObjectTask updateObjectTask = new UpdateObjectTask();
        updateObjectTask.setCloudObject(object);
        updateObjectTask.setUpdateObjectListener(updateObjectListener);
        updateObjectTask.setCode(objectCode);
        updateObjectTask.execute();
    }

    //****** Utility methods that make use of above core methods

    public static void updatePassword(User user, String newPassword, UpdateObjectListener updateObjectListener) {

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        user.setPasswordExpired(false);
        user.setPassword(hashedPassword);

        updateObject(user, (int) user.getId(), updateObjectListener);
    }


    public static void getFiscalFromUser(User user, GetObjectsListener listener) {

        Cloud.getObjects("Fiscal", new FilterBuilder("numero_telefone", user.getUsername()),
                new TypeReference<List<Fiscal>>() {
                }, listener);


    }

    public static void saveOcorrencia(Ocorrencia ocorrencia, SaveObjectListener listener) {

        ocorrencia.setEquipaForeignKey(ocorrencia.getEquipa().getId());
        ocorrencia.setDistritoForeignKey(ocorrencia.getDistrito().getId());

        saveObject(ocorrencia, listener);
    }

    public static void saveValidacao(Validacao validacao, SaveObjectListener listener) {

        validacao.setServerPedidoValidacao(validacao.getPedidoValidacao().getId());
        validacao.setServerEquipa(validacao.getEquipa().getId());
        Cloud.saveObject(validacao, listener);
    }

    public static void uploadMedia(Uri mediaUri, OnSuccessListener<UploadTask.TaskSnapshot> success,
                                   OnFailureListener failure, OnProgressListener<UploadTask.TaskSnapshot> progress) {

        StorageReference storage = FirebaseStorage.getInstance().getReference();

        StorageReference mediaFileReference = storage.child("imagens/" + new Date().toString());

        mediaFileReference.putFile(mediaUri)
                .addOnSuccessListener(success)
                .addOnFailureListener(failure)
                .addOnProgressListener(progress);

    }

    public static void getPedidosValidacao(ArrayList<Distrito> distritos, final CloudResponseListener<Pedido> listener) {

        ArrayList<String> idsDistritos = new ArrayList<>();

        for (Distrito distrito : distritos) {
            idsDistritos.add(distrito.getId() + "");
        }

        FilterBuilder filter = new FilterBuilder()
                .equalTo("validado", "false")
                .and()
                .open()
                .equalToOr("distrito_id", idsDistritos)
                .close();

        getObjects("Pedido", filter, new TypeReference<List<Pedido>>() {
        }, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                ArrayList<Pedido> pedidos = extractPedidosValidacao(lista);
                listener.success(pedidos);
            }

            @Override
            public void error(String error) {

                listener.error(error);
            }
        });
    }

    private static ArrayList<Pedido> extractPedidosValidacao(List<?> lista) {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {

            Pedido pedido = (Pedido) lista.get(i);

            Ocorrencia ocorrencia = JsonUtil.mapper.convertValue(pedido.getServerOcorrencia(),
                    new TypeReference<Ocorrencia>() {
                    });

            pedido.setOcorrencia(ocorrencia);
            pedidos.add(pedido);
        }

        return pedidos;
    }


    public static void updatePedidoValidacao(Pedido pedido, UpdateObjectListener listener) {
        updateObject(pedido, pedido.getId(), listener);

    }

    public static void getEquipaOcorrencias(Equipa equipa, GetObjectsListener listener) {

        DateTime now = DateTime.now();
        DateTime sevenDaysAgo = now.minusDays(7);

        String lowerDate = sevenDaysAgo.toString("yyyy-MM-dd");
        String upperDate = now.toString("yyyy-MM-dd");

        FilterBuilder filter = new FilterBuilder()
                .equalTo("equipa_id", equipa.getId() + "")
                .and().biggerOrEquals("data", lowerDate)
                .and().smallerOrEquals("data", upperDate);

        getObjects("Ocorrencia", filter, new TypeReference<List<Ocorrencia>>() {
        }, listener);

    }

    public static void getEquipaValidacoes(Equipa equipa, GetObjectsListener listener) {

        DateTime now = DateTime.now();
        DateTime sevenDaysAgo = now.minusDays(7);

        String lowerDate = sevenDaysAgo.toString("yyyy-MM-dd");
        String upperDate = now.toString("yyyy-MM-dd");

        FilterBuilder filter = new FilterBuilder()
                .equalTo("equipa_id", equipa.getId() + "")
                .and().biggerOrEquals("data_de_registo", lowerDate)
                .and().smallerOrEquals("data_de_registo", upperDate);

        getObjects("Validacao", filter, new TypeReference<List<Validacao>>() {
        }, listener);

    }

    public static void getAlocacoesHoje(Equipa equipa, final CloudResponseListener<Alocacao> listener) {

        DateTime now = DateTime.now();

        String today = now.toString("yyyy-MM-dd");
        String tomorrow = now.plusDays(1).toString("yyyy-MM-dd");

        Log.d("Alocacao", "procurando entre: " + today + " e " + tomorrow);

        /*FilterBuilder filter = new FilterBuilder()
                .equalTo("equipa_id", equipa.getId() + "")
                .and().biggerOrEquals("data_de_alocacao", today)
                .and().smallerOrEquals("data_de_alocacao", tomorrow);*/

        FilterBuilder filter = new FilterBuilder()
                .equalTo("equipa_id", equipa.getId() + "")
                .and().smallerOrEquals("data_inicio", today)
                .and().biggerOrEquals("data_fim", today);

        getObjects("Alocacao", filter, new TypeReference<List<Alocacao>>() {

        }, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                ArrayList<Alocacao> alocacoes = extractAlocacoes(lista);
                listener.success(alocacoes);
            }

            @Override
            public void error(String error) {
                listener.error(error);
            }
        });
    }

    private static ArrayList<Alocacao> extractAlocacoes(List<?> lista) {

        ArrayList<Alocacao> alocacoes = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {

            Alocacao alocacao = (Alocacao) lista.get(i);
            alocacoes.add(alocacao);

            Object serverDistrito = alocacao.getServerDistrito();
            Distrito distrito = mapper.convertValue(serverDistrito, new TypeReference<Distrito>() {
            });

            alocacao.setDistrito(distrito);
            Log.d("Alocacao", distrito.getNome());
        }

        return alocacoes;
    }

}


