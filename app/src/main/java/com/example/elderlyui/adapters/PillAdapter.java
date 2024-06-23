package com.example.elderlyui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elderlyui.R;
import com.example.elderlyui.models.Pill;

import java.util.List;

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.PillViewHolder> {
    private List<Pill> pillList;

    public PillAdapter(List<Pill> pillList) {
        this.pillList = pillList;
    }

    @NonNull
    @Override
    public PillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pill, parent, false);
        return new PillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PillViewHolder holder, int position) {
        Pill pill = pillList.get(position);
        holder.pillName.setText(pill.getName());
        holder.pillDose.setText(pill.getDose()+" mg");

        holder.pillMorning.setVisibility(pill.isMorning() ? View.VISIBLE : View.GONE);
        holder.pillMesimeri.setVisibility(pill.isMesimeri() ? View.VISIBLE : View.GONE);
        holder.pillNoon.setVisibility(pill.isNoon() ? View.VISIBLE : View.GONE);
        holder.pillNight.setVisibility(pill.isNight() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return pillList.size();
    }

    public static class PillViewHolder extends RecyclerView.ViewHolder {
        TextView pillName, pillDose, pillMorning, pillMesimeri, pillNoon, pillNight;

        public PillViewHolder(@NonNull View itemView) {
            super(itemView);
            pillName = itemView.findViewById(R.id.pillName);
            pillDose = itemView.findViewById(R.id.pillDose);
            pillMorning = itemView.findViewById(R.id.pillMorning);
            pillMesimeri = itemView.findViewById(R.id.pillMesimeri);
            pillNoon = itemView.findViewById(R.id.pillNoon);
            pillNight = itemView.findViewById(R.id.pillNight);
        }
    }
}
