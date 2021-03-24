package com.smatworld.iniencrypt;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.smatworld.iniencrypt.data.security.utils.DataUtil;
import com.smatworld.iniencrypt.databinding.FragmentMainBinding;
import com.smatworld.iniencrypt.databinding.FragmentMainDialogBinding;
import com.smatworld.iniencrypt.di.AppContainer;
import com.smatworld.iniencrypt.di.IniEncryptApp;
import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.FileData;
import com.smatworld.iniencrypt.models.TaskStatus;
import com.smatworld.iniencrypt.presentation.FileViewModel;
import com.smatworld.iniencrypt.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;

public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding mBinding;
    public static final int FILE_REQUEST_CODE = 4;
    private FileViewModel mFileViewModel;
    private BottomDialog mBottomDialog;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((IniEncryptApp) requireActivity().getApplication()).appContainer;
        mFileViewModel = new ViewModelProvider(requireActivity(), appContainer.fileViewModelFactory).get(FileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        mBinding.setLifecycleOwner(requireActivity());
        mBinding.setViewModel(mFileViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeAppOnBackPressed();
        mBinding.chooseButton.setOnClickListener(v -> {
            final boolean isSuccessful = FileUtil.uploadFileFromStorage(this);
            if (!isSuccessful) displaySnackBar(requireActivity().getString(R.string.no_app_found));
        });

        for (int i = 0; i < mBinding.asymmetricChipGroup.getChildCount(); i++) {
            mBinding.asymmetricChipGroup.getChildAt(i).setOnClickListener(this);
            mBinding.symmetricChipGroup.getChildAt(i).setOnClickListener(this);
        }

        mBinding.refreshButton.setOnClickListener(v -> refreshWorkSpace());
        mBinding.encryptButton.setOnClickListener(v -> displayInputDialog(16));
        mBinding.decryptButton.setOnClickListener(v -> {
            final FileData fileData = mFileViewModel.getFileData().getValue();
            if (fileData != null && fileData.getEncryptedStream() != null)
                startDecryption(fileData.getEncryptedStream());
            else displaySnackBar("You have to encrypt a file first.");
        });
    }

    private void startDecryption(InputStream encryptedStream) {
        displayBottomDialog(String.format(Locale.getDefault(), "%s decryption in progress...", mFileViewModel.getAlgorithm().getAlgorithm()), R.drawable.ic_no_encryption_blue, false);
        switch (mFileViewModel.getAlgorithm()) {
            case AES:
                final FileData fileData = mFileViewModel.getFileData().getValue();
                mFileViewModel.decryptAES(encryptedStream, fileData.getKey()).observe(getViewLifecycleOwner(), data -> {
                    if (data.getTaskStatus() == TaskStatus.SUCCESS) {
                        if (mBottomDialog != null) mBottomDialog.dismiss();
                        displaySnackBar(data.getSuccessMessage());
                        final InputStream decryptedStream = data.getData();
                        mBinding.plainTv.setText(DataUtil.getEncodedStream(decryptedStream));
                        final long decryptionTime = data.getEndTime() - data.getStartTime();
                        mBinding.decryptionTimeTv.setText(getString(R.string.decryption_time, String.valueOf(decryptionTime)));
                        mBinding.decryptionTimeTv.setVisibility(View.VISIBLE);
                        // update ViewModel
                        fileData.setDecryptedStream(decryptedStream);
                        fileData.setDecryptionTime(decryptionTime);
                        fileData.setStreamAvailable(true);
                        data.setTaskStatus(TaskStatus.PENDING);
                    } else if (data.getTaskStatus() == TaskStatus.FAILED) {
                        if (mBottomDialog != null) mBottomDialog.dismiss();
                        displaySnackBar(data.getErrorMessage());
                        data.setTaskStatus(TaskStatus.PENDING);
                    }
                });
        }
    }

    private void startEncryption(String key) {
        displayBottomDialog(String.format(Locale.getDefault(), "%s encryption in progress...", mFileViewModel.getAlgorithm().getAlgorithm()), R.drawable.ic_encryption_blue, false);
        switch (mFileViewModel.getAlgorithm()) {
            case AES:
                final FileData fileData = mFileViewModel.getFileData().getValue();
                mFileViewModel.encryptAES(DataUtil.getInputStreamFromFile(Objects.requireNonNull(fileData).getFile()), key).observe(getViewLifecycleOwner(), data -> {
                    if (data.getTaskStatus() == TaskStatus.SUCCESS) {
                        if (mBottomDialog != null) mBottomDialog.dismiss();
                        displaySnackBar(data.getSuccessMessage());
                        final InputStream encryptedStream = data.getData();
                        mBinding.encryptedTv.setText(DataUtil.getEncodedStream(encryptedStream));
                        final long encryptionTime = data.getEndTime() - data.getStartTime();
                        mBinding.encryptionTimeTv.setText(getString(R.string.encryption_time, String.valueOf(encryptionTime)));
                        mBinding.encryptionTimeTv.setVisibility(View.VISIBLE);
                        // update ViewModel
                        fileData.setEncryptedStream(encryptedStream);
                        fileData.setEncryptionTime(encryptionTime);
                        fileData.setStreamAvailable(true);
                        mBinding.decryptButton.setEnabled(true);
                        data.setTaskStatus(TaskStatus.PENDING);
                    } else if (data.getTaskStatus() == TaskStatus.FAILED) {
                        if (mBottomDialog != null) mBottomDialog.dismiss();
                        displaySnackBar(data.getErrorMessage());
                        fileData.setStreamAvailable(false);
                        data.setTaskStatus(TaskStatus.PENDING);
                    }
                });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                // occurs when user selects multiple files
                if (clipData == null) { // occurs when user selects only a file
                    String previewData = "";
                    Bitmap bitmap = null;
                    final Uri imageUri = data.getData();
                    final File file = FileUtil.getFileFromUri(this, imageUri);
                    final String fileName = FileUtil.getQueryName(imageUri, this);
                    String fileExtension = fileName.trim().substring(fileName.indexOf('.'));
                    if (fileExtension.toLowerCase().startsWith(".t"))
                        previewData = FileUtil.getTextFromFile(file);
                    else {
                        try {
                            bitmap = FileUtil.getBitmapFromUri(imageUri, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    FileData fileData = new FileData(bitmap, file, fileName, file.length(), previewData);
                    mFileViewModel.setFileData(fileData);
                    mBinding.setViewModel(mFileViewModel);
                    changeViewState();
                } else displaySnackBar("You can only upload a single file.");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
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

    @Override
    public void onClick(View v) {
        final Chip chip = ((Chip) v);
        final int buttonId = chip.getId();
        final boolean isChecked = chip.isChecked();
        String selectedEncryption = "";

        if (buttonId == R.id.aes_chip && isChecked) {
            selectedEncryption = getString(R.string.aes);
            setState(mBinding.asymmetricChipGroup);
            mFileViewModel.setAlgorithm(Algorithm.AES);
        } else if (buttonId == R.id.des_chip && isChecked) {
            selectedEncryption = getString(R.string._3des);
            setState(mBinding.asymmetricChipGroup);
            mFileViewModel.setAlgorithm(Algorithm.TRIPLE_DES);
        } else if (buttonId == R.id.rsa_chip && isChecked) {
            selectedEncryption = getString(R.string.rsa);
            setState(mBinding.symmetricChipGroup);
            mFileViewModel.setAlgorithm(Algorithm.RSA);
        } else if (buttonId == R.id.dh_chip && isChecked) {
            selectedEncryption = getString(R.string.diffie);
            setState(mBinding.symmetricChipGroup);
            mFileViewModel.setAlgorithm(Algorithm.DIFFIE_HELLMAN);
        }
        final MaterialButton selectedEncryptButton = mBinding.selectedEncryptButton;
        selectedEncryptButton.setText(selectedEncryption);
        if (selectedEncryption.isEmpty()) {
            selectedEncryptButton.setVisibility(View.GONE);
            mFileViewModel.setAlgorithmSelected(false);
            mBinding.chooseButton.setEnabled(false);
            disableCryptographyButtons();
        } else {
            selectedEncryptButton.setVisibility(View.VISIBLE);
            mFileViewModel.setAlgorithmSelected(true);
            mBinding.chooseButton.setEnabled(true);
            changeViewState();
        }
    }

    private void setState(ChipGroup chipGroup) {
        for (int i = 0; i < chipGroup.getChildCount(); i++)
            ((Chip) chipGroup.getChildAt(i)).setChecked(false);
    }

    private void disableCryptographyButtons() {
        mBinding.encryptButton.setEnabled(false);
        mBinding.decryptButton.setEnabled(false);
    }

    /*private void enableCryptographyButtons() {
        mBinding.encryptButton.setEnabled(true);
        mBinding.decryptButton.setEnabled(true);
    }*/

    private void refreshWorkSpace() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Reset workspace")
                .setMessage("Are you sure you want to reset the workspace?")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setPositiveButton("Yes", (dialog, which) -> {
                    disableCryptographyButtons();
                    mBinding.chooseButton.setEnabled(false);
                    mBinding.previewTv.setText("");
                    mBinding.encryptedTv.setText("");
                    mBinding.plainTv.setText("");
                    mBinding.fileNameTv.setText("");
                    mBinding.previewImage.setImageResource(R.drawable.file_key);
                    mBinding.keyTv.setText(getString(R.string.key_text_view));
                    mBinding.selectedEncryptButton.setVisibility(View.GONE);
                    setState(mBinding.asymmetricChipGroup);
                    setState(mBinding.symmetricChipGroup);
                    mFileViewModel.setAlgorithmSelected(false);
                    mFileViewModel.setFileData(null);
                    mFileViewModel.setAlgorithm(null);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void displayBottomDialog(String title, int resId, boolean isCancellable) {
        mBottomDialog = BottomDialog.newInstance(String.format(Locale.getDefault(), title, mFileViewModel.getAlgorithm().getAlgorithm()), resId);
        mBottomDialog.setCancelable(isCancellable);
        mBottomDialog.show(requireParentFragment().getParentFragmentManager(), title);
    }

    private void displayInputDialog(int keyLength) {
        FragmentMainDialogBinding dialogBinding = FragmentMainDialogBinding.inflate(LayoutInflater.from(requireContext()));
        final TextInputLayout textInputLayout = dialogBinding.dataTil;
        textInputLayout.setCounterMaxLength(keyLength);
        textInputLayout.setHint(getString(R.string.hint_key_length, keyLength));
        dialogBinding.dataEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int charCount = s.length();
                dialogBinding.dialogButton.setEnabled(charCount == keyLength);
            }
        });
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.getRoot())
                .setCancelable(true).create();
        alertDialog.show();

        dialogBinding.dialogButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            final String key = Objects.requireNonNull(dialogBinding.dataEt.getText()).toString();
            final FileData fileData = mFileViewModel.getFileData().getValue();
            if (fileData != null) {
                fileData.setKey(key);
                final String formattedKey = String.format(Locale.getDefault(), "Key: %s", fileData.getKey());
                mBinding.keyTv.setText(formattedKey);
                startEncryption(key);
            } else displaySnackBar("Upload a file.");
        });
    }

    private void changeViewState() {
        final FileData fileData = mFileViewModel.getFileData().getValue();
        if (fileData == null) disableCryptographyButtons();
        else {
            mBinding.encryptButton.setEnabled(true);
            mBinding.decryptButton.setEnabled(fileData.getEncryptedStream() != null);
        }
    }
}