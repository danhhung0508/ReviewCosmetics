package com.example.reviewcosmetics;

public interface ItemClickListener {
    public void itemClick(int position);
    public void itemClick(Product product, String key);
}
