package com.example.instagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.databinding.ItemPostBinding;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private final Context context;
    private final List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemPostBinding itemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(itemPostBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        final Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvUsername;
        private final ImageView ivImage;
        private final TextView tvDescription;

        public ViewHolder(@NonNull ItemPostBinding postBinding) {
            super(postBinding.getRoot());
            tvUsername = postBinding.tvUsername;
            ivImage = postBinding.ivImage;
            tvDescription = postBinding.tvDescription;
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            final ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
        }
    }
}
