package com.tectoy.fullscanner.model.sqlite;

import android.provider.BaseColumns;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public final class DatabaseContract {

    private DatabaseContract() {
    }

    public static class FeedProduct implements BaseColumns{
        public static final String TABLE_NAME = "tbdproduct";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_CODE = "barcode";
        public static final String COLUMN_NAME_IMAGE = "image";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedProduct.TABLE_NAME + " (" +
                FeedProduct.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                FeedProduct.COLUMN_NAME_NAME + " TEXT," +
                FeedProduct.COLUMN_NAME_DESC + " TEXT," +
                FeedProduct.COLUMN_NAME_VALUE + " DOUBLE," +
                FeedProduct.COLUMN_NAME_CODE + " TEXT, " +
                FeedProduct.COLUMN_NAME_IMAGE + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedProduct.TABLE_NAME;
    }
}
