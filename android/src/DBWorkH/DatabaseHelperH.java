package DBWorkH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        db.execSQL("PRAGMA foreign_keys = ON;");

        // Create the "user" table
        //інформація про юзерів: ПІБ, дата народження, кількість очок за практику, кількість завалів фінального тесту, чи пройшов тест

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
        //емейли та паролі юзерів
        String createInfoTable = "CREATE TABLE IF NOT EXISTS userInfo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL" +
                ")";
        db.execSQL(createInfoTable);


        // Create "scores" table
        //набрана кількість очок за кожну практику
        String createScoresTable = "CREATE TABLE IF NOT EXISTS scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "userId INTEGER NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "score INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE" +
                ")";

        db.execSQL(createScoresTable);



        // Create "theory" table
        //таблиця для збереження інформації для кожного екрану теорії
        String createTheoryTable = "CREATE TABLE IF NOT EXISTS theory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "pictures TEXT NOT NULL, " +
                "texts TEXT NOT NULL" +
                ")";
        db.execSQL(createTheoryTable);




        // Create "simpleChoiceQuestion" table
        //таблиця для збереження інформації про кожні прості тести з вибором
        String createSimpleChoiceQuestion = "CREATE TABLE IF NOT EXISTS simpleChoiceQuestion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "text TEXT NOT NULL, " +
                "answer TEXT NOT NULL, " +
                "picture TEXT" +
                ")";
        db.execSQL(createSimpleChoiceQuestion);


        // Create "wrongChoices" table
        //таблиця для збереження інформації про неправильні відповіді у тестах
        String createWrongChoices = "CREATE TABLE IF NOT EXISTS wrongChoices (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "wrongAnswer TEXT NOT NULL, " +
                "questionId INTEGER NOT NULL, " +
                "FOREIGN KEY (questionId) REFERENCES simpleChoiceQuestion(id) ON DELETE CASCADE" +
                ")";

        db.execSQL(createWrongChoices);




        // Insert initial data
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if necessary
        // For example, you can drop the existing table and create a new one

    }

    public void insertInitialData(SQLiteDatabase db) {


        /**Заповнюємо інформацію про юзерів (айді 1,2)**/

        // Insert initial data into the "user" table
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "Тест");
        values.put("surname", "Тестович");
        values.put("fathername", "Тестовенко");
        values.put("score", 0);
        values.put("dateOfBirth", "1990-01-01");
        values.put("failures", 0);
        values.put("pass", 0);

        long newRowId = db.insertWithOnConflict("users", null, values,SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues values2 = new ContentValues();

        values2.put("id", 2);
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

        for (int userId = 1; userId <= 2; userId++) {
            for (int levelNumb = 1; levelNumb <= 10; levelNumb++) {
                ContentValues valuess = new ContentValues();
                valuess.put("userId", userId);
                valuess.put("levelNumb", levelNumb);
                valuess.put("score", 0);

                long rowId = db.insert("scores", null, valuess);

                if (rowId == -1) {
                    // Failed to insert the row, handle the error if needed
                }
            }
        }








        /**Заповнюємо інформацію про рівні**/




        /**1) Тестові питання з варіантами відповіді**/


        /**рівень 1**/
        ContentValues simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 1);
        simpleValues.put("text", "Дозволи на будівництво та експлуатацію об'єктів біля доріг видають органи дорожнього управління, а платить і погоджує їх:");
        simpleValues.put("answer", "поліція");
        simpleValues.put("picture", "noPicture");

         long questionId = db.insert("simpleChoiceQuestion", null, simpleValues);



            ContentValues wrongChoiceValues = new ContentValues();
            wrongChoiceValues.put("questionId", 1);
            wrongChoiceValues.put("wrongAnswer", "президент");
            db.insert("wrongChoices", null, wrongChoiceValues);

            wrongChoiceValues.clear();

            wrongChoiceValues.put("questionId", 1);
            wrongChoiceValues.put("wrongAnswer", "місцева влада");
            db.insert("wrongChoices", null, wrongChoiceValues);
            wrongChoiceValues.clear();

            wrongChoiceValues.put("questionId", 1);
            wrongChoiceValues.put("wrongAnswer", "спілка водіїв");
            db.insert("wrongChoices", null, wrongChoiceValues);

            wrongChoiceValues.clear();
            wrongChoiceValues.put("questionId", 1);
            wrongChoiceValues.put("wrongAnswer", "Глибовець");
            db.insert("wrongChoices", null, wrongChoiceValues);
            wrongChoiceValues.clear();






        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 1);
        simpleValues.put("text", "Які статті Закону України “Про автомобільні дороги” стосуються використання доріг не за їх призначенням?");
        simpleValues.put("answer", "Статті 36-38");
        simpleValues.put("picture", "noPicture");

       questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


         wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Усі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Ніякі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Статті 91-93");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Статті 4-5");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Статті 45-46");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Статті 52-53");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 2);
        wrongChoiceValues.put("wrongAnswer", "Статті 1-10");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();







        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 1);
        simpleValues.put("text", "Переміщення одним транспортним засобом іншого, яке не належить до експлуатації автопоїздів, це:");
        simpleValues.put("answer", "буксирування");
        simpleValues.put("picture", "noPicture");

        db.insert("simpleChoiceQuestion", null, simpleValues);

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "випередження");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "гальмування");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "засліплення");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "сесія");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();


        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "аварія");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "зупинка");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 3);
        wrongChoiceValues.put("wrongAnswer", "обгін");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();




        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 1);
        simpleValues.put("text", "Автомобіль з кількістю місць для сидіння більше дев'яти з місцем водія включно, призначений для перевезення пасажирів:");
        simpleValues.put("answer", "автобус");
        simpleValues.put("picture", "noPicture");

        db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "потяг");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "катафалк");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "мопед");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "легковик");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "грузовик");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 4);
        wrongChoiceValues.put("wrongAnswer", "велосипед");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();



        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 1);
        simpleValues.put("text", "Випередження одного або кількох транспортних засобів, пов'язане з виїздом на смугу зустрічного руху:");
        simpleValues.put("answer", "обгін");
        simpleValues.put("picture", "noPicture");

        db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", 5);
        wrongChoiceValues.put("wrongAnswer", "гальмування");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 5);
        wrongChoiceValues.put("wrongAnswer", "зупинка");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 5);
        wrongChoiceValues.put("wrongAnswer", "естакада");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 5);
        wrongChoiceValues.put("wrongAnswer", "перевага");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", 5);
        wrongChoiceValues.put("wrongAnswer", "засліплення");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();










    }




}
