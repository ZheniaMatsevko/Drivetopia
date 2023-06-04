package DBWorkH;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperH extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelperH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "user" table
        String createTableQuery = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, email TEXT, password TEXT, name TEXT)";
        db.execSQL(createTableQuery);

        // Insert initial data
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if necessary
        // For example, you can drop the existing table and create a new one
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS user");
            onCreate(db);
        }
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Insert initial data into the "user" table
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("email", "example@email.com");
        values.put("password", "password123");
        values.put("name", "Andrew");

        db.insert("user", null, values);
    }
}
