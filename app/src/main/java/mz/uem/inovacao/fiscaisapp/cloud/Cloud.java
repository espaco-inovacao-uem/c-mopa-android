package mz.uem.inovacao.fiscaisapp.cloud;

import android.net.Uri;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import mz.uem.inovacao.fiscaisapp.dreamfactory.api.UserApi;
import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.entities.Validacao;
import mz.uem.inovacao.fiscaisapp.listeners.GetFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetPedidosValidacaoListener;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignInListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignUpListener;
import mz.uem.inovacao.fiscaisapp.listeners.UpdateObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.BCrypt;
import mz.uem.inovacao.fiscaisapp.utils.QueryBuilder;

import java.io.File;
import java.util.Date;
import java.util.List;

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

    public static void saveValidacao(Validacao validacao, SaveObjectListener listener){

        validacao.setServerPedidoValidacao(validacao.getPedidoValidacao().getId());
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

    public static void getPedidosValidacao(Equipa equipa, GetObjectsListener listener) {

        Cloud.getObjects("Pedido", new FilterBuilder("equipa_id", equipa.getId() + ""),
                new TypeReference<List<Pedido>>() {
                }, listener);
    }

    public static void getFiscalOcorrencias(Fiscal fiscal, GetObjectsListener listener){

        

    }

    public static void getFiscalValidacoes(Fiscal fiscal, GetObjectsListener listener){


    }
}


