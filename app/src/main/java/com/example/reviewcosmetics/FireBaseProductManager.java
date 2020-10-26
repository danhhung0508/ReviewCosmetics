package com.example.reviewcosmetics;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBaseProductManager {
    private DatabaseReference databaseReference;

    public FireBaseProductManager(String typeRef) {
        databaseReference = FirebaseDatabase.getInstance().getReference(typeRef);
    }

    public interface OnCountLike {
        public void countFinish(long total);
    }

    public interface DataChangeListener {
        public void loadData(Product product, String key);
    }

    public interface OnSuccessful {
        public void push(boolean isSuccessful);
    }

    public void getDataProduct(String productType, final DataChangeListener dataChangeListener) {
        databaseReference.child(productType).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String key = snapshot.getKey();
                    Product product = snapshot.getValue(Product.class);
                    dataChangeListener.loadData(product, key);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void pushComment(String keyProduct, Comment comment, final OnSuccessful pushCommentListener) {
        databaseReference.child(keyProduct).push().
                setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pushCommentListener.push(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pushCommentListener.push(false);
            }
        });
    }

    public void stopFavorite(String productKey, String phoneUser, final OnSuccessful delete) {
        databaseReference.child(productKey).child(phoneUser).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                delete.push(true);
            }
        });
    }

    public void checkFavorite(String productKey, String phoneUser, final OnSuccessful isFavorite) {
        databaseReference.child(productKey).child(phoneUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    isFavorite.push(false);
                } else {
                    isFavorite.push(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startFavorite(String productKey, String phoneUser, String value, final OnSuccessful isFavorite) {
        databaseReference.child(productKey).child(phoneUser).push().setValue(value).
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isFavorite.push(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFavorite.push((false));
            }
        });
    }

    public void countFavorite(String productKey, final OnCountLike countLike) {
        final long[] count = {0};
        databaseReference.child(productKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count[0] = snapshot.getChildrenCount();
                countLike.countFinish(count[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateTotalLikeProduct(String productType, String keyProduct, Product product) {
        Map<String, Object> newProduct = new HashMap<>();
        newProduct.put(keyProduct, product);
        databaseReference.child(productType).updateChildren(newProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("MH", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MH", "Fail");
            }
        });
    }
}
