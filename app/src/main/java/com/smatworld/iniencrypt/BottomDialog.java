package com.smatworld.iniencrypt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smatworld.iniencrypt.databinding.FragmentBottomDialogBinding;

public class BottomDialog extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "com.smatworld.iniencrypt.TITLE";
    private static final String RES_ID = "com.smatworld.iniencrypt.RES_ID";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private int mResId;
    private FragmentBottomDialogBinding mBinding;

    public BottomDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param resId Parameter 2.
     * @return A new instance of fragment BottomDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomDialog newInstance(String title, int resId) {
        BottomDialog fragment = new BottomDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(RES_ID, resId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mResId = getArguments().getInt(RES_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentBottomDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.dialogImage.setImageResource(mResId);
        mBinding.dialogTitle.setText(mTitle);
    }
}