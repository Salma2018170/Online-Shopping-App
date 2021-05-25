package com.example.shopping.ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.Ui.Model;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<Model> models;
    private RecyclerViewEvent recyclerViewEvent;

    public CategoryAdapter(Context mContext, List<Model> models, RecyclerViewEvent recyclerViewEvent) {
        this.mContext = mContext;
        this.models = models;
        this.recyclerViewEvent = recyclerViewEvent;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = models.get(position);
        holder.name.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.img.setImageResource(model.getImg());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout productItem;
        private TextView name, price;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productItem = itemView.findViewById(R.id.product_dialog);
            name = itemView.findViewById(R.id.imageTitle);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.Image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != -1) {
                        Model model = models.get(getAdapterPosition());
                        recyclerViewEvent.onRecyclerViewClick(model);
                    }
                }
            });
        }
    }

    public void filterList(ArrayList<Model> filteredList) {
        models = filteredList;
        notifyDataSetChanged();
    }

}

