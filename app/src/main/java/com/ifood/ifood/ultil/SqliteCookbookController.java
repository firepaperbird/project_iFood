package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_User;

import java.util.ArrayList;
import java.util.List;

public class SqliteCookbookController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_DISH_ID_INDEX = 1;
    private final int COLUMN_DISH_NAME_INDEX = 2;
    private final int COLUMN_USER_ID_INDEX = 3;

    private final String TABLE_NAME = "CookBook";

    public SqliteCookbookController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<Dish> getCookbookByUserId(int id) {
        List<Dish> listDishes = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "Email = ?", new String[]{id + ""}, null, null, null, null);
            while (cs.moveToNext()) {
                Dish dish = new Dish();
                dish.setId(Integer.parseInt(cs.getString(COLUMN_DISH_ID_INDEX)));
                dish.setTitle(cs.getString(COLUMN_DISH_NAME_INDEX));
                listDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listDishes;
    }

    public Dish checkDishIsAdded(int dishId, int userId){
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "DishId = ? AND UserId = ?", new String[]{dishId + "", userId + ""}, null, null, null, null);
            if (cs.moveToFirst()) {
                Dish dish = new Dish();
                dish.setId(Integer.parseInt(cs.getString(COLUMN_DISH_ID_INDEX)));
                dish.setTitle(cs.getString(COLUMN_DISH_NAME_INDEX));
                return dish;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}
