package ahmedg2797.inventory.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ahmedg2797.inventory.R;
import ahmedg2797.inventory.database.DatabaseHelper;
import ahmedg2797.inventory.utils.Product;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_CODE = 100;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    private EditText editName, editQuantity, editPrice, editMail;
    private Button pickImage;
    private ImageView imageProduct;
    private Uri imageUri;
    private Intent gallery;

    private DatabaseHelper DB_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        initializeViews();
        DB_Helper = new DatabaseHelper(this);
    }

    private void initializeViews() {

        editName = findViewById(R.id.edit_name);
        editQuantity = findViewById(R.id.edit_quantity);
        editPrice = findViewById(R.id.edit_price);
        editMail = findViewById(R.id.edit_mail);
        imageProduct = findViewById(R.id.image_picked);
        pickImage = findViewById(R.id.button_pick_image);
        pickImage.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.save_icon):

                if (!editName.getText().toString().isEmpty() &&
                        !editQuantity.getText().toString().isEmpty() &&
                        !editPrice.getText().toString().isEmpty() &&
                        !editMail.getText().toString().isEmpty() &&
                        imageProduct.getDrawable() != null){

                    Product product = new Product();
                    String P_name = editName.getText().toString();
                    int P_quantity = Integer.parseInt(editQuantity.getText().toString());
                    int P_price = Integer.parseInt(editPrice.getText().toString());
                    String P_email = editMail.getText().toString();
                    String P_image = String.valueOf(imageUri);

                    product = new Product(P_name, P_quantity, P_price, P_email, P_image);
                    DB_Helper.insertProduct(product);
                    Toast.makeText(this, P_name + " Is Added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (editName.getText().toString().isEmpty()){
                        editName.setBackground(getResources().getDrawable(R.drawable.background_error));
                    } else {
                        editName.setBackground(getResources().getDrawable(R.drawable.background_edit_text));
                    }

                    if (editQuantity.getText().toString().isEmpty()){
                        editQuantity.setBackground(getResources().getDrawable(R.drawable.background_error));
                    } else {
                        editQuantity.setBackground(getResources().getDrawable(R.drawable.background_edit_text));
                    }

                    if (editPrice.getText().toString().isEmpty()){
                        editPrice.setBackground(getResources().getDrawable(R.drawable.background_error));
                    } else {
                        editPrice.setBackground(getResources().getDrawable(R.drawable.background_edit_text));
                    }

                    if (editMail.getText().toString().isEmpty()){
                        editMail.setBackground(getResources().getDrawable(R.drawable.background_error));
                    } else {
                        editMail.setBackground(getResources().getDrawable(R.drawable.background_edit_text));
                    }

                    if (imageProduct.getDrawable() == null){
                        imageProduct.setBackground(getResources().getDrawable(R.drawable.background_error));
                    }

                    Toast.makeText(this, "You must enter all Data", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        gallery = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            startActivityForResult(gallery, PICK_IMAGE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(gallery, PICK_IMAGE_CODE);
                } else {
                    Toast.makeText(getBaseContext(), "You can't pick image without permission.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE){
            imageUri = data.getData();
            Picasso.with(AddActivity.this)
                    .load(imageUri)
                    .placeholder(R.color.colorPrimaryDark)
                    .error(R.color.colorPrimary)
                    .into(imageProduct);
            imageProduct.setBackground(null);
        }
    }
}
