package com.example.reviewcosmetics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {
    private static final int REQUEST_PICK_IMAGE = 58;
    TextInputEditText edtEmailSignUp, edtPasswordSignUp, edtNameSignUp;
    TextInputLayout layoutNameSignUp, layoutPassSignUp, layoutEmailSignUp;
    Button btnSignUp, btnSetAvatar;
    ImageView ivAvatarSignUp;
    DatabaseReference dbRef;
    StorageReference storageReference;
    String avatar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mapping();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegister();
            }
        });

        btnSetAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAvatar();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null &&
            data.getData() != null) {
            Glide.with(this).load(R.drawable.loading).centerCrop().into(ivAvatarSignUp);
            saveImageToStorage(data);
        }
    }

    private void saveImageToStorage(Intent intent) {
        Uri image = intent.getData();
        final StorageReference imageName = storageReference.child("image/" + image.getLastPathSegment());
        UploadTask uploadTask = imageName.putFile(image);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        avatar = String.valueOf(uri);
                        Glide.with(SignUpActivity.this).load(avatar).centerCrop().
                                error(R.drawable.ic_baseline_error_outline_24).into(ivAvatarSignUp);
                    }
                });
            }
        });

    }

    private void setAvatar() {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, REQUEST_PICK_IMAGE);
    }

    private void checkRegister() {
        String phone = edtEmailSignUp.getText().toString().trim();
        String name = edtNameSignUp.getText().toString().trim();
        String pass = edtPasswordSignUp.getText().toString().trim();
        boolean checkPhone = checkPhone(phone);
        boolean checkName = checkName(name);
        boolean checkPass = checkPass(pass);
        if(!checkPhone || !checkName || !checkPass) {
            return;
        } else {
            User user = new User(name, pass, avatar);
            saveAccount(phone, user);
        }
    }

    private void saveAccount(String phone, User user) {
        dbRef.child(phone).setValue(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Sign Up Fail !", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private boolean checkPass(String pass) {
        if(pass.isEmpty()) {
            layoutPassSignUp.setError("Not be empty password !");
            return false;
        } else if(pass.length() < 5) {
            layoutPassSignUp.setError("Weak password ! Create password more than 5 characters");
            return false;
        } else {
            layoutPassSignUp.setError(null);
        }
        return true;
    }

    private boolean checkName(String name) {
        if(name.isEmpty()) {
            layoutNameSignUp.setError("Not be empty name !");
            return false;
        } else {
            layoutNameSignUp.setError(null);
        }
        return true;
    }

    private boolean checkPhone(String phone) {
        if(phone.isEmpty()) {
            layoutEmailSignUp.setError("Not be empty phone !");
            return false;
        } else {
            layoutEmailSignUp.setError(null);
        }
        return true;
    }

    private void mapping() {
        storageReference = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSetAvatar = findViewById(R.id.btnSetAvatar);
        ivAvatarSignUp = findViewById(R.id.ivAvatarSignUp);
        edtEmailSignUp = findViewById(R.id.etEmailSignUp);
        edtPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        edtNameSignUp = findViewById(R.id.etNameSignUp);
        layoutNameSignUp = findViewById(R.id.etLayoutNameSignUp);
        layoutEmailSignUp = findViewById(R.id.etLayoutEmailSignUp);
        layoutPassSignUp = findViewById(R.id.etLayoutPassSignUp);
    }
}