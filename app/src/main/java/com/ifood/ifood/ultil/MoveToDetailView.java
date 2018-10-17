package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.Intent;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.detailFoodActivity;
import com.ifood.ifood.mainMenuActivity;

import java.io.Serializable;
import java.util.List;

public class MoveToDetailView {
    public static void moveToDetail(Context source, Class destination, Dish dish, List<Dish> dishList){
        Intent intent = new Intent(source, destination);
        intent.putExtra("dish", dish);
        intent.putExtra("listDish", (Serializable) dishList);
        source.startActivity(intent);
    }
}
