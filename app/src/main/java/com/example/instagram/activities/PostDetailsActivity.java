package com.example.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.instagram.databinding.ActivityPostDetailsBinding;
import com.example.instagram.models.Post;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityPostDetailsBinding postDetailsBinding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(postDetailsBinding.getRoot());

        final TextView tvUsername = postDetailsBinding.tvUsername;
        final ImageView ivImage = postDetailsBinding.ivImage;
        final TextView tvDescription = postDetailsBinding.tvDescription;
        final TextView tvTimestamp = postDetailsBinding.tvTimestamp;

        final Intent intent = getIntent();
        tvUsername.setText(intent.getStringExtra(Post.KEY_USER));
        tvDescription.setText(intent.getStringExtra(Post.KEY_DESCRIPTION));
        tvTimestamp.setText(intent.getStringExtra(Post.KEY_CREATED_AT));
        Glide.with(this).load(intent.getStringExtra(Post.KEY_IMAGE)).into(ivImage);

    }
}