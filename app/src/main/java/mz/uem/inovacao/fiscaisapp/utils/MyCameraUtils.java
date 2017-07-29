package mz.uem.inovacao.fiscaisapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.Calendar;

import mz.uem.inovacao.fiscaisapp.R;

/**
 *
 */

public class MyCameraUtils {

    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_TAKE_PICTURE = 2;
    private static final String MY_MEDIA_FOLDER = "CMOPA";
    private static final int OPTION_CREATE = 1;
    private static final int OPTION_MODIFY = 2;

    private Activity activity;
    private String temporaryFileName;
    private int requestMade;

    public MyCameraUtils(Activity activity) {
        this.activity = activity;
    }

    public void showPickMediaDialog() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);
        builder.negativeText(R.string.cancel);

        builder.title(R.string.add_photo);
        builder.items(R.array.choose_photo_options);


        builder.itemsCallback(new MaterialDialog.ListCallback() {

            @Override
            public void onSelection(MaterialDialog dialog, View view, int item, CharSequence text) {


                if (item == 0) {//Take picture

                    requestMade = REQUEST_TAKE_PICTURE;
                    startActivityTakePicture();

                } else if (item == 1) {//Select from gallery

                    requestMade = REQUEST_PICK_IMAGE;
                    startActivityChoosePicture();

                }

            }
        });

        builder.onNegative(new MaterialDialog.SingleButtonCallback() {

            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void startActivityChoosePicture() {

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        String title = activity.getResources().getString(R.string.choose_photo_chooser_title);
        activity.startActivityForResult(
                Intent.createChooser(intent, title),
                REQUEST_PICK_IMAGE);

    }

    private void startActivityTakePicture() {

        File mediaDirectory = getOrCreateMediaDirectory();
        File pictureFile = new File(mediaDirectory, generateMediaName());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));

        activity.startActivityForResult(intent, REQUEST_TAKE_PICTURE);

    }

    /**
     * Creates a new directory in the default device storage for storing the media files created
     * in this project.  If the directory already exists, it just opens it. The directory name
     * can be changed by changing the MY_MEDIA_FOLDER constant.
     */
    private File getOrCreateMediaDirectory() {

        File directory = new File(Environment.getExternalStorageDirectory(), MY_MEDIA_FOLDER);

        if (!directory.exists()) {

            Log.v("MEDIA", "Directory doesn't exist. Creating it");
            boolean created = directory.mkdirs();

            if (created) {
                return directory;
            } else {
                return null;
            }
        } else {
            Log.v("MEDIA", "Directory exists. Skipping creation");

            return directory;
        }
    }

    /**
     * Generates a new name for a newly created media file using timestamp for uniqueness.
     */
    private String generateMediaName() {

        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH))
                + fromInt(c.get(Calendar.DAY_OF_MONTH))
                + fromInt(c.get(Calendar.YEAR))
                + fromInt(c.get(Calendar.HOUR_OF_DAY))
                + fromInt(c.get(Calendar.MINUTE))
                + fromInt(c.get(Calendar.SECOND));


        temporaryFileName = date + ".png";
        return temporaryFileName;

    }

    private String fromInt(int val) {

        return String.valueOf(val);
    }

    public boolean isResultInOurDirectory(){
        return requestMade == REQUEST_TAKE_PICTURE;
    }

    public File getPictureTaken(){

        return new File(getOrCreateMediaDirectory(), temporaryFileName);
    }


}
