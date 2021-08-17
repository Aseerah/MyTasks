package com.myapplicationdev.android.p10_ndpsongs_clv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mytasks.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_TASK = "Task";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_DEADLINE = "deadline";
	private static final String COLUMN_STARS = "difficultyLevel";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE Song
		// (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
		// singers TEXT, stars INTEGER, year INTEGER );
		String createSongTableSql = "CREATE TABLE " + TABLE_TASK + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_TITLE + " TEXT, "
				+ COLUMN_DEADLINE + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
		db.execSQL(createSongTableSql);
		Log.i("info", createSongTableSql + "\ncreated tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
		onCreate(db);
	}

	public long insertTask(String title, int year, int stars) {
		// Get an instance of the database for writing
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DEADLINE, year);
		values.put(COLUMN_STARS, stars);
		// Insert the row into the TABLE_SONG
		long result = db.insert(TABLE_TASK, null, values);
		// Close the database connection
		db.close();
        Log.d("SQL Insert","" + result);
        return result;
	}

	public ArrayList<Tasks> getAllTask() {
		ArrayList<Tasks> songslist = new ArrayList<Tasks>();
		String selectQuery = "SELECT " + COLUMN_ID + ","
				+ COLUMN_TITLE + ","
				+ COLUMN_DEADLINE + ","
				+ COLUMN_STARS + " FROM " + TABLE_TASK;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String title = cursor.getString(1);
				int year = cursor.getInt(2);
                int stars = cursor.getInt(3);

				Tasks newTasks = new Tasks(id, title, year, stars);
                songslist.add(newTasks);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return songslist;
	}

	public ArrayList<Tasks> getAllTaskByStars(int starsFilter) {
		ArrayList<Tasks> songslist = new ArrayList<Tasks>();

		SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_DEADLINE, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};



        Cursor cursor;
        cursor = db.query(TABLE_TASK, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int year = cursor.getInt(2);
                int stars = cursor.getInt(3);

                Tasks newTasks = new Tasks(id, title, year, stars);
                songslist.add(newTasks);
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return songslist;
	}

	public int updateTask(Tasks data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_DEADLINE, data.getYearReleased());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }


    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }

}
