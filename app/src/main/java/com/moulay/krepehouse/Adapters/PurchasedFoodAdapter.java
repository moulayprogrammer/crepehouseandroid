package com.moulay.krepehouse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.PurchasedFood;
import com.moulay.krepehouse.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchasedFoodAdapter extends RecyclerView.Adapter<PurchasedFoodAdapter.ViewHolder> {

    private List<PurchasedFood> purchasedFoods;
    private List<Food> foods;
    private OnPurchaseFoodClickListener listener;
    private Context context;


    public interface OnPurchaseFoodClickListener {
        void onPurchaseFood(Food food,PurchasedFood purchasedFood);
    }

    public PurchasedFoodAdapter(List<Food> foods, List<PurchasedFood> purchasedFoods, OnPurchaseFoodClickListener listener, Context context) {
        this.purchasedFoods = purchasedFoods != null ? purchasedFoods : new ArrayList<>();
        this.foods = foods != null ? foods : new ArrayList<>();
        this.listener = listener;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameArabic, tvQte, tvTotal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameArabic = itemView.findViewById(R.id.tv_purchase_food_name_ar);
            tvQte = itemView.findViewById(R.id.tv_purchase_food_qte);
            tvTotal = itemView.findViewById(R.id.tv_purchase_food_total);
        }

        public void bind(Food food,PurchasedFood purchasedFood, OnPurchaseFoodClickListener listener) {
            itemView.setOnClickListener(v -> listener.onPurchaseFood(food,purchasedFood));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_purchased_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PurchasedFood purchasedFood = purchasedFoods.get(position);
        Food food = foods.get(position);

        holder.tvNameArabic.setText(purchasedFood.getNameAr());
        holder.tvQte.setText(String.valueOf(purchasedFood.getQte()));
        // Format to 2 decimal places (rounds if needed)
        DecimalFormat df = new DecimalFormat("#0.00");
        String formatted = df.format(purchasedFood.getTotal()); // Result: "123.46"
        holder.tvTotal.setText(formatted);

        holder.bind(food,purchasedFood,listener);
    }

    @Override
    public int getItemCount() {
        return purchasedFoods.size();
    }

    public List<PurchasedFood> getPurchaseFoods(){
        return this.purchasedFoods;
    }

    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Update the list of foods.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setPurchasedFoods(List<Food> newFoods,List<PurchasedFood> newPurchaseFoods) {
        this.foods = newFoods != null ? newFoods : new ArrayList<>();
        this.purchasedFoods = newPurchaseFoods != null ? newPurchaseFoods : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Add a food to the list.
     */
    public void addFood(Food food,PurchasedFood purchasedFood) {
        foods.add(food);
        purchasedFoods.add(purchasedFood);
        notifyItemInserted(purchasedFoods.size() - 1);
    }

    public void updateFood(int position, int newQte, float newPrice){
        purchasedFoods.get(position).setQte(newQte);
        foods.get(position).setPrice(newPrice);
        purchasedFoods.get(position).setTotal(newQte*newPrice);
        notifyItemChanged(position);
    }

    public void deleteFood(int position) {
        foods.remove(position);
        purchasedFoods.remove(position);
        notifyItemRemoved(position);
    }
}
