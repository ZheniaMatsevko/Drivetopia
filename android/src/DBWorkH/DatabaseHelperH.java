package DBWorkH;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                "state INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE" +
                ")";

        db.execSQL(createScoresTable);




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

        // Create "normalTextInputQuestion" table
        String createNormalTextInputQuestion = "CREATE TABLE IF NOT EXISTS normalTextInputQuestion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "text TEXT NOT NULL, " +
                "answer TEXT NOT NULL " +
                ")";
        db.execSQL(createNormalTextInputQuestion);


        // Create "wrongChoices" table
        //таблиця для збереження інформації про неправильні відповіді у тестах
        String createWrongChoices = "CREATE TABLE IF NOT EXISTS wrongChoices (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "wrongAnswer TEXT NOT NULL, " +
                "questionId INTEGER NOT NULL, " +
                "FOREIGN KEY (questionId) REFERENCES simpleChoiceQuestion(id) ON DELETE CASCADE" +
                ")";

        db.execSQL(createWrongChoices);

        String createFlashCardQuestion = "CREATE TABLE IF NOT EXISTS normalFlashCardQuestion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "text TEXT NOT NULL, " +
                "answer TEXT NOT NULL, " +
                "type TEXT NOT NULL " +
                ")";
        db.execSQL(createFlashCardQuestion);


        // Create "wrongChoices" table
        //таблиця для збереження інформації про неправильні відповіді у тестах
        String createImagesForFlashCards = "CREATE TABLE IF NOT EXISTS flashCardImages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "image TEXT NOT NULL, " +
                "type TEXT NOT NULL " +
                ")";

        db.execSQL(createImagesForFlashCards);

        String createNormalRelationsQuestion = "CREATE TABLE IF NOT EXISTS normalRelationsQuestion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "text TEXT NOT NULL, " +
                "answer TEXT NOT NULL, " +
                "type TEXT NOT NULL " + //image or text
                ")";
        db.execSQL(createNormalRelationsQuestion);

        String createTheoryTable = "CREATE TABLE IF NOT EXISTS theory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "levelNumb INTEGER NOT NULL, " +
                "text TEXT NOT NULL, " +
                "image TEXT NOT NULL " +
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
        values2.put("failures", 0);
        values2.put("pass", 0);

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
            for (int levelNumb = 1; levelNumb <= 16; levelNumb++) {
                ContentValues valuess = new ContentValues();
                valuess.put("userId", userId);
                valuess.put("levelNumb", levelNumb);
                valuess.put("score", 0);

                if(userId==1 || levelNumb==16)valuess.put("state", 0);
                else valuess.put("state", 1);

                long rowId = db.insert("scores", null, valuess);

                if (rowId == -1) {
                    Log.d("scores insert failure","-1");// Failed to insert the row, handle the error if needed
                }
            }
        }


        /**
         * Заповнюємо теорію
         */
        ContentValues theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 1);
        theoryValues.put("text", "theory1.txt");
        theoryValues.put("image", "theory1.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 1);
        theoryValues.put("text", "theory1-2.txt");
        theoryValues.put("image", "theory1-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 1);
        theoryValues.put("text", "theory1-3.txt");
        theoryValues.put("image", "theory1-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2.txt");
        theoryValues.put("image", "theory2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-2.txt");
        theoryValues.put("image", "theory2-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-3.txt");
        theoryValues.put("image", "theory2-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-4.txt");
        theoryValues.put("image", "theory2-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-5.txt");
        theoryValues.put("image", "theory2-5.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-6.txt");
        theoryValues.put("image", "theory2-6.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 2);
        theoryValues.put("text", "theory2-7.txt");
        theoryValues.put("image", "theory2-7.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 3);
        theoryValues.put("text", "theory3.txt");
        theoryValues.put("image", "theory3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 3);
        theoryValues.put("text", "theory3-2.txt");
        theoryValues.put("image", "theory3-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 3);
        theoryValues.put("text", "theory3-3.txt");
        theoryValues.put("image", "theory3-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4.txt");
        theoryValues.put("image", "theory4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-2.txt");
        theoryValues.put("image", "theory4-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-3.txt");
        theoryValues.put("image", "theory4-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-4.txt");
        theoryValues.put("image", "theory4-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-5.txt");
        theoryValues.put("image", "theory4-5.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-6.txt");
        theoryValues.put("image", "theory4-6.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-7.txt");
        theoryValues.put("image", "theory4-7.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-8.txt");
        theoryValues.put("image", "theory4-8.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-9.txt");
        theoryValues.put("image", "theory4-9.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-10.txt");
        theoryValues.put("image", "theory4-10.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-11.txt");
        theoryValues.put("image", "theory4-11.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 4);
        theoryValues.put("text", "theory4-12.txt");
        theoryValues.put("image", "theory4-12.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 5);
        theoryValues.put("text", "theory5.txt");
        theoryValues.put("image", "theory5.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 5);
        theoryValues.put("text", "theory5-2.txt");
        theoryValues.put("image", "theory5-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 5);
        theoryValues.put("text", "theory5-3.txt");
        theoryValues.put("image", "theory5-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 5);
        theoryValues.put("text", "theory5-4.txt");
        theoryValues.put("image", "theory5-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6.txt");
        theoryValues.put("image", "theory6.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6-2.txt");
        theoryValues.put("image", "theory6-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6-3.txt");
        theoryValues.put("image", "theory6-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6-4.txt");
        theoryValues.put("image", "theory6-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6-5.txt");
        theoryValues.put("image", "theory6-5.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 6);
        theoryValues.put("text", "theory6-6.txt");
        theoryValues.put("image", "theory6-6.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 7);
        theoryValues.put("text", "theory7.txt");
        theoryValues.put("image", "theory7.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 7);
        theoryValues.put("text", "theory7-1.txt");
        theoryValues.put("image", "theory7-1.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 7);
        theoryValues.put("text", "theory7-2.txt");
        theoryValues.put("image", "theory7-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 7);
        theoryValues.put("text", "theory7-3.txt");
        theoryValues.put("image", "theory7-3.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 7);
        theoryValues.put("text", "theory7-4.txt");
        theoryValues.put("image", "theory7-4.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 8);
        theoryValues.put("text", "theory8.txt");
        theoryValues.put("image", "theory8.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 8);
        theoryValues.put("text", "theory8-1.txt");
        theoryValues.put("image", "theory8-1.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 8);
        theoryValues.put("text", "theory8-2.txt");
        theoryValues.put("image", "theory8-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 9);
        theoryValues.put("text", "theory9.txt");
        theoryValues.put("image", "theory9.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 9);
        theoryValues.put("text", "theory9-1.txt");
        theoryValues.put("image", "theory9-1.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 10);
        theoryValues.put("text", "theory10.txt");
        theoryValues.put("image", "theory10.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 10);
        theoryValues.put("text", "theory10-1.txt");
        theoryValues.put("image", "theory10-1.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        theoryValues = new ContentValues();
        theoryValues.put("levelNumb", 10);
        theoryValues.put("text", "theory10-2.txt");
        theoryValues.put("image", "theory10-2.jpg");

        db.insert(DBConstants.THEORY_TABLE, null, theoryValues);

        /**Заповнюємо інформацію про рівні**/

        ContentValues flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Пішохідний перехід'?");
        flashCardValues.put("answer", "sign15.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Кінець велосипедної смуги'?");
        flashCardValues.put("answer", "sign1.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Кінець пішохідної зони'?");
        flashCardValues.put("answer", "sign14.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Смуга руху для аварійної зупинки'?");
        flashCardValues.put("answer", "sign13.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Дорога з одностороннім рухом'?");
        flashCardValues.put("answer", "sign11.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 14);
        flashCardValues.put("text", "На якій картинці зображений знак 'Дорога із зустрічною велосипедною смугою'?");
        flashCardValues.put("answer", "sign10.png");
        flashCardValues.put("type", "sign");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);

        flashCardValues = new ContentValues();
        flashCardValues.put("levelNumb", 6);
        flashCardValues.put("text", "На якій картинці розворот дозволено?");
        flashCardValues.put("answer", "turnOK.jpg");
        flashCardValues.put("type", "turn");

        db.insert(DBConstants.FLASHCARD_QUESTION_TABLE, null, flashCardValues);


        for(int i=1;i<16;i++){
            ContentValues flashCardImageValues = new ContentValues();
            flashCardImageValues.put("image", "sign"+i+".png");
            flashCardImageValues.put("type", "sign");
            db.insert(DBConstants.FLASHCARD_IMAGES_TABLE, null, flashCardImageValues);
        }
        for(int i=1;i<5;i++){
            ContentValues flashCardImageValues = new ContentValues();
            flashCardImageValues.put("image", "turn"+i+".jpg");
            flashCardImageValues.put("type", "turn");
            db.insert(DBConstants.FLASHCARD_IMAGES_TABLE, null, flashCardImageValues);
        }











        /**Питання на відповідність**/



        ContentValues relationsValues = new ContentValues();


        String[] texts = new String[]{"Кінець велосипедної смуги","Місце для стоянки", "Виїзд на дорогу із смугою для руху маршрутних транспортних засобів", "Кінець смуги для руху маршрутних транспортних засобів", "Кінець додаткової смуги руху",
        "Місце для розвороту","Пішохідна зона","Підземний пішохідний перехід",
                "Велосипедна смуга","Дорога із зустрічною велосипедною смугою",
        "Дорога з одностороннім рухом", "Кінець дороги із смугою для руху маршрутних транспортних засобів",
        "Смуга руху для аварійної зупинки", "Кінець пішохідної зони","Пішохідний перехід"};
        for(int i=1;i<16;i++){
            relationsValues = new ContentValues();
            relationsValues.put("levelNumb", 14);
            relationsValues.put("text", texts[i-1]);
            relationsValues.put("answer", "sign"+i+".png");
            relationsValues.put("type", "image");
            db.insert(DBConstants.RELATIONS_TABLE, null, relationsValues);
        }

        texts = new String[]{"попереджувальні знаки", "знаки пріоритету",
        "заборонні знаки", "наказові знаки", "інформаційно-вказівні знаки",
                "знаки сервісу", "таблички до дорожніх знаків"};
        String[] answers = new String[]{"інформують водіїв про наближення до небезпечної ділянки дороги і характер небезпеки.",
        "встановлюють черговість проїзду перехресть, перехрещень проїзних частин або вузьких ділянок дороги;",
        "запроваджують/скасовують певні обмеження в русі;",
        "показують обов’язкові напрямки руху або дозволяють деяким категоріям учасників рух по проїзній частині чи окремих її ділянках, а також запроваджують/скасовують деякі обмеження;",
        "запроваджують/скасовують певний режим руху, а також інформують учасників дорожнього руху про розташування населених пунктів, різних об’єктів, територій, де діють спеціальні правила;",
        "інформують учасників дорожнього руху про розташування об’єктів обслуговування;",
        "уточнюють або обмежують дію знаків, разом з якими вони встановлені."};
        for(int i=0;i<texts.length;i++){
            relationsValues = new ContentValues();
            relationsValues.put("levelNumb", 4);
            relationsValues.put("text", texts[i]);
            relationsValues.put("answer", answers[i]);
            relationsValues.put("type", "text");
            db.insert(DBConstants.RELATIONS_TABLE, null, relationsValues);
        }

        texts = new String[]{"синій маячок", "оранжевий маячок","ближнє світло фар", "зелений маячок"};
        answers = new String[]{"надає перевагу в русі і забезпечує безперешкодний проїзд собі і супроводжувальному транспорту.",
                "не дає переваги в русі, а служить для привернення уваги та попередження про небезпеку.",
                "має бути ввімкнено на транспортних засобах, які рухаються в супроводжуваній колоні.",
                "після його проїзду скасовуються обмеження на рух інших транспортних засобів."};
        for(int i=0;i<texts.length;i++){
            relationsValues = new ContentValues();
            relationsValues.put("levelNumb", 3);
            relationsValues.put("text", texts[i]);
            relationsValues.put("answer", answers[i]);
            relationsValues.put("type", "text");
            db.insert(DBConstants.RELATIONS_TABLE, null, relationsValues);
        }









        /**Питання на введення відповіді (5 балів)**/

        /**рівень 1**/
        ContentValues normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 1);
        normalInputValues.put("text", "Будівництво спортивних споруд, автозаправних станцій та інших об'єктів на дорогах можливе лише з дозволу державних органів та погодженням з ...");
        normalInputValues.put("answer", "поліцією");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 1);
        normalInputValues.put("text", "Чи потрібен дозвіл від центрального органу влади з дорожнього господарства для проведення спортивних змагань на недержавних дорогах?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 1);
        normalInputValues.put("text", "Хто вимагає оплату за видання дозволу на будівництво об’єктів коло доріг?");
        normalInputValues.put("answer", "Орган дорожнього управління");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 1);
        normalInputValues.put("text", "Як називаються статті 36-38 Закону України, в яких прописані вимоги використання доріг не за їх призначенням? (без лапок)");
        normalInputValues.put("answer", "Про автомобільні дороги");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        /**рівень 2**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 2);
        normalInputValues.put("text", "Чи має пасажир право на отримання інформації про порядок руху?");
        normalInputValues.put("answer", "так");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 2);
        normalInputValues.put("text", "Водієві забороняється керувати авто після прийому ліків, що...");
        normalInputValues.put("answer", "знижують увагу");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 2);
        normalInputValues.put("text", "Для забезпечення безпеки дорожнього руху водій зобов’язаний перед виїздом перевірити і забезпечити технічно справний стан і _____________ транспортного засобу");
        normalInputValues.put("answer", "комплектність");

        db.insert("normalTextInputQuestion", null, normalInputValues);



        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 2);
        normalInputValues.put("text", "Посадку дозволяється здійснювати пасажирам після зупинки транспортного засобу лише з...");
        normalInputValues.put("answer", "посадкового майданчику");

        db.insert("normalTextInputQuestion", null, normalInputValues);



        /**рівень 3**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 3);
        normalInputValues.put("text", "Чи можна обганяти транспорт з увімкненим звуковим сигналом?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 3);
        normalInputValues.put("text", "Що повинен зробити водій, наближаючись до нерухомого транспорту з увімкненим маячком синього кольору?");
        normalInputValues.put("answer", "знизити швидкість");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 3);
        normalInputValues.put("text", "Що повинні зробити водії, коли наближається транспорт зі спеціальним звуковим сигналом?");
        normalInputValues.put("answer", "дати дорогу");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 3);
        normalInputValues.put("text", "Наближаючись до нерухомого транспорту з увімкненим проблисковим маячком синього кольору, водій повинен знизити швидкість до... (у км/год)");
        normalInputValues.put("answer", "40");

        db.insert("normalTextInputQuestion", null, normalInputValues);



        /**рівень 4**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 4);
        normalInputValues.put("text", "Для зорового орієнтування використовується ___________ дорожна розмітка.");
        normalInputValues.put("answer", "вертикальна");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 4);
        normalInputValues.put("text", "Встановлюють черговість проїзду перехресть, перехрещень проїзних частин або вузьких ділянок дороги...");
        normalInputValues.put("answer", "знаки пріорітету");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 4);
        normalInputValues.put("text", "Знак тимчасовий, якщо він на _______ фоні.");
        normalInputValues.put("answer", "жовтому");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 4);
        normalInputValues.put("text", "Як допоміжний засіб регулювання дорожнього руху використовується...");
        normalInputValues.put("answer", "дорожнє обладнання");

        db.insert("normalTextInputQuestion", null, normalInputValues);




        /**рівень 5**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 5);
        normalInputValues.put("text", "Чи надає водієві переваги попереджувальний сигнал?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 5);
        normalInputValues.put("text", "Що забороняється використовувати, як попереджувальний сигнал, в умовах, коли це може призвести до засліплення інших водіїв?");
        normalInputValues.put("answer", "дальнє світло фар");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 5);
        normalInputValues.put("text", "Який попереджувальний сигнал слід ввімкнути у разі зупинки на вимогу поліцейького?");
        normalInputValues.put("answer", "аварійну світлову сигналізацію");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 5);
        normalInputValues.put("text", "Який попереджувальний сигнал слід ввімкнути у разі вимушеної зупинки на дорозі?");
        normalInputValues.put("answer", "аварійну світлову сигналізацію");

        db.insert("normalTextInputQuestion", null, normalInputValues);



        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 5);
        normalInputValues.put("text", "Чи вважаються за попереджувальні сигнали, які подаються рукою?");
        normalInputValues.put("answer", "так");

        db.insert("normalTextInputQuestion", null, normalInputValues);




        /**рівень 6**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 6);
        normalInputValues.put("text", "За одночасного перестроювання транспортних засобів в одному напрямку дорогу має дати водій, що рухається...");
        normalInputValues.put("answer", "ліворуч");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 6);
        normalInputValues.put("text", "Чи дозволяється розворот на залізничному переїзді?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 6);
        normalInputValues.put("text", "Чи дозволяється розворот у тунелі?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 6);
        normalInputValues.put("text", "Розворот забороняється за видимості менше... (у м)");
        normalInputValues.put("answer", "100");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 6);
        normalInputValues.put("text", "Чи має водій давати дорогу іншим, виїжджаючи з житлової зони?");
        normalInputValues.put("answer", "так");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        /**рівень 7**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 7);
        normalInputValues.put("text", "Чи дозволяється виїжджати на трамвайну колію зустрічного напрямку?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 7);
        normalInputValues.put("text", "Транспортні засоби, швидкість руху яких не повинна перевищувати 40 км/год, повинні рухатися якнайближче до _______ краю проїзної частини.");
        normalInputValues.put("answer", "правого");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 7);
        normalInputValues.put("text", "Чи можна займати дві смуги одночасно під час руху?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 7);
        normalInputValues.put("text", "Наїжджати на переривчасті лінії розмітки дозволяється лише під час...");
        normalInputValues.put("answer", "перестроювання");

        db.insert("normalTextInputQuestion", null, normalInputValues);


       /**рівень 8**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 8);
        normalInputValues.put("text", "Зі швидкістю не більше 50 км/год дозволяється рух у...");
        normalInputValues.put("answer", "населених пунктах");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 8);
        normalInputValues.put("text", "Під час буксирування швидкість не повинна перевищувати... (у км/год)");
        normalInputValues.put("answer", "50");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 8);
        normalInputValues.put("text", "Поза населеними пунктами швидкість автобусів не повинна перевищувати 90 км/год за винятком...");
        normalInputValues.put("answer", "мікроавтобусів");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 8);
        normalInputValues.put("text", "Чи дозволяється різко гальмувати, якщо без цього не запобігти ДТП?");
        normalInputValues.put("answer", "так");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 8);
        normalInputValues.put("text", "Чи дозволяється без потреби рухатися з дуже малою швидкістю?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);


        /**рівень 9**/
        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 9);
        normalInputValues.put("text", "На дорогах, позначених знаками Крутий підйом або Крутий спуск, за наявності перешкоди дати дорогу повинен водій, що рухається...");
        normalInputValues.put("answer", "на спуск");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 9);
        normalInputValues.put("text", "Відстань до транспортного засобу, що рухається попереду:");
        normalInputValues.put("answer", "дистанція");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 9);
        normalInputValues.put("text", "Відстань до транспортного засобу, що рухається по суміжній смузі:");
        normalInputValues.put("answer", "інтервал");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        /**рівень 10**/





        /**рівень 12**/

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 12);
        normalInputValues.put("text", "Хто має перевагу на пішохідному переході?");
        normalInputValues.put("answer", "пішоходи");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 12);
        normalInputValues.put("text", "Чи можна переходити дорогу на пішохідному переході, якщо світлофор для пішоходів червоний?");
        normalInputValues.put("answer", "ні");

        db.insert("normalTextInputQuestion", null, normalInputValues);

        normalInputValues = new ContentValues();
        normalInputValues.put("levelNumb", 12);
        normalInputValues.put("text", "Чи потрібно зупинятися, якщо пішоходи перетинають дорогу поза пішохідним переходом?");
        normalInputValues.put("answer", "так");

        db.insert("normalTextInputQuestion", null, normalInputValues);

















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






        /**рівень 2**/
         simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 2);
        simpleValues.put("text", "Що заборонено пасажирам під час руху у транспортному засобі?");
        simpleValues.put("answer", "відчиняти двері");
        simpleValues.put("picture", "noPicture");

         questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "стояти");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "сидіти");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "їсти");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "пити");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "розмовляти з водієм");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "відчиняти вікна");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "тримати багаж");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "пристібатися");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();




        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 2);
        simpleValues.put("text", "До якої категорії транспорту належить авто з причепом?");
        simpleValues.put("answer", "СЕ");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "В1");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "Т");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "А1");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "А2");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();


        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "В2");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();




        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 2);
        simpleValues.put("text", "Для забезпечення безпеки дорожнього руху водій НЕ зобов’язаний:");
        simpleValues.put("answer", "мати відеорегістратор");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "бути уважним");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "пристібатися");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "стежити за дорогою");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "стежити за вантажем");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "мати посвідчення");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "не відволікатися");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();




        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 2);
        simpleValues.put("text", "Право на керування мототранспортними засобами і мотоколясками може бути надано з:");
        simpleValues.put("answer", "16 років");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "18 років");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "19 років");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "21 року");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "14 років");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "25 років");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

