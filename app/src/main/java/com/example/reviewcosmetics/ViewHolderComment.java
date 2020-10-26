package com.example.reviewcosmetics;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ViewHolderComment extends RecyclerView.ViewHolder {
    ImageView ivAvatar;
    TextView tvName, tvCmt;

    public ViewHolderComment(@NonNull View itemView) {
        super(itemView);
        ivAvatar = itemView.findViewById(R.id.ivAvatarCmt);
        tvName = itemView.findViewById(R.id.tvNameCmt);
        tvCmt = itemView.findViewById(R.id.tvCmtContent);
    }
}
