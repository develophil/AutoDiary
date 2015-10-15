package com.hkpking.life.autodiary.sqlite;

import android.content.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.*;

import com.hkpking.life.autodiary.common.CommonDefines;

public class ADSQLiteOpenHelper extends SQLiteOpenHelper implements CommonDefines{

	public ADSQLiteOpenHelper(Context context, String name,
							  CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.i(TAG, "onCreate ver. >>>>>>>>>>>>>> "+db.getVersion());
		
		String sql1 = "create table ad_tb_loc010 ( " +
				" _id integer primary key autoincrement , " +
				" latitude integer , " +
				" longitude integer , " +
				" address text , " +
				" creation_timestamp text )";

		db.execSQL(sql1);
	}//end onCreate()

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.i(TAG, "onUpgrade old : "+oldVersion+" >>> "+newVersion);
		String sql1 = "drop table if exists ad_tb_loc010";
		db.execSQL(sql1);
		onCreate(db);
	}// end onUpgrade()

}// end class
