package com.smatworld.iniencrypt.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textview.MaterialTextView;
import com.smatworld.iniencrypt.R;
import com.smatworld.iniencrypt.utils.Constants;
import com.smatworld.iniencrypt.utils.StringUtil;

import java.util.Locale;


public class CustomBindingAdapter {

    @BindingAdapter("image_bitmap")
    public static void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) imageView.setImageBitmap(bitmap);
        else imageView.setImageResource(R.drawable.file_key);
    }

    @BindingAdapter("path_bitmap")
    public static void bindImagePathBitmap(ImageView imageView, String imageFilePath){
        if (!imageFilePath.isEmpty())
            imageView.setImageBitmap(BitmapFactory.decodeFile(imageFilePath));
        else imageView.setImageResource(R.drawable.file_key_outline);
    }

    @BindingAdapter("file_size")
    public static void setFileSize(MaterialTextView textView, long fileSize) {
        if (fileSize != 0) {
            Log.i(Constants.TAG, "setFileSize: " + fileSize);
            textView.setText(String.format(Locale.getDefault(), "File Size: %s", StringUtil.getFormattedSize(fileSize)));
        } else
            textView.setText("");//(String.format(Locale.getDefault(), "File Size: %s", R.string.bytes));
    }

    @BindingAdapter("encryption_time")
    public static void setEncryptionTime(MaterialTextView textView, long encryptionTime) {
        if (encryptionTime != 0)
            textView.setText(textView.getContext().getString(R.string.encryption_time, String.valueOf(encryptionTime)));
        else textView.setVisibility(View.GONE);

    }

    @BindingAdapter("decryption_time")
    public static void setDecryptionTime(MaterialTextView textView, long encryptionTime) {
        if (encryptionTime != 0)
            textView.setText(textView.getContext().getString(R.string.decryption_time, String.valueOf(encryptionTime)));
        else textView.setVisibility(View.GONE);

    }

    /*@BindingAdapter("data_stream")
    public static void setDataStream(MaterialTextView textView, InputStream stream) {
        if (stream != null) textView.setText(SecurityUtil.getEncodedStream(stream));
        else textView.setText("");
    }*/

}
