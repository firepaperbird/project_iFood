package com.ifood.ifood.data;

import com.ifood.ifood.R;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String id;
    private String name;
    private String description;
    private List<Dish> listDish;

    public Menu(String id, String name, String description, List<Dish> listDish) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.listDish = listDish;
    }

    public Menu(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Dish> getListDish() {
        return listDish;
    }

    public void setListDish(List<Dish> listDish) {
        this.listDish = listDish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  List<Dish> getDalyMenu(){
        List<String> tags = new ArrayList<>();
        tags.add("High Vitamin C"); tags.add("High Potassium"); tags.add("Main Dishes"); tags.add("Slow Cooking");
        Dish dish_1 = new Dish("1", "CrockPot Goulash", "", "Marina", R.drawable.crockpot_goulash, tags);

        tags = new ArrayList<>();
        tags.add("High Protein"); tags.add("Main Dishes"); tags.add("Marinating");
        Dish dish_2 = new Dish("2", "Honey Soy Chicken Breasts", "", "Rock", R.drawable.honey_soy_chicken, tags);

        tags = new ArrayList<>();
        tags.add("Main Dishes"); tags.add("Baked Chicken"); tags.add("Oven");
        Dish dish_3 = new Dish("3", "Chicken Wish Mushrooms And Thyme", "", "Little Spice JAR", R.drawable.chicken_with_mushrooms, tags);

        tags = new ArrayList<>();
        tags.add("Main Dishes"); tags.add("Mexican"); tags.add("Slow Cooking");
        Dish dish_4 = new Dish("4", "Mexican Chicken", "", "Granish And Glaze", R.drawable.mexico_chicken, tags);

        tags = new ArrayList<>();
        tags.add("Browning"); tags.add("Frying"); tags.add("Quick"); tags.add("Main Dishes"); tags.add("Baking");
        Dish dish_5 = new Dish("5", "Classic Chicken Parmesan", "", "Good House Keeping", R.drawable.classic_chicken_parmesan, tags);

        List<Dish> list = new ArrayList<>();
        list.add(dish_1);
        list.add(dish_2);
        list.add(dish_3);
        list.add(dish_4);
        list.add(dish_5);
        return list;
    }

    public  List<Dish> getHealthyMenu(){
        List<String> tags = new ArrayList<>();
        tags.add("Quick and easy"); tags.add("Salad");
        Dish dish_1 = new Dish("1", "Autumn Chopped Salad with White Cheddar Dressing", "", "Spoonful of Flavor", R.drawable.autumn_salad, tags);

        tags = new ArrayList<>();
        tags.add("Salads");
        Dish dish_2 = new Dish("2", "Kale Salad with Brussels and Apples", "", "Kitchen Aid", R.drawable.kale_salad_apples, tags);

        tags = new ArrayList<>();
        tags.add("Green Salad"); tags.add("Oven"); tags.add("Fall");
        Dish dish_3 = new Dish("3", "Autumn Pearl Couscous Salad with Roasted Butternut Squash", "", "Little Spice JAR", R.drawable.pearl_couscous_salad, tags);

        tags = new ArrayList<>();
        tags.add("Quick and easy"); tags.add("Salads");
        Dish dish_4 = new Dish("4", "Avocado Salad", "", "Precious Core", R.drawable.avocado_salad, tags);

        tags = new ArrayList<>();
        tags.add("Easy"); tags.add("Salads"); tags.add("Noodles");
        Dish dish_5 = new Dish("5", "Pasta Salad", "", "The Diy Foodie", R.drawable.pasta_salad, tags);

        List<Dish> list = new ArrayList<>();
        list.add(dish_1);
        list.add(dish_2);
        list.add(dish_3);
        list.add(dish_4);
        list.add(dish_5);
        return list;
    }

    public  List<Dish> getGymMenu(){
        List<String> tags = new ArrayList<>();
        tags.add("Quick and easy"); tags.add("Salad");
        Dish dish_1 = new Dish("1", "Black Bean-Quinoa Buddha Bowl", "", "Eating Well", R.drawable.black_bean_bowl, tags);

        tags = new ArrayList<>();
        tags.add("Burritos"); tags.add("Main Dishes"); tags.add("Quick");
        Dish dish_2 = new Dish("2", "Chicken and Avocado Burritos", "", "Closet Cooking", R.drawable.chicken_avocado_burritos, tags);

        tags = new ArrayList<>();
        tags.add("High Protein"); tags.add("Low Fat"); tags.add("Quick and Easy");
        Dish dish_3 = new Dish("3", "Overnight Oat", "", "100 Days of Real Food", R.drawable.overnight_oats, tags);

        tags = new ArrayList<>();
        tags.add("Quick and easy"); tags.add("Appetizers"); tags.add("Eggs");
        Dish dish_4 = new Dish("4", "Deviled Eggs", "", "Skinny Taste", R.drawable.deviled_eggs, tags);

        tags = new ArrayList<>();
        tags.add("High Vitamin C"); tags.add("Low Calories"); tags.add("Low Suggar");
        Dish dish_5 = new Dish("5", "Roasted Broccoli with Smashed Garlic", "", "Skinny Taste", R.drawable.roasted_broccoli, tags);

        List<Dish> list = new ArrayList<>();
        list.add(dish_1);
        list.add(dish_2);
        list.add(dish_3);
        list.add(dish_4);
        list.add(dish_5);
        return list;
    }
}
