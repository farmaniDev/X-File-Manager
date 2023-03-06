package com.farmani.xfilemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    List<File> files;
    private FileItemEventListener fileItemEventListener;

    public FileAdapter(List<File> files, FileItemEventListener fileItemEventListener) {
        this.files = new ArrayList<>(files);
        this.fileItemEventListener = fileItemEventListener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.bind(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName;
        private ImageView fileIcon;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.tv_file_name);
            fileIcon = itemView.findViewById(R.id.iv_file);
        }

        public void bind(File file) {
            if (file.isDirectory()) {
                fileIcon.setImageResource(R.drawable.baseline_folder_24);
            } else {
                fileIcon.setImageResource(R.drawable.baseline_insert_drive_file_24);
            }

            fileName.setText(file.getName());
            itemView.setOnClickListener(v -> {
                fileItemEventListener.onItemClick(file);
            });
        }
    }

    public interface FileItemEventListener {
        void onItemClick(File file);
    }

    public void addFile(File file) {
        files.add(0, file);
        notifyItemInserted(0);
    }
}
