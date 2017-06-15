package mz.uem.inovacao.fiscaisapp.cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import mz.uem.inovacao.fiscaisapp.entities.User;
import mz.uem.inovacao.fiscaisapp.listeners.GetFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.listeners.InitializeAppListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveFileListener;
import mz.uem.inovacao.fiscaisapp.listeners.SaveObjectListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignInListener;
import mz.uem.inovacao.fiscaisapp.listeners.SignUpListener;
import mz.uem.inovacao.fiscaisapp.listeners.UpdateObjectListener;
import mz.uem.inovacao.fiscaisapp.utils.QueryBuilder;

import java.io.File;
import java.util.List;

public class Cloud {

    public static String session;
    public static String dsp_url;
    public static boolean novo = false;
    private static int mensagensSize = 0;



    public static void initialize(InitializeAppListener initializeAppListener){
        InitializeAppTask initializeAppTask = new InitializeAppTask();
        initializeAppTask.setInitializeAppListener(initializeAppListener);
        initializeAppTask.execute();

    }

    public static void signIn(String userName, String password, SignInListener listener){

        SignInTask signInTask = new SignInTask();
        signInTask.setSignInListener(listener);
        signInTask.setUserName(userName);
        signInTask.setPassword(password);
        signInTask.execute();
    }

    public static void signUp(User user, SignUpListener signUpListener){

        SignUpTask signUpTask = new SignUpTask();
        signUpTask.setSignUpListener(signUpListener);
        signUpTask.setUser(user);
        signUpTask.execute();
    }

    public static void saveObject(Object cloudObject, SaveObjectListener saveObjectListener){

        SaveObjectTask saveObjectTask = new SaveObjectTask();
        saveObjectTask.setCloudObject(cloudObject);
        saveObjectTask.setSaveObjectListener(saveObjectListener);
        saveObjectTask.execute();
    }

    public static void getObject(){

    }
    public static void getObjects(String objectClassName, QueryBuilder queryBuilder, TypeReference typeReference, GetObjectsListener getObjectsListener){
        GetObjectsTask getObjectsTask = new GetObjectsTask();
        getObjectsTask.setObjectClassName(objectClassName);
        getObjectsTask.setTypeReference(typeReference);
        getObjectsTask.setQueryBuilder(queryBuilder);
        getObjectsTask.setGetObjectsListener(getObjectsListener);
        getObjectsTask.execute();
    }



    public static void saveOcurrence(){

    }







    public static void saveFile(File imageFile, String name, SaveFileListener saveFileListener){
        SaveFileTask saveFileTask = new SaveFileTask();
        saveFileTask.setFile(imageFile);
        saveFileTask.setFileName(name);
        saveFileTask.setSaveFileListener(saveFileListener);
        saveFileTask.execute();

    }

    public static void getFile(String filePath, GetFileListener getFileListener){

        GetFileTask getFileTask = new GetFileTask();
        getFileTask.setFilePath(filePath);
        getFileTask.setGetFileListener(getFileListener);
        getFileTask.execute();

    }



    public static void updateObject(Object object, int objectCode, UpdateObjectListener updateObjectListener){
        UpdateObjectTask updateObjectTask = new UpdateObjectTask();
        updateObjectTask.setCloudObject(object);
        updateObjectTask.setUpdateObjectListener(updateObjectListener);
        updateObjectTask.setCode(objectCode);
        updateObjectTask.execute();
    }


}


