package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_User;

import java.util.ArrayList;
import java.util.List;

public class SqliteCookbookController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_USER_ID_INDEX = 1;
    private final int COLUMN_TITLE_INDEX = 2;
    private final int COLUMN_IMAGE_ID_INDEX = 3;
    private final int COLUMN_TOTAL_RECIPES_INDEX = 3;

    private final String TABLE_NAME = "CookBook";

    public SqliteCookbookController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<Model_Cookbook> getCookbookByUserId(int userId) {
        List<Model_Cookbook> listCookbook = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "UserId = ?", new String[]{userId + ""}, null, null, null, null);
            while (cs.moveToNext()) {
                Model_Cookbook cookbook = new Model_Cookbook();
                cookbook.setId(Integer.parseInt(cs.getString(COLUMN_ID_INDEX)));
                cookbook.setUserId(Integer.parseInt(cs.getString(COLUMN_USER_ID_INDEX)));
                cookbook.setTitle(cs.getString(COLUMN_TITLE_INDEX));
                cookbook.setImageId(cs.getString(COLUMN_IMAGE_ID_INDEX));
                cookbook.setTotalRecipes(Integer.parseInt(cs.getString(COLUMN_TOTAL_RECIPES_INDEX)));
                listCookbook.add(cookbook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listCookbook;
    }

   /* public Dish checkDishIsAdded(int dishId, int userId){
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
    }*/

    public String getTableName(){
        return TABLE_NAME;
    }
}
