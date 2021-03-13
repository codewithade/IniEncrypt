package com.smatworld.iniencrypt;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.smatworld.iniencrypt.databinding.FragmentMainBinding;
import com.smatworld.iniencrypt.models.FileData;
import com.smatworld.iniencrypt.utils.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainFragment extends Fragment {

    private FragmentMainBinding mBinding;
    public static final int FILE_REQUEST_CODE = 4;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeAppOnBackPressed();
        mBinding.chooseButton.setOnClickListener(v -> uploadFileFromStorage());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                // occurs when user selects multiple files
                if (clipData == null) { // occurs when user selects only a file
                    final Uri imageUri = data.getData();
                    final File file = getFileFromUri(imageUri);
                    final String fileName = getQueryName(imageUri);
                    Bitmap bitmap = null;
                    try {
                        bitmap = getBitmapFromUri(imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileData fileData = new FileData(bitmap, file, fileName, file.length(), true);
                    mBinding.setFileData(fileData);
                } else displaySnackBar("You can only upload a file.");
            }
        }
    }

    private File getFileFromUri(Uri uri) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        final String imageFileName = getQueryName(uri);
        File file = new File(requireActivity().getCacheDir(), imageFileName);
        try {
            ParcelFileDescriptor fileDescriptor = requireActivity().getContentResolver().openFileDescriptor(uri, "r", null);
            inputStream = new BufferedInputStream(new FileInputStream(fileDescriptor.getFileDescriptor()));
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            while (inputStream.read(buffer) != -1) outputStream.write(buffer);
            Log.i(Constants.TAG, "getFileFromUri => " + imageFileName + ": " + file.length());
        } catch (FileNotFoundException e) {
            displaySnackBar(e.getMessage());
            e.printStackTrace();
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

    private void processUri(Uri returnUri) {
        Cursor returnCursor = requireActivity().getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            Log.i(Constants.TAG, "processUri: Uri: " + Uri.parse(returnUri.toString()));
            Log.i(Constants.TAG, "processUri: Uri Path: " + returnUri.getPath());
            Log.i(Constants.TAG, "processUri: File Name: " + returnCursor.getString(nameIndex));
            Log.i(Constants.TAG, "processUri: File Size: " + returnCursor.getLong(sizeIndex));
            returnCursor.close();
        }
    }

    // run on background thread
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = requireActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void uploadFileFromStorage() {
        Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = new String[]{"image/png", "image/jpeg", "image/jpg", "text/plain"};
        fileIntent.setType("image/*");
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(fileIntent, FILE_REQUEST_CODE);
    }

    private String getQueryName(Uri uri) {
        Cursor returnCursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
        String name = "";
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return name;
    }

    private void displaySnackBar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void closeAppOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }
}