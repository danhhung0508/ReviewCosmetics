package com.example.reviewcosmetics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ItemClickListener{
    public static final int ID_CLEANSER = 0;
    public static final int ID_TONER = 1;
    public static final int ID_SERUM = 2;
    public static final int ID_MOISTURISER = 3;
    public static final int ID_EXFOLIATOR = 4;
    public static final int ID_SUNSCREEN = 5;
    private static final int ID_MASK = 6;
    private static final int ID_LOTION = 7;

    List<Category> categories;
    CategoryAdapter adapterCategory;
    RecyclerView recyclerView;
    ItemClickListener productTypeClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ItemClickListener) {
            productTypeClick = (ItemClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setDataCategories();
        setRecyclerView(view);
        return view;
    }

    @Override
    public void itemClick(int position) {
        productTypeClick.itemClick(categories.get(position).getId());
    }

    @Override
    public void itemClick(Product product, String key) {

    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rvFragHome);
        recyclerView.setHasFixedSize(true);
        adapterCategory = new CategoryAdapter(getContext(), categories, R.layout.item_category, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterCategory);
    }

    private void setDataCategories() {
        categories = new ArrayList<>();
        Category category0 = new Category("Cleanser", R.drawable.cleanser, ID_CLEANSER);
        Category category1 = new Category("Toner", R.drawable.toner, ID_TONER);
        Category category2 = new Category("Serum", R.drawable.serum, ID_SERUM);
        Category category3 = new Category("Moisturiser", R.drawable.cream, ID_MOISTURISER);
        Category category4 = new Category("Exfoliator", R.drawable.exfoliate, ID_EXFOLIATOR);
        Category category5 = new Category("Sunscreen", R.drawable.sunscreen, ID_SUNSCREEN);
        categories.add(category0);
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);
    }
}
