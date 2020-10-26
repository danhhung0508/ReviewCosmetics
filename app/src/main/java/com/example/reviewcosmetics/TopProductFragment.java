package com.example.reviewcosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import java.util.ArrayList;
import java.util.List;

public class TopProductFragment extends Fragment implements ItemClickListener {
    RecyclerView rvTop;
    FirebaseRecyclerAdapter adapter;
    FirebaseRecyclerOptions<Product> options;
    String typeProduct;

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
        rvTop = view.findViewById(R.id.rvTopProduct);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        typeProduct = args.getString(ProductTypeFragment.PRODUCT_TYPE);
        setRecyclerView(typeProduct);
    }

    @Override
    public void itemClick(int position) {

    }

    @Override
    public void itemClick(Product product, String key) {
        Intent itDetail = new Intent(getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductTypeFragment.PRODUCT_DETAIL, product);
        bundle.putString(ProductTypeFragment.PRODUCT_KEY, key);
        bundle.putString(ProductTypeFragment.PRODUCT_TYPE, typeProduct);
        itDetail.putExtras(bundle);
        startActivity(itDetail);
    }

    private void setRecyclerView(String typeProduct) {
        final ItemClickListener click = this;
        DatabaseReference query = FirebaseDatabase.getInstance().getReference("Products").
                child(typeProduct);
        options = new FirebaseRecyclerOptions.Builder<Product>().setQuery(
                query.orderByChild("like").limitToLast(8),
                Product.class).build();
        adapter = new FirebaseRecyclerAdapter<Product, ViewHolderProduct>(options) {

            @NonNull
            @Override
            public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
                return new ViewHolderProduct(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderProduct holder, final int position, @NonNull final Product model) {
                Glide.with(getContext()).load(model.getImageProduct()).into(holder.ivProduct);
                holder.tvProductName.setText(model.getProductName());
                holder.tvProductBrand.setText(model.getBrandName());
                holder.tvFavourite.setText(model.getLike() + "");
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = getRef(position).getKey();
                        click.itemClick(model, key);
                    }
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        rvTop.setLayoutManager(manager);
        rvTop.setAdapter(adapter);
    }


}
