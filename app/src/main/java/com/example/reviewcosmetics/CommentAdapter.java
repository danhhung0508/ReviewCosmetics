package com.example.reviewcosmetics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, ViewHolderComment> {
    Context context;
    int layout;

    public CommentAdapter(@NonNull FirebaseRecyclerOptions<Comment> options, Context context, int layout) {
        super(options);
        this.context = context;
        this.layout = layout;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderComment holder, int position, @NonNull Comment model) {
        Glide.with(context).load(model.getImageUser()).circleCrop().into(holder.ivAvatar);
        holder.tvName.setText(model.getNameUser());
        holder.tvCmt.setText(model.getCmt());
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolderComment(view);
    }
}
