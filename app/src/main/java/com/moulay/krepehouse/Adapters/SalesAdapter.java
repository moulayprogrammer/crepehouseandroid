package com.moulay.krepehouse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moulay.krepehouse.Models.Sale;
import java.util.List;
import com.moulay.krepehouse.R;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SaleViewHolder> {

    private List<Sale> saleList;

    public static class SaleViewHolder extends RecyclerView.ViewHolder {
        TextView tvSaleNumber, tvSaleDateTime, tvProductsCount, tvSaleAmount;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSaleNumber = itemView.findViewById(R.id.tvSaleNumber);
            tvSaleDateTime = itemView.findViewById(R.id.tvSaleDateTime);
            tvProductsCount = itemView.findViewById(R.id.tvProductsCount);
            tvSaleAmount = itemView.findViewById(R.id.tvSaleAmount);
        }
    }

    public SalesAdapter(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale_card, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        holder.tvSaleNumber.setText(sale.getSaleNumber());
        holder.tvSaleDateTime.setText(sale.getDateTime());
        holder.tvProductsCount.setText(sale.getProductsCount() + " منتجات");
        holder.tvSaleAmount.setText(String.format("%,.2f دج", sale.getAmount()));
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }
}