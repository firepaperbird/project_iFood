package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ifood.ifood.data.Comment_User;
import com.ifood.ifood.data.Model_Review;

import java.util.List;

public class ViewCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);

        Intent intent = getIntent();
        List<Model_Review> list = (List<Model_Review>) intent.getSerializableExtra("listComment");
        LinearLayout layout = findViewById(R.id.layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (Model_Review commentUser:list) {
            LinearLayout comment = new LinearLayout(this);
            comment.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            comment.setOrientation(LinearLayout.HORIZONTAL);
            comment.setPadding(20,0,0,0);

            TextView user_img = new TextView(this);
            user_img.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
            user_img.setBackgroundResource(R.drawable.icon_user_50);

            LinearLayout userInfo = new LinearLayout(this);
            userInfo.setLayoutParams(new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.WRAP_CONTENT));
            userInfo.setOrientation(LinearLayout.VERTICAL);

            TextView username = new TextView(this);
            username.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            username.setText(commentUser.getUserName());
            username.setTypeface(null, Typeface.BOLD);

            TextView time = new TextView(this);
            time.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            time.setText(commentUser.getReviewOn().toString().substring(0,10));

            RatingBar rating = new RatingBar(this,null,android.R.attr.ratingBarStyleSmall);
            rating.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rating.setNumStars(5);
            rating.setRating(commentUser.getRate());
            rating.setClickable(false);


            userInfo.addView(username);
            userInfo.addView(time);

            comment.addView(user_img);
            comment.addView(userInfo);
            comment.addView(rating);

            layout.addView(comment);

            TextView comment_review = new TextView(this);
            comment_review.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            comment_review.setText(commentUser.getComment());
            comment_review.setPadding(30,0,20,30);

            layout.addView(comment_review);
        }
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
