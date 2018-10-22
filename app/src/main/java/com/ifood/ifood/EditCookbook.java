package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifood.ifood.Dialog.ConfirmDeleteCookbookDialog;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SqliteCookbookController;

public class EditCookbook extends AppCompatActivity {
    private EditText edtTitle;
    private ImageView edtImage;
    private Model_Cookbook cookbook;
    private SqliteCookbookController sqlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cookbook);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        cookbook = (Model_Cookbook) intent.getSerializableExtra("COOKBOOK_INFO");

        sqlite = new SqliteCookbookController(getApplicationContext());

        edtTitle = findViewById(R.id.editCookbookTitle);
        edtTitle.setText(cookbook.getTitle());

        edtImage = findViewById(R.id.editImagecookbook);
        edtImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        edtImage.setImageResource(Integer.parseInt(cookbook.getImageId()));
    }

    public void btnSaveCookbook(View view) {
        edtTitle = findViewById(R.id.editCookbookTitle);
        String txtTitle = edtTitle.getText().toString();

        cookbook.setTitle(txtTitle);

        sqlite.updateDataIntoTable(sqlite.getTableName(), cookbook, "Id = ?", new String[] {cookbook.getId() + ""});
        Intent intent = new Intent(this, DetailCookbook.class);
        intent.putExtra("COOKBOOK_INFO", cookbook);
        intent.putExtra("UPDATE_COOKBOOK_SUCCESSFUL", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void btnDeleteCookbook(View view) {
        ConfirmDeleteCookbookDialog dialog = new ConfirmDeleteCookbookDialog();
        dialog.setEditingCookbook(cookbook);
        dialog.show(getFragmentManager(), "");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
