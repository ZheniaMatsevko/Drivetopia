package com.wheelie.co;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wheelie.co.Drivetopia;

import DBWorkH.DatabaseHelperH;

public class AndroidLauncher extends AndroidApplication {

	private SQLiteDatabase database;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DatabaseHelperH dbHelper = DatabaseHelperH.getInstance(this);
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		
		//перші два юзери в БД - тестові, і беруться з insertInitialData
		//тут вони видаляються щоб можна було редагувати інфу про них прямо в
		//DatabaseHelperH і вона одразу оновлювалась
		database.delete("users", "id IN (?, ?)", new String[]{"1", "2"});
		database.delete("userInfo", "id IN (?, ?)", new String[]{"1", "2"});
        dbHelper.insertInitialData(database);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Drivetopia(database), config);

		// Perform database operations as needed
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
