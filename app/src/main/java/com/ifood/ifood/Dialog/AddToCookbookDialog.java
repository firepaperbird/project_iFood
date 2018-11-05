package com.ifood.ifood.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ifood.ifood.DetailCookbook;
import com.ifood.ifood.ViewCookbooksActivity;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Cookbook_Dish;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;

import java.util.ArrayList;
import java.util.List;

public class AddToCookbookDialog extends DialogFragment {
    private List<Model_Cookbook> listCookbook = new ArrayList<>();
    private Dish dish;
    private Model_Cookbook selectedItem;
    public void insertListCookbookAndDish(List<Model_Cookbook> listCookbook, Dish dish){
        this.listCookbook = listCookbook;
        this.dish = dish;
        if (listCookbook.size() > 0){
            selectedItem = listCookbook.get(0);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add into cookbook");


        if (listCookbook.size() == 0){
            builder.setTitle("Empty Cookbook.");
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Create Cookbook", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), ViewCookbooksActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        } else {
            String[] listCookbookTitles = new String[listCookbook.size()];
            for (int i = 0; i < listCookbook.size(); i++){
                listCookbookTitles[i] = listCookbook.get(i).getTitle();
            }
            //Gía trị được chọn mặc định là giá trị đầu tiên thứ 0.
            builder.setSingleChoiceItems(listCookbookTitles, 0, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItem = listCookbook.get(which);// truyền giá trị vào selectedItem
                }
            });
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(checkDishIsExistInCookbook(selectedItem, dish)){
                        Toast.makeText(getActivity().getApplicationContext(), "Already existed in cookbook", Toast.LENGTH_SHORT).show();
                    } else {
                        Model_Cookbook_Dish cookbook_dish = new Model_Cookbook_Dish();
                        cookbook_dish.setCookbookId(selectedItem.getId());
                        cookbook_dish.setDishId(dish.getId());
                        cookbook_dish.setDishName(dish.getName());

                        SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getActivity().getApplicationContext());
                        boolean result = sqlite.insertDataIntoTable(sqlite.getTableName(), cookbook_dish);

                        Model_Cookbook selectedCookbook = selectedItem;
                        selectedCookbook.setImageId(dish.getImageLink() + "");
                        selectedCookbook.increaseTotalRecipes(1);
                        SqliteCookbookController sqliteCookbookController = new SqliteCookbookController(getActivity().getApplicationContext());
                        sqliteCookbookController.updateDataIntoTable(sqliteCookbookController.getTableName(), selectedCookbook,
                                "Id = ?", new String[] {selectedCookbook.getId().toString()});

                        Intent intent = new Intent(getActivity(), DetailCookbook.class);
                        intent.putExtra("ADD_COOKBOOK_SUCCESSFUL", true);
                        intent.putExtra("COOKBOOK_INFO", selectedCookbook);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }

        return builder.create();
    }

    private boolean checkDishIsExistInCookbook(Model_Cookbook cookbook, Dish dish){
        List<Model_Cookbook> selectedCookbook = new ArrayList<>();
        selectedCookbook.add(cookbook);

        SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getActivity().getApplicationContext());

        boolean isAdded = sqlite.checkDishIsAdded(selectedCookbook, dish.getId());

        return isAdded;
    }
}
