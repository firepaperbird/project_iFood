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
import com.ifood.ifood.ViewCookbooksActivity;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewCookbookDialog extends DialogFragment {
    private List<Model_Cookbook> listCookbook = new ArrayList<>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.create_new_cookbook, null);
        builder.setView(customLayout);
        builder.setTitle("Create new cookbook");

        final EditText edtCookbookTitle = customLayout.findViewById(R.id.edtCreateCookBookTitle);
        builder.setNegativeButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isExist = false;
                SessionLoginController session = new SessionLoginController(getActivity().getApplicationContext());
                String cookbookTitle = edtCookbookTitle.getText().toString();

                for (Model_Cookbook cookbook : listCookbook){
                    if (cookbook.getName().toLowerCase().equals(cookbookTitle.toLowerCase())){
                        isExist = true;
                        break;
                    }
                }
                if (!isExist){
                    Model_Cookbook cookbook = new Model_Cookbook();
                    cookbook.setId(UUID.randomUUID().toString());
                    cookbook.setUserId(session.getUserId());
                    cookbook.setName(cookbookTitle);


                    SqliteCookbookController sqlite = new SqliteCookbookController(getActivity().getApplicationContext());
                    boolean result = sqlite.insertDataIntoTable(sqlite.getTableName(), cookbook);
                    if (result){
                        getActivity().recreate();
                    } else {
                        Toast.makeText(getActivity(), "Something wrong!", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "That cookbook have already existed!", Toast.LENGTH_SHORT).show();
                }
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

    public void setListCookbook(List<Model_Cookbook> listCookbook){
        this.listCookbook = listCookbook;
    }
}
