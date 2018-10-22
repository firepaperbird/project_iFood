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
import android.widget.Toast;

import com.ifood.ifood.R;
import com.ifood.ifood.UserDetailActivity;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;

public class NewCookbookDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.create_new_cookbook, null);
        builder.setView(customLayout);
        builder.setTitle("Create new cookbook");

        final EditText edtCookbookTitle = customLayout.findViewById(R.id.edtCreateCookBookTitle);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SessionLoginController session = new SessionLoginController(getActivity().getApplicationContext());

                String cookbookTitle = edtCookbookTitle.getText().toString();

                Model_Cookbook cookbook = new Model_Cookbook();
                cookbook.setImageId(R.drawable.cookbook_image_blank + "");
                cookbook.setUserId(session.getUserId());
                cookbook.setTitle(cookbookTitle);
                cookbook.setTotalRecipes(0);

                SqliteCookbookController sqlite = new SqliteCookbookController(getActivity().getApplicationContext());
                boolean result = sqlite.insertDataIntoTable(sqlite.getTableName(), cookbook);
                if (result){
                    Intent intent = new Intent(getActivity(), getActivity().getClass());
                    intent.putExtra("CREATE_COOKBOOK_SUCCESSFUL", true);
                    getActivity().startActivity(intent);

                    getActivity().finish();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
