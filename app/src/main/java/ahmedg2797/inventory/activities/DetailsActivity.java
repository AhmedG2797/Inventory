package ahmedg2797.inventory.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ahmedg2797.inventory.R;
import ahmedg2797.inventory.database.DatabaseHelper;
import ahmedg2797.inventory.utils.Product;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Product product;
    private DatabaseHelper DB_Helper;
    private ImageView image_of_product;
    private TextView name_of_product, quantity_of_product, price_of_product, email_of_product;
    private Button increase_button, decrease_button, order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        DB_Helper = new DatabaseHelper(this);
        if (getIntent() != null && getIntent().hasExtra("prod")){
            product = (Product) getIntent().getSerializableExtra("prod");
        }

        initializeViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        name_of_product.setText(product.getName());
        quantity_of_product.setText(String.valueOf(product.getQuantity()));
        price_of_product.setText(String.valueOf(product.getPrice()));
        email_of_product.setText(product.getEmail());
        image_of_product.setImageURI(Uri.parse(product.getImage()));
//        Picasso.with(DetailsActivity.this)
//                .load(product.getImage())
//                .placeholder(R.color.colorPrimaryDark)
//                .error(R.color.colorPrimary)
//                .into(image_of_product);
    }

    private void initializeViews() {

        image_of_product = findViewById(R.id.image_of_product);

        name_of_product = findViewById(R.id.name_of_product);
        quantity_of_product = findViewById(R.id.quantity_of_product);
        price_of_product = findViewById(R.id.price_of_product);
        email_of_product = findViewById(R.id.email_of_product);

        increase_button = findViewById(R.id.increase_button);
        increase_button.setOnClickListener(this);

        decrease_button = findViewById(R.id.decrease_button);
        decrease_button.setOnClickListener(this);

        order_button = findViewById(R.id.order_button);
        order_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.increase_button):
                if (product.getQuantity() >= 0){
                    product.setQuantity(product.getQuantity() + 1);
                    DB_Helper.updateProduct(product.getId(), product.getQuantity());

                    quantity_of_product.setText(String.valueOf(product.getQuantity()));
                } else {
                    Toast.makeText(this, "Quantity = 0", Toast.LENGTH_SHORT).show();
                }
                break;
            case(R.id.decrease_button):
                if (product.getQuantity() > 0){
                    product.setQuantity(product.getQuantity() - 1);
                    DB_Helper.updateProduct(product.getId(), product.getQuantity());

                    quantity_of_product.setText(String.valueOf(product.getQuantity()));
                } else {
                    Toast.makeText(this, "Quantity = 0", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.order_button):

                String message = "Product Name : " + product.getName() +
                        "\nProduct Quantity : " + product.getQuantity();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Order Product");
                intent.putExtra(Intent.EXTRA_TEXT, message);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.delete_icon):

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation")
                        .setMessage("Are You sure to delete this product ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DB_Helper.deleteProductByID(product.getId());
                                Toast.makeText(DetailsActivity.this,product.getName() + " Is Deleted",
                                        Toast.LENGTH_SHORT).show();
                                finish();
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
