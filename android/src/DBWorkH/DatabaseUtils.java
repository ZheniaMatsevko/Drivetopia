package DBWorkH;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

}
