package com.example.shopping.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.ProductClickListener;
import com.example.shopping.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private List<Product> productsList;
    private Context context;
    private ProductClickListener productClickListener;

    public ProductsAdapter(List<Product> productsList, Context context,ProductClickListener productClickListener) {
        this.productsList = productsList;
        this.context = context;
        this.productClickListener=productClickListener;
    }

    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder holder, int position) {
        Product product= productsList.get(position);
        holder.name.setText(product.getName());
        holder.cost.setText(product.getCost());
        holder.quantities.setText(String.valueOf(product.getQuantities()));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,quantities,cost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            quantities=itemView.findViewById(R.id.quantities);
            cost=itemView.findViewById(R.id.cost);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() != -1) {
                Product product = productsList.get(getAdapterPosition());
                productClickListener.onItemClick(view, product);
            }
        }
    }
}
