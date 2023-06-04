package com.wheelie.co;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wheelie.co.Drivetopia;

import DBWorkH.DatabaseHelperH;

public class AndroidLauncher extends AndroidApplication {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Drivetopia(), config);

		// Perform database operations as needed
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
