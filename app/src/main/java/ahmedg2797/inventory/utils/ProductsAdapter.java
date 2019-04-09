package ahmedg2797.inventory.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ahmedg2797.inventory.R;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context context = null;
    private List<Product> productList = null;
    private RecyclerViewClickListener recyclerViewClickListener = null;

    public ProductsAdapter(Context context, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public void setDataOfProducts(List<Product> list) {

        if (this.productList != null) {
            this.productList.clear();
        }
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {

        Product currentProduct = productList.get(position);
        productViewHolder.name.setText(currentProduct.getName());
        productViewHolder.quantity.setText(String.valueOf(currentProduct.getQuantity()));
        productViewHolder.price.setText(String.valueOf(currentProduct.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (this.productList == null)
            return 0;
        else
            return this.productList.size();
    }

    public interface RecyclerViewClickListener {
        void onSaleClick(View view, int position);

        void onProductClick(View view, int position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, quantity, price;
        private Button sale;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_of_product);
            quantity = itemView.findViewById(R.id.quantity_of_product);
            price = itemView.findViewById(R.id.price_of_product);
            sale = itemView.findViewById(R.id.sale_of_product);

            sale.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == (R.id.sale_of_product)){
                recyclerViewClickListener.onSaleClick(view, getAdapterPosition());
            } else {
                recyclerViewClickListener.onProductClick(view, getAdapterPosition());
            }
        }
    }
}
