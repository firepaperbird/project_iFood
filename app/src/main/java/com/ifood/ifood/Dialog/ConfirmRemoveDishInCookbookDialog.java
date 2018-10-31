package com.ifood.ifood.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ifood.ifood.DetailCookbook;
import com.ifood.ifood.R;
import com.ifood.ifood.ViewCookbooksActivity;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;

import java.util.List;

public class ConfirmRemoveDishInCookbookDialog extends DialogFragment {
    private String warningMessage;
    private Model_Cookbook cookbook;
    private List<Dish> listDishRemove;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(warningMessage);
        builder.setIcon(R.drawable.ic_action_warning);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getActivity().getApplicationContext());
                sqlite.deleteListDishInCookbook(sqlite.getTableName(), listDishRemove, cookbook.getId());
                cookbook.decreaseTotalRecipes(listDishRemove.size());
                if (cookbook.getTotalRecipes() == 0){
                    cookbook.setImageId(R.drawable.cookbook_image_blank + "");
                }
                SqliteCookbookController sqliteCookbook = new SqliteCookbookController(getActivity().getApplicationContext());
                sqliteCookbook.updateDataIntoTable(sqliteCookbook.getTableName(), cookbook, "Id = ?", new String[] {cookbook.getId() + ""});
                Intent intent = new Intent(getActivity(), DetailCookbook.class);
                intent.putExtra("COOKBOOK_INFO", cookbook);
                intent.putExtra("UPDATE_COOKBOOK_SUCCESSFUL", true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    public void setListDishesRemove(Model_Cookbook cookbook, List<Dish> listDishRemove){
        this.cookbook = cookbook;
        this.listDishRemove = listDishRemove;
    }

    public void setWarningMessage(String warningMessage){
        this.warningMessage =  warningMessage;
    }
}
