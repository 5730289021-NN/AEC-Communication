package com.oaksmuth.aeccommunication.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * Created by noraw on 10/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String ENCODING = "UTF-8";
    private static final String DB_NAME = "AECX.db";
    private static String targetPath = null;

    private SQLiteDatabase db;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        targetPath = context.getDatabasePath(DB_NAME).getPath() + DB_NAME;
    }

    private boolean isDatabaseAvailable() {
        try{
            db = SQLiteDatabase.openDatabase(targetPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e)
        {
            return false;
        }
        db.close();
        return true;
    }

    private void transportDatabase() throws IOException {
        File file = new File(targetPath);
        if(!file.exists()) {
            file.mkdirs();
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            InputStream in = context.getAssets().open(DB_NAME);
            OutputStream out = new FileOutputStream(targetPath);
            byte[] buffer = new byte[1024];
            int length;
            while((length = in.read(buffer))>0){
                out.write(buffer, 0, length);
            }
            out.flush();
            out.close();
            in.close();
        }
    }

    public void openDatabase() throws SQLException, IOException {
        if(isDatabaseAvailable()) {
            db = SQLiteDatabase.openDatabase(targetPath, null, SQLiteDatabase.OPEN_READONLY);
            db.execSQL("PRAGMA encoding = \"UTF-8\"");
        }
        else {
            transportDatabase();
            openDatabase();
        }
    }

    public Cursor rawQuery(String sql, String[] args)
    {
        return db.rawQuery(sql,args);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void close()
    {
        if(db != null) db.close();
        super.close();
    }

}
