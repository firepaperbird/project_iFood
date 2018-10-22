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
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_NAME_INDEX = 1;
    private final int COLUMN_EMAIL_INDEX = 2;
    private final int COLUMN_PASSWORD_INDEX = 3;
    private final int COLUMN_ADDRESS_INDEX = 4;
    private final int COLUMN_DESCRIPTION_INDEX = 5;

    private final String TABLE_NAME = "User";


    public SqliteUserController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public Model_User getUserByEmailAndPassword(String email, String password) {
        Model_User user = null;
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "Email = ? AND Password = ?", new String[]{email, password}, null, null, null, null);
            if (cs.moveToFirst()) {
                user = new Model_User();
                user.setId(Integer.parseInt(cs.getString(COLUMN_ID_INDEX)));
                user.setUsername(cs.getString(COLUMN_NAME_INDEX));
                user.setEmail(cs.getString(COLUMN_EMAIL_INDEX));
                user.setPassword(cs.getString(COLUMN_PASSWORD_INDEX));
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

    public Model_User getUserByEmail(String email) {
        Model_User user = null;
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "Email = ?", new String[]{email}, null, null, null, null);
            if (cs.moveToFirst()) {
                user = new Model_User();
                user.setId(Integer.parseInt(cs.getString(COLUMN_ID_INDEX)));
                user.setUsername(cs.getString(COLUMN_NAME_INDEX));
                user.setEmail(cs.getString(COLUMN_EMAIL_INDEX));
                user.setPassword(cs.getString(COLUMN_PASSWORD_INDEX));
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

    public String getTableName(){
        return TABLE_NAME;
    }

}
