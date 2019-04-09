package ahmedg2797.inventory.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ahmedg2797.inventory.R;
import ahmedg2797.inventory.database.DatabaseHelper;
import ahmedg2797.inventory.utils.Product;
import ahmedg2797.inventory.utils.ProductsAdapter;

public class MainActivity extends AppCompatActivity implements ProductsAdapter.RecyclerViewClickListener,
        View.OnClickListener {

    private RecyclerView recyclerView = null;
    private ProductsAdapter adapter = null;
    private FloatingActionButton actionButton = null;
    private TextView emptyView = null;

    private DatabaseHelper DB_Helper = null;
    private List<Product> listOfProducts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        DB_Helper = new DatabaseHelper(this);
        listOfProducts = new ArrayList<>();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.list_of_product);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));

        adapter = new ProductsAdapter(this, this);
        recyclerView.setAdapter(adapter);

        emptyView = findViewById(R.id.empty_view);

        actionButton = findViewById(R.id.add_product);
        actionButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listOfProducts = DB_Helper.getAllProducts();
        if (listOfProducts.isEmpty()){

            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {

            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter.setDataOfProducts(listOfProducts);
    }

    @Override
    public void onSaleClick(View view, int position) {
        Product product = listOfProducts.get(position);
        if (product.getQuantity() > 0){
            product.setQuantity(product.getQuantity() - 1);
            DB_Helper.updateProduct(product.getId(), product.getQuantity());

            listOfProducts.clear();
            listOfProducts = DB_Helper.getAllProducts();
            adapter.setDataOfProducts(listOfProducts);
        } else {
            Toast.makeText(this, "Quantity = 0", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("prod", listOfProducts.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.add_product):
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case (R.id.delete_all):

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation")
                        .setMessage("Are You sure to delete all products ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DB_Helper.deleteAllProducts();
                                listOfProducts = DB_Helper.getAllProducts();
                                if (listOfProducts.isEmpty()){

                                    recyclerView.setVisibility(View.GONE);
                                    emptyView.setVisibility(View.VISIBLE);

                                } else {

                                    recyclerView.setVisibility(View.VISIBLE);
                                    emptyView.setVisibility(View.GONE);
                                }
                                adapter.setDataOfProducts(listOfProducts);

                                Toast.makeText(MainActivity.this, "All Products Is Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
