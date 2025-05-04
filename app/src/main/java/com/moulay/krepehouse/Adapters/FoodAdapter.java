package com.moulay.krepehouse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moulay.krepehouse.Models.Food;

import java.util.ArrayList;
import java.util.List;
import  com.moulay.krepehouse.R;
import android.view.LayoutInflater;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foods;
    private OnFoodClickListener listener;
    private Context context;

    public interface OnFoodClickListener {
        void onFoodClick(Food food);
    }

    public FoodAdapter(List<Food> foods, OnFoodClickListener listener, Context context) {
        this.foods = foods;
        this.context = context;
        this.listener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameArabic;
        TextView nameFrench;
        TextView price;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivProductImage);
            nameArabic = itemView.findViewById(R.id.tvProductNameArabic);
            nameFrench = itemView.findViewById(R.id.tvProductNameFrench);
            price = itemView.findViewById(R.id.tvProductPrice);
        }

        public void bind(Food food, OnFoodClickListener listener) {
            itemView.setOnClickListener(v -> listener.onFoodClick(food));
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        Glide.with(context).load(food.getPicture()).into(holder.imageView);
        holder.nameArabic.setText(food.getNameAr());
        holder.nameFrench.setText(food.getNameFr());
        holder.price.setText(String.valueOf(food.getPrice()));
        holder.bind(food, listener);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFoods(List<Food> newFoods) {
        this.foods = newFoods != null ? newFoods : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Add a food to the list.
     */
    public void addFood(Food food) {
        foods.add(food);
        notifyItemInserted(foods.size() - 1);
    }
}


