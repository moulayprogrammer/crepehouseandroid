package com.moulay.krepehouse.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.PurchasedFood;
import com.moulay.krepehouse.Models.Sale;

import java.util.ArrayList;
import java.util.List;
import com.moulay.krepehouse.R;

import org.threeten.bp.format.DateTimeFormatter;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SaleViewHolder> {

    private List<Sale> saleList;

    public static class SaleViewHolder extends RecyclerView.ViewHolder {
        TextView tvSaleNumber, tvSaleDate, tvSaleTime, tvProductsCount, tvSaleAmount;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSaleNumber = itemView.findViewById(R.id.tvSaleNumber);
            tvSaleDate = itemView.findViewById(R.id.tvSaleDate);
            tvSaleTime = itemView.findViewById(R.id.tvSaleTime);
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

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        holder.tvSaleNumber.setText(sale.getBillNumber());
        holder.tvSaleDate.setText(sale.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        holder.tvSaleTime.setText(sale.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        holder.tvProductsCount.setText(sale.getProductsCount() + " منتجات");
        holder.tvSaleAmount.setText(String.format("%,.2f دج", sale.getAmount()));
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    public void setSales(List<Sale> sales) {
        this.saleList = sales != null ? sales : new ArrayList<>();
        notifyDataSetChanged();
    }
}