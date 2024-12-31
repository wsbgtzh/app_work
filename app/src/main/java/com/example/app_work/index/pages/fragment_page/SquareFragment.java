package com.example.app_work.index.pages.fragment_page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Adapter.PostAdapter;
import com.example.app_work.Client.UserRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.Data.Post;
import com.example.app_work.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SquareFragment extends Fragment {
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private Uri selectedImageUri;
    private Bitmap selectedImage;
    private BottomSheetDialog bottomSheetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        //View rootView = inflater.inflate(R.layout.dialog_edit_post, container, false);
        //initImagePicker();
        //Button post_btn = rootView.findViewById(R.id.post);
        //post_btn.setOnClickListener(v-> showButtonSheetDialog());
        UserRepository userRepository = new UserRepository(requireContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        userRepository.getAllPost(recyclerView);
        // 初始化图片选择器
        initImagePicker();

        // 设置悬浮按钮点击事件
        FloatingActionButton fabAddPost = rootView.findViewById(R.id.post);
        fabAddPost.setOnClickListener(v -> showButtonSheetDialog());
        return rootView;
    }

    private void initImagePicker() {
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        displaySelectedImage();
                    }
                });
    }

    private void showButtonSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_post, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageView selectedImageView = dialogView.findViewById(R.id.selectedImageView);
        Button uploadImageButton = dialogView.findViewById(R.id.uploadImageButton);
        TextInputEditText titleText = dialogView.findViewById(R.id.titleTextPost);
        TextInputEditText editText = dialogView.findViewById(R.id.editTextPost);
        Button saveButton = bottomSheetDialog.findViewById(R.id.btnSavePost);
        Button cancelButton = bottomSheetDialog.findViewById(R.id.btnCancelEdit);

        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        saveButton.setOnClickListener(v -> {
            String title = titleText.getText().toString();
            String content = editText.getText().toString();
            if (!title.isEmpty() || !content.isEmpty() || selectedImage != null) {
                UserRepository userRepository = new UserRepository(requireContext());
                userRepository.uploadPost(title, content, selectedImage);
            }
            selectedImage = null;
            bottomSheetDialog.dismiss();
        });
        cancelButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void displaySelectedImage() {
        if (selectedImageUri != null && bottomSheetDialog != null) {
            try {
                InputStream imageStream = requireContext().getContentResolver().openInputStream(selectedImageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);

                ImageView selectedImageView = bottomSheetDialog.findViewById(R.id.selectedImageView);
                if (selectedImageView != null) {
                    selectedImageView.setImageBitmap(selectedImage);
                    selectedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
