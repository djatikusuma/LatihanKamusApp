package me.djatikusuma.kamusku.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by djatikusuma on 03/01/2018.
 *
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "kamusku_app.db";

    public static String TABLE_ENGLISH = "tb_english";
    public static String TABLE_INDONESIA = "tb_indonesia";

    public static String FIELD_ID = "id";
    public static String FIELD_WORD = "word";
    public static String FIELD_TRANSLATE = "translate";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TB_ENGLISH = "create table " + TABLE_ENGLISH + " (" +
            FIELD_ID + " integer primary key autoincrement, " +
            FIELD_WORD + " text not null, " +
            FIELD_TRANSLATE + " text not null);";

    public static String CREATE_TB_INDONESIA = "create table " + TABLE_INDONESIA + " (" +
            FIELD_ID + " integer primary key autoincrement, " +
            FIELD_WORD + " text not null, " +
            FIELD_TRANSLATE + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_ENGLISH);
        db.execSQL(CREATE_TB_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDONESIA);
        onCreate(db);
    }
}
