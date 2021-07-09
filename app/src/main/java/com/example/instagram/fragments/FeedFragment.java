package com.example.instagram.fragments;

import com.example.instagram.models.Post;
import com.parse.ParseQuery;

public class FeedFragment extends PostsFragment {

    @Override
    protected void queryPosts() {
        final ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(MAX_NUMBER_OF_POSTS_ALLOWED);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new RetrievePostsFindCallback());
    }
}