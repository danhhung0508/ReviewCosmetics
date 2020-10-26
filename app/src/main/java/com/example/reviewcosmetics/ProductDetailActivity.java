package com.example.reviewcosmetics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivDetail, ivDetailFavorite;
    TextView tvDetailName, tvDetailBrand, tvDetailIngredient, tvDetailFavorite, tvIngredienrDialog,
            tvDetailDescription;
    EditText edtComment;
    Button btnSend;
    RecyclerView rvComment;
    Product productInfo;
    String productKey;
    String productType;
    FirebaseRecyclerOptions<Comment> option;
    CommentAdapter adapter;
    boolean isFavorite = false;
    String phoneUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mapping();
        getDataProduct();
        setData();
        validateFavorite();
        setEventClickView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllComment();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDetailIngredient:
                showDialogIngredient();
                break;
            case R.id.tvDetailBrand:

                break;
            case R.id.tvDetailFavorite:

                break;
            case R.id.btnSendComment:
                sendComment();
                break;
            case R.id.ivFavoriteDetail:
                setFavoriteEventListener();
                break;
        }
    }

    private void setFavoriteEventListener() {
        final FireBaseProductManager manager = new FireBaseProductManager("Favorites");
        if (isFavorite) {
            manager.stopFavorite(productKey, phoneUser, new FireBaseProductManager.OnSuccessful() {
                @Override
                public void push(boolean isSuccessful) {
                    Toast.makeText(ProductDetailActivity.this, "You dislike product",
                            Toast.LENGTH_LONG).show();
                    getTotalLike(manager);
                }
            });
            isFavorite = false;
            ivDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_24);
        } else {
            manager.startFavorite(productKey, phoneUser, phoneUser.toString() + " loved this product",
                    new FireBaseProductManager.OnSuccessful() {
                        @Override
                        public void push(boolean isSuccessful) {
                            Toast.makeText(ProductDetailActivity.this, "You like this product",
                                    Toast.LENGTH_LONG).show();
                            getTotalLike(manager);
                        }
                    });
            isFavorite = true;
            ivDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
    }

    private void getTotalLike(FireBaseProductManager manager) {
         manager.countFavorite(productKey, new FireBaseProductManager.OnCountLike() {
             @Override
             public void countFinish(long total) {
                 tvDetailFavorite.setText(String.valueOf(total));
                 productInfo.setLike(total);
                 updateTotalLike();
             }
         });
    }

    private void updateTotalLike() {
        new FireBaseProductManager("Products").updateTotalLikeProduct(productType, productKey, productInfo);
    }

    private void showAllComment() {
        DatabaseReference query = FirebaseDatabase.getInstance().getReference("Comment")
                .child(productKey);
        option = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(query, Comment.class).build();

        adapter = new CommentAdapter(option, this, R.layout.item_comment);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(adapter);
    }

    private void validateFavorite() {
        new FireBaseProductManager("Favorites").
                checkFavorite(productKey, phoneUser, new FireBaseProductManager.OnSuccessful() {
                    @Override
                    public void push(boolean isSuccessful) {
                        if (isSuccessful) {
                            ivDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                            isFavorite = true;
                        }
                    }
                });
    }

    private void sendComment() {
        User user = getCurrentUser();
        FireBaseProductManager.OnSuccessful isSuccessful = sendCmtListener();
        String cmt = edtComment.getText().toString().trim();
        if (cmt.isEmpty()) return;
        Comment comment = new Comment(user.getName(), user.getAvatar(), cmt);
        new FireBaseProductManager("Comment").pushComment(productKey, comment, isSuccessful);
    }

    private User getCurrentUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(HomeActivity.CHECK_SIGN_IN, MODE_PRIVATE);
        String name = sharedPreferences.getString(HomeActivity.NAME, "");
        String avatar = sharedPreferences.getString(HomeActivity.AVATAR, "");
        return new User(name, avatar);
    }

    private FireBaseProductManager.OnSuccessful sendCmtListener() {
        return new FireBaseProductManager.OnSuccessful() {
            @Override
            public void push(boolean isSuccessful) {
                if (isSuccessful) {
                    Toast.makeText(ProductDetailActivity.this, "You rated this product",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Fail",
                            Toast.LENGTH_SHORT).show();
                }
                edtComment.setText("");
            }
        };
    }

    private void setData() {
        Glide.with(this).load(productInfo.getImageProduct()).into(ivDetail);
        tvDetailName.setText(productInfo.getProductName());
        tvDetailBrand.setText(productInfo.getBrandName());
        tvDetailDescription.setText(productInfo.getDescribe());
        phoneUser = getSharedPreferences(HomeActivity.CHECK_SIGN_IN, MODE_PRIVATE).
                getString(HomeActivity.PHONE, "");
        tvDetailFavorite.setText(String.valueOf(productInfo.getLike()));
    }

    private void showDialogIngredient() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_show_info);
        mappingAndSetText(dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.Animation_Dialog;
        dialog.show();
    }

    private void mappingAndSetText(Dialog dialog) {
        tvIngredienrDialog = dialog.findViewById(R.id.tvDesDialog);
        tvIngredienrDialog.setText(productInfo.getIngredient());
    }

    private void getDataProduct() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            productInfo = bundle.getParcelable(ProductTypeFragment.PRODUCT_DETAIL);
            productKey = bundle.getString(ProductTypeFragment.PRODUCT_KEY);
            productType = bundle.getString(ProductTypeFragment.PRODUCT_TYPE);
        }
    }

    private void setEventClickView() {
        tvDetailIngredient.setOnClickListener(this);
        tvDetailBrand.setOnClickListener(this);
        tvDetailFavorite.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        ivDetailFavorite.setOnClickListener(this);
    }

    private void mapping() {
        btnSend = findViewById(R.id.btnSendComment);
        edtComment = findViewById(R.id.edtComment);
        ivDetail = findViewById(R.id.ivDetailProduct);
        ivDetailFavorite = findViewById(R.id.ivFavoriteDetail);
        tvDetailName = findViewById(R.id.tvDetailNameProduct);
        tvDetailBrand = findViewById(R.id.tvDetailBrand);
        tvDetailIngredient = findViewById(R.id.tvDetailIngredient);
        tvDetailFavorite = findViewById(R.id.tvDetailFavorite);
        rvComment = findViewById(R.id.rvComment);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
    }
}