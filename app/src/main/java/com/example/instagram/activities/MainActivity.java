package com.example.instagram.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.databinding.ActivityMainBinding;
import com.example.instagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 27;
    private static final int EMPTY_RESOURCE_ID = 0;
    private static final String PHOTO_FILE_NAME = "photo.jpg";
    private EditText etDescription;
    private ImageView ivPostImage;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        final Button btnLogout = mainBinding.btnLogout;
        btnLogout.setOnClickListener(new LogoutButtonViewOnClickListener());

        etDescription = mainBinding.etDescription;
        final Button btnCaptureImage = mainBinding.btnCaptureImage;
        ivPostImage = mainBinding.ivPostImage;
        final Button btnSubmit = mainBinding.btnSubmit;

        btnCaptureImage.setOnClickListener(new CameraOnClickListener());
        btnSubmit.setOnClickListener(new PostCreationOnClickListener());
    }

    private void launchCamera() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(PHOTO_FILE_NAME);
        final Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                return;
            }
            final Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ivPostImage.setImageBitmap(takenImage);
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        final File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to created directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        final Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SavePostSaveCallback());
    }

    private void goToLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public class LogoutButtonViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ParseUser.logOut();
            goToLoginActivity();
        }
    }

    private class PostCreationOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String description = etDescription.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(MainActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile == null || ivPostImage.getDrawable() == null) {
                Toast.makeText(MainActivity.this, "There is no image!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "Post saved", Toast.LENGTH_SHORT).show();
            etDescription.setText("");
            ivPostImage.setImageResource(EMPTY_RESOURCE_ID);
        }
    }

    private class CameraOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            launchCamera();
        }
    }

}