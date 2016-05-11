package esposito.nicholas.recyclingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicholasesposito on 09/05/2016.
 */
public class LocalDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LocalDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_SCORE = "userScore";
    private static final String USER = "player";
    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_USER_ID + " STRING," + KEY_USER_SCORE + " INTEGER"+ ")";

    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void UpdateHighScore(Integer score){

        SQLiteDatabase db = getWritableDatabase();
        String query="SELECT "+KEY_USER_ID+" FROM " + TABLE_USER + " WHERE "+ KEY_USER_ID +" = '"+USER+"'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() == 0)
        {
            db.execSQL("INSERT INTO " + TABLE_USER + " (" + KEY_USER_ID + ") " + "VALUES ('" + USER + "');");
            db.execSQL("INSERT INTO " + TABLE_USER + " (" + KEY_USER_SCORE + ") " + "VALUES ('" + 0 + "');");
        }
            db.execSQL(" UPDATE " + TABLE_USER + " SET " + KEY_USER_SCORE + " = '" + score + "' WHERE " + KEY_USER_ID + " = '" + USER + "';");
    }

    public  Integer  getHighScore()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT " + KEY_USER_SCORE + " FROM " + TABLE_USER + " WHERE " + KEY_USER_ID + " = '" + USER + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
            return null;
            else
            return cursor.getInt(0);
    }

    public void reSet() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(db);
    }
}
