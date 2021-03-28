package com.smatworld.iniencrypt.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.smatworld.iniencrypt.utils.Constants.TAG;

public class FileUtil {

    public static File getFileFromUri(Fragment fragment, Uri uri) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        final String imageFileName = getQueryName(uri, fragment);
        File file = new File(fragment.requireActivity().getCacheDir(), imageFileName);
        try {
            ParcelFileDescriptor fileDescriptor = fragment.requireActivity().getContentResolver().openFileDescriptor(uri, "r", null);
            inputStream = new BufferedInputStream(new FileInputStream(fileDescriptor.getFileDescriptor()));
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            while (inputStream.read(buffer) != -1) outputStream.write(buffer);
            Log.i(TAG, "getFileFromUri => " + imageFileName + ": " + file.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }


    // run on background thread
    public static Bitmap getBitmapFromUri(Uri uri, Fragment fragment) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = fragment.requireActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static String getQueryName(Uri uri, Fragment fragment) {
        Cursor returnCursor = fragment.requireActivity().getContentResolver().query(uri, null, null, null, null);
        String name = "";
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return name;
    }

    // https://developer.android.com/guide/components/intents-common#OpenFile
    public static boolean uploadFileFromStorage(Fragment fragment) {
        boolean isActivityAvailable = true;
        Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = new String[]{"image/png", "image/jpeg", "image/jpg", "text/plain"};
        fileIntent.setType("*/*");
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Try to invoke the intent.
        if (fileIntent.resolveActivity(fragment.requireActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(fileIntent, Constants.FILE_REQUEST_CODE);
        } else isActivityAvailable = false;
        return isActivityAvailable;
    }

    // returns a text file
    public static String getTextFromFile(File file) {
        InputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder("");
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) sb.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null && isr != null && bufferedReader != null) {
                try {
                    fis.close();
                    isr.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void saveFileToStorage(Context context, byte[] fileContents, String filename) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileToStorage(Context context, InputStream inputStream, String filename) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            byte[] buffer = new byte[inputStream.available()];
            int readLength = inputStream.read(buffer);
            Log.i(TAG, "saveFileToStorage File Name: " + filename);
            Log.i(TAG, "saveFileToStorage File length: " + readLength + " bytes");
            fos.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileFromStorage(Context context, String filename) {
        InputStreamReader inputStreamReader = null;
        try (FileInputStream fis = context.openFileInput(filename)) {
            inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            e.printStackTrace();
        } finally {
            String contents = stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

    // deletes the cached file
    public static void deleteCacheFile(Context context, String cacheFileName) {
        context.deleteFile(cacheFileName);
    }

    // returns a list of the app's specific cached files
    public static String[] getCacheFileList(Context context) {
        return context.fileList();
    }

    // returns the File at the app's specific file directory
    public static File getFile(Context context, String fileName) {
        return new File(context.getFilesDir(), fileName);
    }

    // returns the file extension of a File (e.g. .jpg .txt)
    public static String getFileExtension(String fileName) {
        return fileName.trim().substring(fileName.indexOf('.'));
    }

    // returns a Base64 encoded form of the passed binary file
    public static String getEncodedFile(File file) {
        InputStream fis = null;
        StringBuilder sb = new StringBuilder("");
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[fis.available()];
            final int readLength = fis.read(buffer);
            Log.i(TAG, "getEncodedFile: Read Length: " + readLength);
            sb.append(Base64.encodeToString(buffer, Base64.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
