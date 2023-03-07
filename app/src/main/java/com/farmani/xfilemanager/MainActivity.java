package com.farmani.xfilemanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewFolderDialog.AddNewFolderCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (StorageHelper.isExternalStorageReadable()) {
            File externalFilesDir = getExternalFilesDir(null);
            ListFiles(externalFilesDir.getPath(), false);
        }

        findViewById(R.id.iv_main_addNewFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewFolderDialog().show(getSupportFragmentManager(), null);
            }
        });

        EditText editText = findViewById(R.id.et_main_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
                if (fragment instanceof FileListFragment) {
                    ((FileListFragment) fragment).search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void ListFiles(String path, boolean addToBackStack) {
        FileListFragment fileListFragment = new FileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fileListFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_fragmentContainer, fileListFragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void ListFiles(String path) {
        this.ListFiles(path, true);
    }

    @Override
    public void onCreateFolderButtonClick(String folderName) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
        if (fragment instanceof FileListFragment) {
            ((FileListFragment) fragment).createNewFolder(folderName);
        }
    }
}