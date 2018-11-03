package com.ifood.ifood.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ifood.ifood.R;
import com.ifood.ifood.UserDetailActivity;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

public class ConfirmRemoveDishShoppingListDialog extends DialogFragment {
    private int dishIdRemove = -1;
    private String userId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove this dish ?");
        builder.setIcon(R.drawable.ic_action_warning);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SqliteShoppingListController sqlite = new SqliteShoppingListController(getActivity().getApplicationContext());
                sqlite.deleteData_From_Table(sqlite.getTableName(), "UserId = ? AND DishId = ?"
                        , new String[]{userId + "", dishIdRemove + ""});
                getActivity().recreate();
                dismiss();
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

    public void setDishIdRemove(int dishIdRemove, String userId){
        this.dishIdRemove = dishIdRemove;
        this.userId = userId;
    }
}
