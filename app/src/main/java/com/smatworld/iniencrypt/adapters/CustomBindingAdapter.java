package com.smatworld.iniencrypt.adapters;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textview.MaterialTextView;
import com.smatworld.iniencrypt.R;

import java.util.Locale;


public class CustomBindingAdapter {

    @BindingAdapter("image_bitmap")
    public static void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) imageView.setImageBitmap(bitmap);
        else imageView.setImageResource(R.drawable.file_key);
    }

    @BindingAdapter("file_size")
    public static void setFileSize(MaterialTextView textView, long fileSize) {
        if (fileSize != 0)
            textView.setText(String.format(Locale.getDefault(), "File Size: %d bytes", fileSize));
        else
            textView.setText(String.format(Locale.getDefault(), "File Size: %d bytes", R.string.bytes));
    }

}