/*
Яким категоріям належить певний транспорт?
	Т — трамвай
	В1 — квадроцикл
	D1 — автобус
	СЕ — авто з причепом

	відповідність
*/


/**рівень 3**/
        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 3);
        simpleValues.put("text", "Дозволяється здійснювати обгін транспорту з увімкненими проблисковими маячками цього кольору:");
        simpleValues.put("answer", "оранжевий");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "синій");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "синій і зелений");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "червоний");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "синій і червоний");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "зелений");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();


/**рівень 4**/
        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 4);
        simpleValues.put("text", "Миготливий біло-місячний сигнал на залізничному переїзді показує, що...");
        simpleValues.put("answer", "сигналізація справна");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "рух заборонено");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "потяг наближається");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "сигналізація несправна");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "треба зачекати");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();



        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 4);
        simpleValues.put("text", "Що із списку має найбільшу перевагу?");
        simpleValues.put("answer", "тимчасові знаки");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "дорожня розмітка");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "наказові знаки");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "знаки сервісу");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "заборонні знаки");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "вказівні знаки");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();





        /**рівень 5**/

        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 5);
        simpleValues.put("text", "Розворот дозволяється...");
        simpleValues.put("answer", "на перехресті");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "під мостом");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();









        /**рівень 6**/

        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 6);
        simpleValues.put("text", "Розворот дозволяється...");
        simpleValues.put("answer", "на перехресті");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "під мостом");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на естакаді");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на мосту");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "під шляхопроводом");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "у тунелі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на пішохідних переходах");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "нема вірної відповіді");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();



        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 6);
        simpleValues.put("text", "Рух заднім ходом дозволяється...");
        simpleValues.put("answer", "нема вірної відповіді");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на автомагістралі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на мосту");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на естакаді");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на в'їзді в тунель");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "в тунелі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "на перехресті");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();






















        /**рівень 12**/
        simpleValues = new ContentValues();
        simpleValues.put("levelNumb", 12);
        simpleValues.put("text", "Якщо неможливо визначити наявність покриття на дорозі (темна пора доби, бруд, сніг тощо), а знаки пріоритету відсутні," +
                " водій повинен вважати, що перебуває на...");
        simpleValues.put("answer", "другорядній дорозі");
        simpleValues.put("picture", "noPicture");

        questionId = db.insert("simpleChoiceQuestion", null, simpleValues);


        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "головній дорозі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "перехресті");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "додатковій секції");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "лівій смузі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

        wrongChoiceValues = new ContentValues();
        wrongChoiceValues.put("questionId", questionId);
        wrongChoiceValues.put("wrongAnswer", "правій смузі");
        db.insert("wrongChoices", null, wrongChoiceValues);
        wrongChoiceValues.clear();

    }




}
