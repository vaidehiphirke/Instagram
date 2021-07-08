package com.example.instagram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.instagram.databinding.FragmentComposeBinding;
import com.example.instagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends Fragment {

    public static final String TAG = "ComposeFragment";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 27;
    private static final int EMPTY_RESOURCE_ID = 0;
    private static final String PHOTO_FILE_NAME = "photo.jpg";
    private EditText etDescription;
    private ImageView ivPostImage;
    private File photoFile;
    private FragmentComposeBinding composeBinding;

    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        composeBinding = FragmentComposeBinding.inflate(inflater, container, false);
        return composeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDescription = composeBinding.etDescription;
        ivPostImage = composeBinding.ivPostImage;

        composeBinding.btnCaptureImage.setOnClickListener(new CameraOnClickListener());
        composeBinding.btnSubmit.setOnClickListener(new PostCreationOnClickListener());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final boolean isRequestCodeValid = requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;
        if (isRequestCodeValid && resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRequestCodeValid) {
            final Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ivPostImage.setImageBitmap(takenImage);
        }
    }

    private File getPhotoFileUri() {
        final File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to created directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator + PHOTO_FILE_NAME);
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        final Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SavePostSaveCallback());
    }

    private class CameraOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = getPhotoFileUri();
            final Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
    }

    private class PostCreationOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String description = etDescription.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile == null || ivPostImage.getDrawable() == null) {
                Toast.makeText(getContext(), "There is no image!", Toast.LENGTH_SHORT).show();
                return;
            }
            final ParseUser currentUser = ParseUser.getCurrentUser();
            savePost(description, currentUser, photoFile);
        }
    }

    private class SavePostSaveCallback implements SaveCallback {
        @Override
        public void done(ParseException e) {
            if (e != null) {
                Log.e(TAG, "Error while saving", e);
                Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "Post saved", Toast.LENGTH_SHORT).show();
            etDescription.setText("");
            ivPostImage.setImageResource(EMPTY_RESOURCE_ID);
        }
    }
}