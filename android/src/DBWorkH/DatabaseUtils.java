package DBWorkH;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseUtils {



public DatabaseUtils(){}

    /**повертає кількість завалів фінального тесту юзером**/
    public static int getUserFailures(SQLiteDatabase database, int userId) {
        String query = "SELECT failures FROM users WHERE id = ?";
        int failures = 0;

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            failures = cursor.getInt(cursor.getColumnIndexOrThrow("failures"));
        }

        if (cursor != null) {
            cursor.close();
        }

        return failures;
    }

    public static int[] getLevelsWithStateZero(SQLiteDatabase database, int userId) {
        String query = "SELECT levelNumb FROM scores WHERE userId = ? AND state = ? AND levelNumb != ?";
        String[] args = {String.valueOf(userId), "0", "16"};
        Cursor cursor = database.rawQuery(query, args);

        int[] levelNumbers;

        if (cursor.moveToFirst()) {
            levelNumbers = new int[cursor.getCount()];
            int index = 0;
            do {
                levelNumbers[index] = cursor.getInt(cursor.getColumnIndexOrThrow("levelNumb"));
                index++;
            } while (cursor.moveToNext());
        } else {
            levelNumbers = new int[0]; // No levels found with state 0
        }

        cursor.close();
        return levelNumbers;
    }

    /**метод визначає, який кубок відображати при успішному завершенні фінального тесту**/
    public static String getCup(SQLiteDatabase database, int userId, boolean finTestMax) {
        boolean practiceMax = true;
        int[] practices = getLevelsWithStateZero(database,userId);
        for(int i = 0;i<practices.length;i++) {
            if(practices[i]!=2) practiceMax = false;
        }
        if(practiceMax && finTestMax && getUserFailures(database,userId)==0) {
            return "gold2.png";
        }
        else if(practiceMax && finTestMax) {
            return "gold.png";
        }
        else if(practiceMax || finTestMax) {
            return "silver.png";
        }
        else return "bronze.png";

    }


    /**повертає, чи пройдено вже фінальний тест юзером; passValue=0 ні, passValue=1 - так**/
    public static int getUserPassValue(SQLiteDatabase database, int userId) {
        String query = "SELECT pass FROM users WHERE id = ?";
        int passValue = 0;

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            passValue = cursor.getInt(cursor.getColumnIndexOrThrow("pass"));
        }

        if (cursor != null) {
            cursor.close();
        }

        return passValue;
    }


/**Повертає стан юзера стосовного певного рівню. 0 - не пройдено/завалено; 1 - пройдено не ідеально
 * 2 - пройдено ідеально**/
    public static int getUserStateForLevel(SQLiteDatabase database, int userId, int levelNumb) {
        String query = "SELECT state FROM scores WHERE userId = ? AND levelNumb = ?";
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(levelNumb)};
        int state = 0;

        Cursor cursor = database.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            int stateIndex = cursor.getColumnIndex("state");
            if (stateIndex != -1) {
                state = cursor.getInt(stateIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return state;
    }

/**встановлює значення щодо проходження фінального тесту (1 - пройшов)**/
    public static void setUserPassed(SQLiteDatabase database, int userId, int passedValue) {
        String query = "UPDATE users SET pass = ? WHERE id = ?";
        Object[] args = {passedValue, userId};

        database.execSQL(query, args);
        Log.d("User " + userId, "pass set to " + passedValue);
    }


}
