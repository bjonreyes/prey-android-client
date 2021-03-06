package com.prey.actions.wipe;

import java.io.File;

import android.os.Environment;

import com.prey.PreyLogger;

public class WipeUtil {
        public static void deleteSD(){
                String accessable = Environment.getExternalStorageState();
                PreyLogger.d("Deleting folder: " + accessable + " from SD");

         if (Environment.MEDIA_MOUNTED.equals(accessable)) {
                 File dir = new File(Environment.getExternalStorageDirectory()+"");
                 deleteRecursive(dir);
         }

        }

        public static void deleteRecursive(File fileOrDirectory) {
         if (fileOrDirectory.isDirectory())
         for (File child : fileOrDirectory.listFiles())
         deleteRecursive(child);
         fileOrDirectory.delete();
        }
}