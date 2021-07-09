package com.example.instagram.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.databinding.ActivityPostDetailsBinding;
import com.example.instagram.models.Post;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityPostDetailsBinding postDetailsBinding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(postDetailsBinding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.instagram_word_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final Intent intent = getIntent();
        postDetailsBinding.tvUsername.setText(intent.getStringExtra(Post.KEY_USER));
        postDetailsBinding.tvDescription.setText(intent.getStringExtra(Post.KEY_DESCRIPTION));
        postDetailsBinding.tvTimestamp.setText(intent.getStringExtra(Post.KEY_CREATED_AT));
        Glide.with(this).load(intent.getStringExtra(Post.KEY_IMAGE)).into(postDetailsBinding.ivImage);
    }
}