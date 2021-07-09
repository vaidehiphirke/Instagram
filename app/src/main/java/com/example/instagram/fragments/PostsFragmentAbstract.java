package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instagram.adapters.PostsAdapter;
import com.example.instagram.databinding.FragmentPostsBinding;
import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public abstract class PostsFragmentAbstract extends Fragment {

    public static final String TAG = "PostsFragment";
    protected static final int MAX_NUMBER_OF_POSTS_ALLOWED = 20;
    private FragmentPostsBinding postsBinding;
    private PostsAdapter adapter;
    private List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;

    public PostsFragmentAbstract() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        postsBinding = FragmentPostsBinding.inflate(inflater, container, false);
        return postsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView rvPosts = postsBinding.rvPosts;
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeContainer = postsBinding.swipeContainer;
        swipeContainer.setOnRefreshListener(this::queryPosts);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    protected abstract void queryPosts();

    protected class RetrievePostsFindCallback implements FindCallback<Post> {
        @Override
        public void done(List<Post> posts, ParseException e) {
            if (e != null) {
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }
            for (Post post : posts) {
                Log.i(TAG, "Post " + post.getDescription() + ", username: " + post.getUser().getUsername());
            }
            adapter.clear();
            allPosts.addAll(posts);
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        }
    }
}