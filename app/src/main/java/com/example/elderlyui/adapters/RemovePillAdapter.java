package com.example.elderlyui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elderlyui.R;
import com.example.elderlyui.models.Pill;

import java.util.List;

public class RemovePillAdapter extends RecyclerView.Adapter<RemovePillAdapter.RemovePillViewHolder> {
    private List<Pill> pillList;
    private OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(Pill pill);
    }

    public RemovePillAdapter(List<Pill> pillList, OnRemoveClickListener onRemoveClickListener) {
        this.pillList = pillList;
        this.onRemoveClickListener = onRemoveClickListener;
    }

    @NonNull
    @Override
    public RemovePillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remove_pill, parent, false);
        return new RemovePillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemovePillViewHolder holder, int position) {
        Pill pill = pillList.get(position);
        holder.pillName.setText(pill.getName());

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveClickListener.onRemoveClick(pill);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pillList.size();
    }

    public static class RemovePillViewHolder extends RecyclerView.ViewHolder {
        TextView pillName;
        Button removeButton;

        public RemovePillViewHolder(@NonNull View itemView) {
            super(itemView);
            pillName = itemView.findViewById(R.id.pillName);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
