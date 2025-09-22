package com.example.bcodegen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.ViewHolder> {

    private final List<BarcodeEntity> barcodeList;

    public BarcodeAdapter(List<BarcodeEntity> barcodeList) {
        this.barcodeList = barcodeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barcode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BarcodeEntity barcode = barcodeList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

        holder.barcodeDataTextView.setText("Barcode: " + barcode.getBarcodeData());
        holder.timestampTextView.setText("Scanned on: " + sdf.format(barcode.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return barcodeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView barcodeDataTextView, timestampTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            barcodeDataTextView = itemView.findViewById(R.id.barcodeDataTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
