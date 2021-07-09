package com.example.instagram.fragments;

import com.example.instagram.models.Post;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProfileFragment extends PostsFragmentAbstract {

    @Override
    protected void queryPosts() {
        final ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(MAX_NUMBER_OF_POSTS_ALLOWED);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new RetrievePostsFindCallback());
    }
}
