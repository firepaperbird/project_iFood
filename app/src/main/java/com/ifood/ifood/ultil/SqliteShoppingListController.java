package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.ConstantStatusTransaction;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteShoppingListController extends SqliteDataController {
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_TRANSACTION_ID_INDEX = 1;
    private final int COLUMN_USER_ID_INDEX = 2;
    private final int COLUMN_DISH_ID_INDEX = 3;
    private final int COLUMN_DISH_NAME_INDEX = 4;
    private final int COLUMN_DISH_IMAGE_INDEX = 5;
    private final int COLUMN_INGREDIENT_ID_INDEX = 6;
    private final int COLUMN_INGREDIENT_NAME_INDEX = 7;
    private final int COLUMN_INGREDIENT_AMOUNT_INDEX = 8;
    private final int COLUMN_INGREDIENT_UNIT_INDEX = 9;

    private final String TABLE_NAME = "ShoppingList";

    public SqliteShoppingListController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public String getTableName(){
        return TABLE_NAME;
    }

    public List<Dish> getDishInShoppingList(int userId) {
        List<Dish> listDish = new ArrayList<>();
        Dish dish = new Dish();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "UserId = ?", new String[]{userId + ""}, null, null, "DishId ASC", null);
            while (cs.moveToNext()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(Integer.parseInt(cs.getString(COLUMN_INGREDIENT_ID_INDEX)));
                ingredient.setName(cs.getString(COLUMN_INGREDIENT_NAME_INDEX));
                ingredient.setAmount(cs.getString(COLUMN_INGREDIENT_AMOUNT_INDEX));
                ingredient.setUnit(cs.getString(COLUMN_INGREDIENT_UNIT_INDEX));

                dish.setId(cs.getInt(COLUMN_DISH_ID_INDEX));
                dish.setTitle(cs.getString(COLUMN_DISH_NAME_INDEX));
                dish.setImage(cs.getInt(COLUMN_DISH_IMAGE_INDEX));


                if (listDish.size() == 0 || listDish.get(listDish.size() - 1).getId().intValue() != dish.getId().intValue()){
                    listDish.add(dish);
                    dish = new Dish();
                }

                listDish.get(listDish.size() - 1).increaseIngredient(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listDish;
    }

    public List<Dish> getDishByTransactionId(int transactionId, int userId) {
        List<Dish> listDish = new ArrayList<>();
        Dish dish = new Dish();
        boolean isCallBack = false;
        try {

            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "TransactionId = ? AND UserId = ? AND Status = ?",
                    new String[]{transactionId + "", userId + "", ConstantStatusTransaction.SUCCESSFUL + ""}, null, null, "DishId ASC", null);
            while (cs.moveToNext()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(Integer.parseInt(cs.getString(COLUMN_INGREDIENT_ID_INDEX)));
                ingredient.setName(cs.getString(COLUMN_INGREDIENT_NAME_INDEX));
                ingredient.setAmount(cs.getString(COLUMN_INGREDIENT_AMOUNT_INDEX));
                ingredient.setUnit(cs.getString(COLUMN_INGREDIENT_UNIT_INDEX));

                dish.setId(cs.getInt(COLUMN_DISH_ID_INDEX));
                dish.setTitle(cs.getString(COLUMN_DISH_NAME_INDEX));
                dish.setImage(cs.getInt(COLUMN_DISH_IMAGE_INDEX));


                if (listDish.size() == 0 || listDish.get(listDish.size() - 1).getId().intValue() != dish.getId().intValue()){
                    listDish.add(dish);
                    dish = new Dish();
                }

                listDish.get(listDish.size() - 1).increaseIngredient(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listDish;
    }

    public boolean isDishExistInShoppingList(int userId, int dishId) {
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "UserId = ? AND DishId = ? AND Status = ?"
                    , new String[]{userId + "", dishId + "", ConstantStatusTransaction.PENDING + ""}, null, null, "DishId", null);
            if (cs.moveToFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return false;
    }

}
