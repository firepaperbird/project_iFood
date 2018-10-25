package com.ifood.ifood.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.ifood.ifood.R;
import com.ifood.ifood.UserDetailActivity;
import com.ifood.ifood.ViewCookbooksActivity;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;

public class ConfirmDeleteCookbookDialog extends DialogFragment {
    private Model_Cookbook cookbook;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this cookbook ?");
        builder.setIcon(R.drawable.ic_action_warning);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SqliteCookbookController sqlite = new SqliteCookbookController(getActivity().getApplicationContext());
                sqlite.deleteData_From_Table(sqlite.getTableName(), "Id = ?", new String[] {cookbook.getId() + ""});
                Intent intent = new Intent(getActivity(), ViewCookbooksActivity.class);
                intent.putExtra("DELETE_COOKBOOK_SUCCESSFUL", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
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

    public void setEditingCookbook(Model_Cookbook cookbook){
        this.cookbook = cookbook;
    }
}
