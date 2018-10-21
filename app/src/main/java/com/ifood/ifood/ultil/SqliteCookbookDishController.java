package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Cookbook_Dish;

import java.util.ArrayList;
import java.util.List;

public class SqliteCookbookDishController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_DISH_ID_INDEX = 1;
    private final int COLUMN_DISH_NAME_INDEX = 2;
    private final int COLUMN_COOKBOOK_ID_INDEX = 3;

    private final String TABLE_NAME = "Cookbook_Dish";


    public SqliteCookbookDishController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<Model_Cookbook_Dish> getDishInCookbook(int cookbookId) {
        List<Model_Cookbook_Dish> listDish = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "CookbookId = ?", new String[]{cookbookId + ""}, null, null, null, null);
            while (cs.moveToNext()) {
                Model_Cookbook_Dish cookbook_dish = new Model_Cookbook_Dish();
                cookbook_dish.setId(Integer.parseInt(cs.getString(COLUMN_ID_INDEX)));
                cookbook_dish.setDishId(Integer.parseInt(cs.getString(COLUMN_DISH_ID_INDEX)));
                cookbook_dish.setDishName(cs.getString(COLUMN_DISH_NAME_INDEX));
                cookbook_dish.setCookbookId(Integer.parseInt(cs.getString(COLUMN_COOKBOOK_ID_INDEX)));
                listDish.add(cookbook_dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listDish;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}