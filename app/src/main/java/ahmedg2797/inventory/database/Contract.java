package ahmedg2797.inventory.database;

import android.provider.BaseColumns;

public final class Contract {

    public final static String DB_NAME = "inventory.db";
    public final static int DB_VERSION = 1;

    private Contract() {
    }

    public static final class Entry implements BaseColumns {

        public final static String TABLE_PRODUCTS = "products";

        public final static String COLUMN_ID = BaseColumns._ID;
        public final static String COLUMN_NAME  = "name";
        public final static String COLUMN_QUANTITY  = "quantity";
        public final static String COLUMN_PRICE  = "price";
        public final static String COLUMN_EMAIL  = "email";
        public final static String COLUMN_IMAGE  = "image";

        public final static String CREATE_TABLE  = "CREATE TABLE " + TABLE_PRODUCTS + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " INTEGER NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " TEXT NOT NULL )";

        public final static String DELETE_TABLE = "DROP TABLE " + TABLE_PRODUCTS + " IF EXISTS";
    }
}
