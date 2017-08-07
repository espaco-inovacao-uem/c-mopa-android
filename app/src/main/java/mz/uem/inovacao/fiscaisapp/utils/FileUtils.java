package mz.uem.inovacao.fiscaisapp.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mauro Banze
 * Useful file methods.
 */

public class FileUtils {

    /**
     * Copies file to a new directory
     *
     * @return newly copied file reference
     */
    public static File copyFile(File oldFile, File newFileDirectory, String newFileName) {

        InputStream in = null;
        OutputStream out = null;

        try {

            File newFile = new File(newFileDirectory, newFileName);
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

            return newFile;

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    /**
     * The last modified file in a giver directory
     */
    public static File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    /**
     * Checks if we have permission to write to the device external storage
     * @return
     */
    public static boolean canWriteOnExternalStorage() {

        // get the state of your external storage
        String state = Environment.getExternalStorageState();

        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
