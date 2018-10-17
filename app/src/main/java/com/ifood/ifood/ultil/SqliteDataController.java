package com.ifood.ifood.ultil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SqliteDataController extends SQLiteOpenHelper {
    public String DB_PATH = "//data//data//%s//databases//";
    // đường dẫn nơi chứa database
    public static String DB_NAME = "I_FOOD_Database";
    public SQLiteDatabase database;
    private final Context mContext;

    public SqliteDataController(Context con) {
        super(con, DB_NAME, null, 3);
        DB_PATH = String.format(DB_PATH, con.getPackageName());
        this.mContext = con;
    }

    public boolean isCreatedDatabase() throws IOException {
        // Default là đã có DB
        boolean result = true;
        // Nếu chưa tồn tại DB thì copy từ Asses vào Data
        if (!checkExistDataBase()) {
            this.getWritableDatabase();
            try {
                //copyDataBase();
                result = false;
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }

        return result;
    }

    /**
     * check whether database exist on the device?
     *
     * @return true if existed
     */
    private boolean checkExistDataBase() {

        try {
            String myPath = DB_PATH + DB_NAME;
            File fileDB = new File(myPath);

            if (fileDB.exists()) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * copy database from assets folder to the device
     *
     * @throws IOException
     */
    /*private void copyDataBase() throws IOException{
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }*/

    /**
     * delete database file
     *
     * @return
     */
    public boolean deleteDatabase() {
        File file = new File(DB_PATH + DB_NAME);
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * open database
     *
     * @throws //SQLException
     */
    public void openDataBase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    public boolean insertDataIntoTable (String tableName, Object object) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            Class objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                if (field.isSynthetic() || field.getName().equals("serialVersionUID")){
                    continue;
                }
                String fieldName = field.getName();
                fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1, fieldName.length());
                Method getValueMethod = objectClass.getMethod("get" + fieldName, null);
                values.put(field.getName(), getValueMethod.invoke(object).toString());
            }

            long rs = database.insert(tableName, null, values);
            if (rs > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public boolean updateDataIntoTable (String tableName, Object object) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            Class objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                if (field.isSynthetic() || field.getName().equals("serialVersionUID")){
                    continue;
                }
                String fieldName = field.getName();
                fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1, fieldName.length());
                Method getValueMethod = objectClass.getMethod("get" + fieldName, null);
                values.put(field.getName(), getValueMethod.invoke(object).toString());
            }

            long rs = database.update(tableName, values, "Email = ?", new String[] {values.get("email").toString()});
            if (rs > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public int deleteData_From_Table(String tbName, String whereClause) {

        int result = 0;
        try {
            openDataBase();
            database.beginTransaction();
            result = database.delete(tbName, whereClause, null);
            if (result >= 0) {
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            database.endTransaction();
            close();
        } finally {
            database.endTransaction();
            close();
        }

        return result;
    }

    public void checkTableExistInDatabase(String tableName) {
        try {
            isCreatedDatabase();
            openDataBase();
            Cursor cursor = database.rawQuery("SELECT 1 FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
            String fileName = "";
            if (cursor.moveToFirst() == false)
            {
                fileName = tableName + ".sql";
                InputStream inputStream = mContext.getAssets().open(fileName);
                BufferedReader insertReader = new BufferedReader(new InputStreamReader(inputStream));
                String sql = "";
                while (insertReader.ready()){
                    sql += insertReader.readLine() + " ";
                }

                database.execSQL(sql);
                insertReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
