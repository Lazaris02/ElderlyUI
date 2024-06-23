package com.example.elderlyui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elderlyui.R;
import com.example.elderlyui.models.TefteriItem;

import java.util.List;

public class TefteriAdapter extends RecyclerView.Adapter<TefteriAdapter.SuperMarketViewHolder> {
    private List<TefteriItem> itemList;
    private boolean isDeleteMode = false;
    private OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(TefteriItem item);
    }

    public TefteriAdapter(List<TefteriItem> itemList, OnRemoveClickListener onRemoveClickListener) {
        this.itemList = itemList;
        this.onRemoveClickListener = onRemoveClickListener;
    }

    public void setDeleteMode(boolean isDeleteMode) {
        this.isDeleteMode = isDeleteMode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuperMarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tefteri, parent, false);
        return new SuperMarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperMarketViewHolder holder, int position) {
        TefteriItem item = itemList.get(position);
        holder.itemName.setText(item.getName());
        //holder.removeButton.setVisibility(isDeleteMode ? View.VISIBLE : View.GONE);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveClickListener.onRemoveClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class SuperMarketViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        Button removeButton;

        public SuperMarketViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }
}
