package com.ifood.ifood.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Dish implements Serializable{
    private String id;
    private String name;
    private String description;
    private String authorId;
    private String imageLink;
    private int ratingStar;
    private String timeCooking;
    private Timestamp createOn;
    private Boolean isActive;
    private Boolean isDelete;
    private String filterType;
    private List<Model_Course> courses;
    private List<Ingredient> ingredients;
    private List<Model_Recipe> stepByStep;
    private List<Model_Review> reviews;
    private List<RelatedDish> relatedDishes;

    public Dish() {
    }


    public Dish(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
        this.authorId = jsonObject.getString("authorId");
        this.imageLink = jsonObject.getString("imageLink");
        this.ratingStar = (int) jsonObject.getDouble("rate");
        this.timeCooking = jsonObject.getString("timeCooking");
        this.isActive = jsonObject.getBoolean("active");
        this.isDelete = jsonObject.getBoolean("delete");
        if (!jsonObject.isNull("courses")){
            this.courses = getCoursesFromJSonArray(jsonObject.getJSONArray("courses"));
        }
        if (!jsonObject.isNull("ingredients")){
            this.ingredients = getIngredientsFromJSonArray(jsonObject.getJSONArray("ingredients"));
        }
        if (!jsonObject.isNull("stepByStep")){
            this.stepByStep = getRecipesFromJSonArray(jsonObject.getJSONArray("stepByStep"));
        }
        if (!jsonObject.isNull("reviews")){
            this.reviews = getReviewsFromJSonArray(jsonObject.getJSONArray("reviews"));
        }
        if (!jsonObject.isNull("relatedDishes")){
            this.relatedDishes = getRelatedDishFromJSonArray(jsonObject.getJSONArray("relatedDishes"));
        }
    }

    private List<Model_Course> getCoursesFromJSonArray(JSONArray jsonArray) throws JSONException {
        List<Model_Course> listCourse = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            Model_Course course = new Model_Course((JSONObject) jsonArray.get(i));
            listCourse.add(course);
        }
        return listCourse;
    }

    private List<Ingredient> getIngredientsFromJSonArray(JSONArray jsonArray) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            Ingredient ingredient = new Ingredient((JSONObject) jsonArray.get(i));
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private List<Model_Recipe> getRecipesFromJSonArray(JSONArray jsonArray) throws JSONException {
        List<Model_Recipe> stepByStep = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            Model_Recipe recipe = new Model_Recipe((JSONObject) jsonArray.get(i));
            stepByStep.add(recipe);
        }
        return stepByStep;
    }

    private List<Model_Review> getReviewsFromJSonArray(JSONArray jsonArray) throws JSONException {
        List<Model_Review> reviews = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            Model_Review review = new Model_Review((JSONObject) jsonArray.get(i));
            reviews.add(review);
        }
        return reviews;
    }

    private List<RelatedDish> getRelatedDishFromJSonArray(JSONArray jsonArray) throws JSONException {
        List<RelatedDish> relatedDishes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            RelatedDish relatedDish = new RelatedDish((JSONObject) jsonArray.get(i));
            relatedDishes.add(relatedDish);
        }
        return relatedDishes;
    }

    public List<Model_Course> getCourses() {
        return courses;
    }

    public List<Model_Recipe> getStepByStep() {
        return stepByStep;
    }

    public void setStepByStep(List<Model_Recipe> stepByStep) {
        this.stepByStep = stepByStep;
    }

    public List<Model_Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Model_Review> reviews) {
        this.reviews = reviews;
    }

    public List<RelatedDish> getRelatedDishes() {
        return relatedDishes;
    }

    public void setRelatedDishes(List<RelatedDish> relatedDishes) {
        this.relatedDishes = relatedDishes;
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

    public String getAuthor() {
        return authorId;
    }

    public void setAuthor(String authorId) {
        this.authorId = authorId;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setCourses(List<Model_Course> courses) {
        this.courses = courses;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void increaseIngredient(Ingredient ingredient){
        if (ingredients == null){
            ingredients = new ArrayList<>();
        }

        ingredients.add(ingredient);
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTimeCooking() {
        return timeCooking;
    }

    public void setTimeCooking(String timeCooking) {
        this.timeCooking = timeCooking;
    }

    public Timestamp getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Timestamp createOn) {
        this.createOn = createOn;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
