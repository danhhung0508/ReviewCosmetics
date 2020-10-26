package com.example.reviewcosmetics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BrandChildFragment extends Fragment implements ItemClickListener{
    FirebaseRecyclerAdapter adapter;
    RecyclerView rv;
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_product, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvTopProduct);
        Bundle bundle = getArguments();
        String brandType = bundle.getString(BrandPagerAdapter.BRAND_TYPE);
        setUpRecyclerView(brandType);
    }

    private void setUpRecyclerView(String brandType) {
        DatabaseReference query = FirebaseDatabase.getInstance().getReference("Brand").
                child(brandType);
        FirebaseRecyclerOptions<Brand> options = new FirebaseRecyclerOptions.
                Builder<Brand>().
                setQuery(query, Brand.class).build();
        adapter = new FirebaseRecyclerAdapter<Brand, ViewHolderCategory>(options) {
            @NonNull
            @Override
            public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getParentFragment().getContext()).inflate(R.layout.item_brand,
                        parent, false);
                return new ViewHolderCategory(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCategory holder, int position, @NonNull Brand model) {
                Glide.with(getParentFragment().getContext()).load(model.getImage()).into(holder.ivCategory);
            }

        };
        rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    public void itemClick(int position) {

    }

    @Override
    public void itemClick(Product product, String key) {

    }
}
