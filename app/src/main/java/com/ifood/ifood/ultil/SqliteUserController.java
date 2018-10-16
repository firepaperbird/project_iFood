package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;

import com.ifood.ifood.data.Model_User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserController extends SqliteDataController {
    private final int COLUMN_NAME_INDEX = 1;
    private final int COLUMN_EMAIL_INDEX = 2;
    private final int COLUMN_ADDRESS_INDEX = 4;
    private final int COLUMN_DESCRIPTION_INDEX = 5;

    private final Context context;

    public SqliteUserController(Context con) {
        super(con);
        context = con;
        checkTableExistInDatabase();
    }

    public Model_User getUserById(String id) {
        Model_User user = null;
        try {
            openDataBase();
            Cursor cs = database.rawQuery("select * from User where id=" + id, null);
            if (cs.moveToNext()) {
                user = new Model_User();
                user.setUsername(cs.getString(COLUMN_NAME_INDEX));
                user.setEmail(cs.getString(COLUMN_EMAIL_INDEX));
                user.setAddress(cs.getString(COLUMN_ADDRESS_INDEX));
                user.setDescription(cs.getString(COLUMN_DESCRIPTION_INDEX));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return user;
    }

    public void checkTableExistInDatabase() {
        try {
            boolean dbExist = isCreatedDatabase();
            openDataBase();
            Cursor cursor = database.rawQuery("SELECT 1 FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", "User"});
            String fileName = "";
            if (cursor.moveToFirst() == false)
            {
                fileName = "User.sql";
                InputStream inputStream = context.getAssets().open(fileName);
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
}
