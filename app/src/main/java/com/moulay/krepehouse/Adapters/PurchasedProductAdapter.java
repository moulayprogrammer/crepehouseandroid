package com.moulay.krepehouse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Models.PurchasedProduct;
import com.moulay.krepehouse.R;

import java.util.ArrayList;
import java.util.List;

public class PurchasedProductAdapter extends RecyclerView.Adapter<PurchasedProductAdapter.ViewHolder> {

    private List<PurchasedProduct> products;

    public PurchasedProductAdapter(List<PurchasedProduct> products) {
        this.products = products != null ? products : new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvNameArabic, tvNameFrench, tvQuantity, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.ivProductImage);
            tvNameArabic = itemView.findViewById(R.id.tvProductNameArabic);
            tvNameFrench = itemView.findViewById(R.id.tvProductNameFrench);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
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
        PurchasedProduct product = products.get(position);
        holder.imgProduct.setImageResource(product.getImageResId());
        holder.tvNameArabic.setText(product.getNameArabic());
        holder.tvNameFrench.setText(product.getNameFrench());
        holder.tvQuantity.setText(product.getQuantity());
        holder.tvPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Update the list of purchased products.
     */
    public void setProducts(List<PurchasedProduct> newProducts) {
        this.products = newProducts != null ? newProducts : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Add a product to the list.
     */
    public void addProduct(PurchasedProduct product) {
        products.add(product);
        notifyItemInserted(products.size() - 1);
    }
}
