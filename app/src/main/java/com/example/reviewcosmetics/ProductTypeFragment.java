package com.example.reviewcosmetics;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeFragment extends Fragment implements ItemClickListener {
    public static final String TYPE_CLEANSER = "Cleanser";
    public static final String TYPE_TONER = "Toner";
    public static final String TYPE_SERUM = "Serum";
    public static final String TYPE_MOISTURISER = "Moisturiser";
    public static final String TYPE_EXFOLIATOR = "Exfoliator";
    public static final String TYPE_SUNSCREEN = "Sunscreen";
    public static final String PRODUCT_DETAIL = "product_detail";
    public static final String PRODUCT_KEY = "product_key";
    public static final String PRODUCT_TYPE = "product_type";

    private int idCategory = -1;
    HomeActivity homeActivity;
    TextView tvProductType;
    RecyclerView rv;
    ProductAdapter adapter;
    List<Product> productList;
    List<String> keys;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    public void onAttach(@NonNull Context context) {
        homeActivity = (HomeActivity) context;
        idCategory = HomeActivity.PRODUCT_TYPE_ID;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_product_type, container, false);
        mapping(view);
        searchView.setBackgroundColor(Color.WHITE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(productList.size() >= 0) productList.clear();
        setProductType();
        setRecyclerView();
    }


    private void search(String text) {
        List<Product> filterData = new ArrayList<>();
        if(!text.trim().isEmpty() || text != null) {
            for(Product p : productList) {
                if(p.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    filterData.add(p);
                }
            }
            adapter = new ProductAdapter(getContext(), filterData, R.layout.item_product, this);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(adapter);
        }
    }

    @Override
    public void itemClick(int position) {
        Product product = productList.get(position);
        String keyProduct = keys.get(position);
        Intent itDetail = new Intent(getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_DETAIL, product);
        bundle.putString(PRODUCT_KEY, keyProduct);
        bundle.putString(PRODUCT_TYPE, tvProductType.getText().toString());
        itDetail.putExtras(bundle);
        startActivity(itDetail);
    }

    @Override
    public void itemClick(Product product, String key) {

    }

    private void mapping(View view) {
        searchView = view.findViewById(R.id.searchView);
        rv = view.findViewById(R.id.rvProductType);
        tvProductType = view.findViewById(R.id.tvFmProductType);
        productList = new ArrayList<>();
        keys = new ArrayList<>();
    }

    private void setProductType() {
        switch (idCategory) {
            case HomeFragment.ID_CLEANSER:
                tvProductType.setText(TYPE_CLEANSER);
                new LoadProducts().execute(TYPE_CLEANSER);
                break;
            case HomeFragment.ID_TONER:
                tvProductType.setText(TYPE_TONER);
                new LoadProducts().execute(TYPE_TONER);
                break;
            case HomeFragment.ID_SERUM:
                tvProductType.setText(TYPE_SERUM);
                new LoadProducts().execute(TYPE_SERUM);
                break;
            case HomeFragment.ID_MOISTURISER:
                tvProductType.setText(TYPE_MOISTURISER);
                new LoadProducts().execute(TYPE_MOISTURISER);
                break;
            case HomeFragment.ID_EXFOLIATOR:
                tvProductType.setText(TYPE_EXFOLIATOR);
                new LoadProducts().execute(TYPE_EXFOLIATOR);
                break;
            case HomeFragment.ID_SUNSCREEN:
                tvProductType.setText(TYPE_SUNSCREEN);
                new LoadProducts().execute(TYPE_SUNSCREEN);
                break;
        }
    }

    private class LoadProducts extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            FireBaseProductManager.DataChangeListener dataChangeListener = createCallBack();
            new FireBaseProductManager("Products").getDataProduct(strings[0], dataChangeListener);
            return null;
        }

        private FireBaseProductManager.DataChangeListener createCallBack() {
            return new FireBaseProductManager.DataChangeListener() {
                @Override
                public void loadData(Product product, String key) {
                    productList.add(product);
                    keys.add(key);
                    adapter.notifyItemInserted(productList.size() - 1);
                }
            };
        }
    }

    private void setRecyclerView() {
        rv.setHasFixedSize(true);
        adapter = new ProductAdapter(getContext(), productList, R.layout.item_product, this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }


}
