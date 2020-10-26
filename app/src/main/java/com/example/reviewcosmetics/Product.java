package com.example.reviewcosmetics;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

import java.util.List;

public class Product implements Parcelable {
    private long like;
    private String productName;
    private String brandName;
    private String imageProduct;
    private String describe;
    private String type;
    private String ingredient;

    public Product() {}

    public Product(String productName, String brandName, String imageProduct, String describe,
                   String type, String ingredient) {
        like = 0;
        this.productName = productName;
        this.brandName = brandName;
        this.imageProduct = imageProduct;
        this.describe = describe;
        this.type = type;
        this.ingredient = ingredient;
    }

    protected Product(Parcel in) {
        like = in.readLong();
        productName = in.readString();
        brandName = in.readString();
        imageProduct = in.readString();
        describe = in.readString();
        type = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(like);
        dest.writeString(productName);
        dest.writeString(brandName);
        dest.writeString(imageProduct);
        dest.writeString(describe);
        dest.writeString(type);
        dest.writeString(ingredient);
    }
}
