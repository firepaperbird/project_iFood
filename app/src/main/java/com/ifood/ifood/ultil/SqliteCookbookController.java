package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Model_User;

import java.util.ArrayList;
import java.util.List;

public class SqliteCookbookController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 1;
    private final int COLUMN_DISH_ID_INDEX = 2;
    private final int COLUMN_DISH_NAME_INDEX = 3;
    private final int COLUMN_USER_ID_INDEX = 4;

    private final String TABLE_NAME = "CookBook";

    public SqliteCookbookController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<String> getCookbookByUserId(String userId) {
        List<String> listDishes = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "userId = ?", new String[]{userId}, null, null, null, null);
            while (cs.moveToNext()) {
                listDishes.add(cs.getString(COLUMN_DISH_ID_INDEX));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listDishes;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}
