package ahmedg2797.inventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ahmedg2797.inventory.utils.Product;

import static ahmedg2797.inventory.database.Contract.DB_NAME;
import static ahmedg2797.inventory.database.Contract.DB_VERSION;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_EMAIL;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_ID;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_IMAGE;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_NAME;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_PRICE;
import static ahmedg2797.inventory.database.Contract.Entry.COLUMN_QUANTITY;
import static ahmedg2797.inventory.database.Contract.Entry.CREATE_TABLE;
import static ahmedg2797.inventory.database.Contract.Entry.DELETE_TABLE;
import static ahmedg2797.inventory.database.Contract.Entry.TABLE_PRODUCTS;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context = null;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertProduct(Product product){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_EMAIL, product.getEmail());
        values.put(COLUMN_IMAGE, product.getImage());

        sqLiteDatabase.insert(TABLE_PRODUCTS, null, values);
    }

    public List<Product> getAllProducts (){
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selection_quary = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor cursor = sqLiteDatabase.rawQuery(selection_quary, null);
        if (cursor.moveToFirst()){
            do {
                int P_id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String P_name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int P_quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                int P_price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                String P_email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String P_image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));

                Product product = new Product(P_id, P_name, P_quantity, P_price, P_email, P_image);
                productList.add(product);

            } while (cursor.moveToNext());
        }
        return productList;
    }

    public Product getProductByID(int id){
        Product product = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY,
                COLUMN_PRICE, COLUMN_EMAIL, COLUMN_IMAGE};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = sqLiteDatabase.query(TABLE_PRODUCTS, projection, selection, selectionArgs,
                null, null, null);
        if (cursor.moveToFirst()){
            int P_id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String P_name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            int P_quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
            int P_price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
            String P_email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String P_image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));

            product = new Product(P_id, P_name, P_quantity, P_price, P_email, P_image);
        }
        return product;
    }

    public void updateProduct(int id, int quantity){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, quantity);

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        sqLiteDatabase.update(TABLE_PRODUCTS, values, selection, selectionArgs);
    }

    public void deleteProductByID(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        sqLiteDatabase.delete(TABLE_PRODUCTS, selection, selectionArgs);
    }

    public void deleteAllProducts(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_PRODUCTS, null, null);
    }
}



















