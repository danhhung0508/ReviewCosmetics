package com.example.reviewcosmetics;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCategory extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView ivCategory;
    TextView tvCategory;
    ItemClickListener itemClickListener;

    public ViewHolderCategory(@NonNull View itemView) {
        super(itemView);
        ivCategory = itemView.findViewById(R.id.ivCategory);
        tvCategory = itemView.findViewById(R.id.tvCategory);
    }

    public ViewHolderCategory(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        ivCategory = itemView.findViewById(R.id.ivCategory);
        tvCategory = itemView.findViewById(R.id.tvCategory);
        this.itemClickListener =  itemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.itemClick(getAdapterPosition());
    }
}
