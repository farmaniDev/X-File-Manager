package com.farmani.xfilemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;

public class FileListFragment extends Fragment implements FileAdapter.FileItemEventListener {
    private String path;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        recyclerView = view.findViewById(R.id.rv_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        File currentFolder = new File(path);
        File[] files = currentFolder.listFiles();
        fileAdapter = new FileAdapter(Arrays.asList(files), this);
        recyclerView.setAdapter(fileAdapter);
        TextView pathTV = view.findViewById(R.id.tv_files_path);
        pathTV.setText(currentFolder.getName().equalsIgnoreCase("files") ? "External storage" : currentFolder.getName());
        view.findViewById(R.id.iv_files_back).setOnClickListener(v -> getActivity().onBackPressed());
        return view;
    }

    @Override
    public void onFileItemClick(File file) {
        if (file.isDirectory()) {
            ((MainActivity)getActivity()).ListFiles(file.getPath());
        }
    }

    @Override
    public void onDeleteFileItemClick(File file) {
        if (file.delete()) {
            fileAdapter.deleteFile(file);
        }
    }

    public void createFolderName(String folderName) {
        File newFolder = new File(path + File.separator + folderName);
        if (!newFolder.exists()) {
            if (newFolder.mkdir()) {
                fileAdapter.addFile(newFolder);
                recyclerView.smoothScrollToPosition(0);
            }
        } else {
            Toast.makeText(getContext(), "File name exists!", Toast.LENGTH_SHORT).show();
        }
    }
}
