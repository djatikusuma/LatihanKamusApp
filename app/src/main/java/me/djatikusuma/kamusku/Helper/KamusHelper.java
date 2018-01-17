package me.djatikusuma.kamusku.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import me.djatikusuma.kamusku.Model.KamusModel;

/**
 * Created by djatikusuma on 03/01/2018.
 *
 */

public class KamusHelper {

    private static String ENGLISH = DBHelper.TABLE_ENGLISH;
    private static String INDONESIA = DBHelper.TABLE_INDONESIA;

    private Context context;
    private DBHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor searchQueryByWord(String query, boolean isEng) {
        String DATABASE_TABLE = isEng ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM "
                + DATABASE_TABLE +
                " WHERE "
                + DBHelper.FIELD_WORD +
                " LIKE '%" + query.trim() + "%'", null);
    }

    public ArrayList<KamusModel> getDataByWord(String search, boolean isEng) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQueryByWord(search, isEng);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.FIELD_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_TRANSLATE)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryAllData(boolean isEng) {
        String DATABASE_TABLE = isEng ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM "
                + DATABASE_TABLE +
                " ORDER BY "
                + DBHelper.FIELD_ID + " ASC", null);
    }

    public ArrayList<KamusModel> getAllData(boolean isEng) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData(isEng);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.FIELD_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_TRANSLATE)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTransaction(ArrayList<KamusModel> kamusModels, boolean isEng) {
        String DATABASE_TABLE = isEng ? ENGLISH : INDONESIA;
        String sql = "INSERT INTO "
                + DATABASE_TABLE + " (" +
                DBHelper.FIELD_WORD + ", " +
                DBHelper.FIELD_TRANSLATE + ") VALUES (?, ?)";

        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < kamusModels.size(); i++) {
            stmt.bindString(1, kamusModels.get(i).getWord());
            stmt.bindString(2, kamusModels.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
