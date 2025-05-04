package com.moulay.krepehouse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.R;

import java.util.ArrayList;
import java.util.List;

public class PurchasedFoodAdapter extends RecyclerView.Adapter<PurchasedFoodAdapter.ViewHolder> {

    private List<Food> foods;
    private Context context;

    public PurchasedFoodAdapter(List<Food> foods) {
        this.foods = foods != null ? foods : new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvNameArabic, tvNameFrench, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.ivProductImage);
            tvNameArabic = itemView.findViewById(R.id.tvProductNameArabic);
            tvNameFrench = itemView.findViewById(R.id.tvProductNameFrench);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_purchased_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = foods.get(position);
        Glide.with(context).load(food.getPicture()).into(holder.imgProduct);
        holder.tvNameArabic.setText(food.getNameAr());
        holder.tvNameFrench.setText(food.getNameFr());
        holder.tvPrice.setText(String.valueOf(food.getPrice()));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    /**
     * Update the list of foods.
     */
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
