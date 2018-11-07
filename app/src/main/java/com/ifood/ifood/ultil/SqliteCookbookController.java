package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SqliteCookbookController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_USER_ID_INDEX = 1;
    private final int COLUMN_NAME_INDEX = 2;
    private final int COLUMN_DECRIPTION_INDEX = 3;
    private final int COLUMN_CREATEON_INDEX = 4;

    private final String TABLE_NAME = "CookBook";

    public SqliteCookbookController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<Model_Cookbook> getCookbookByUserId(String userId) {
        List<Model_Cookbook> listCookbook = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "UserId = ?", new String[]{userId}, null, null, null, null);
            while (cs.moveToNext()) {
                Model_Cookbook cookbook = new Model_Cookbook();
                cookbook.setId(cs.getString(COLUMN_ID_INDEX));
                cookbook.setUserId(cs.getString(COLUMN_USER_ID_INDEX));
                cookbook.setName(cs.getString(COLUMN_NAME_INDEX));
                cookbook.setCreateOn(cs.getString(COLUMN_CREATEON_INDEX));
                cookbook.setDescription(cs.getString(COLUMN_DECRIPTION_INDEX));

                List<Dish> dishesInCookbook = new ArrayList<>();
                Cursor cs2 = database.query("CookBook_Dish", null,"CookbookId = ?", new String[]{cookbook.getId()}, null, null, null, null);
                while (cs2.moveToNext()){
                    Dish dish = new Dish();
                    dish.setId(cs2.getString(1));
                    dish.setImageLink(cs2.getString(3));
                    dishesInCookbook.add(dish);
                }
                cookbook.setDishesInCookBook(dishesInCookbook);
                listCookbook.add(cookbook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listCookbook;
    }

    public Model_Cookbook getCookbookById(String id) {
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "Id = ?", new String[]{id}, null, null, null, null);
            if (cs.moveToFirst()) {
                Model_Cookbook cookbook = new Model_Cookbook();
                cookbook.setId(cs.getString(COLUMN_ID_INDEX));
                cookbook.setUserId(cs.getString(COLUMN_USER_ID_INDEX));
                cookbook.setName(cs.getString(COLUMN_NAME_INDEX));
                cookbook.setCreateOn(cs.getString(COLUMN_CREATEON_INDEX));
                cookbook.setDescription(cs.getString(COLUMN_DECRIPTION_INDEX));

                List<Dish> dishesInCookbook = new ArrayList<>();
                Cursor cs2 = database.query("CookBook_Dish", null,"CookbookId = ?", new String[]{cookbook.getId()}, null, null, null, null);
                while (cs2.moveToNext()){
                    Dish dish = new Dish();
                    dish.setId(cs2.getString(1));
                    dish.setImageLink(cs2.getString(3));
                    dishesInCookbook.add(dish);
                }
                cookbook.setDishesInCookBook(dishesInCookbook);
                return cookbook;
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
