package com.moulay.krepehouse.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moulay.krepehouse.Models.Product;
import java.util.List;
import  com.moulay.krepehouse.R;
import android.view.LayoutInflater;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameArabic;
        TextView nameFrench;
        TextView price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivProductImage);
            nameArabic = itemView.findViewById(R.id.tvProductNameArabic);
            nameFrench = itemView.findViewById(R.id.tvProductNameFrench);
            price = itemView.findViewById(R.id.tvProductPrice);
        }
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imageView.setImageResource(product.getImageResId());
        holder.nameArabic.setText(product.getNameArabic());
        holder.nameFrench.setText(product.getNameFrench());
        holder.price.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

