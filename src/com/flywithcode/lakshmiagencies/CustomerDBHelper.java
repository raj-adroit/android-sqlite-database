package com.flywithcode.lakshmiagencies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerDBHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_CUSTOMERS = "customers";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME  = "name";

	public static final String DATABASE_NAME = "lakshmi_agencies.db";
	public static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_CREATE = "create table "+ TABLE_CUSTOMERS +
			"(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null);";
	
	
	public CustomerDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
		onCreate(db);
	}

}
