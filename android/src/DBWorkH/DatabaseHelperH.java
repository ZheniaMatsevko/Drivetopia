package DBWorkH;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperH extends SQLiteOpenHelper {
    private static DatabaseHelperH instance;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "drivetopiaDB.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelperH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelperH getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelperH(context.getApplicationContext());
        }
        return instance;
    }

    public synchronized SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            database = getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "user" table

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY NOT NULL, " +
                "name TEXT NOT NULL, " +
                "surname TEXT NOT NULL, " +
                "fathername TEXT, " +
                "score INTEGER DEFAULT (0) NOT NULL, " +
                "dateOfBirth TEXT NOT NULL, " +
                "failures INTEGER NOT NULL DEFAULT (0), " +
                "pass INTEGER NOT NULL DEFAULT (0)" +
                ")";
        db.execSQL(createUsersTable);


        // Create "userInfo" table
        String createInfoTable = "CREATE TABLE IF NOT EXISTS userInfo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL" +
                ")";
        db.execSQL(createInfoTable);

        // Create "theory" table
        String createTheoryTable = "CREATE TABLE IF NOT EXISTS theory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "pictures TEXT NOT NULL, " +
                "texts TEXT NOT NULL" +
                ")";
        db.execSQL(createTheoryTable);

        // Insert initial data
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if necessary
        // For example, you can drop the existing table and create a new one

    }

    public void insertInitialData(SQLiteDatabase db) {
        // Insert initial data into the "user" table
        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put("name", "Тест");
        values.put("surname", "Тестович");
        values.put("fathername", "Тестовенко");
        values.put("score", 0);
        values.put("dateOfBirth", "1990-01-01");
        values.put("failures", 0);
        values.put("pass", 0);

        long newRowId = db.insertWithOnConflict("users", null, values,SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues values2 = new ContentValues();

        values2.put("id", "2");
        values2.put("name", "Олена");
        values2.put("surname", "Пєчкурова");
        values2.put("fathername", "Миколаївна");
        values2.put("score", 700);
        values2.put("dateOfBirth", "1996-02-03");
        values2.put("failures", 3);
        values2.put("pass", 1);

        newRowId = db.insertWithOnConflict("users", null, values2,SQLiteDatabase.CONFLICT_REPLACE);

        // Insert email and password into the "userInfo" table
        ContentValues userInfoValues1 = new ContentValues();
        userInfoValues1.put("id", 1); // Use the newly inserted row ID from the "users" table
        userInfoValues1.put("email", "test@example.com");
        userInfoValues1.put("password", "12345678");

        db.insertWithOnConflict("userInfo", null, userInfoValues1, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues userInfoValues2 = new ContentValues();
        userInfoValues2.put("id", 2); // Use the next row ID
        userInfoValues2.put("email", "pyechkurova@ukma.edu.ua");
        userInfoValues2.put("password", "ilovecats1234");

        db.insertWithOnConflict("userInfo", null, userInfoValues2, SQLiteDatabase.CONFLICT_REPLACE);

    }
}
