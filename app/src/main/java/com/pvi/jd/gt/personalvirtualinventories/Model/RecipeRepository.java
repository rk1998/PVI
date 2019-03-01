package com.pvi.jd.gt.personalvirtualinventories.Model;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecipeRepository {

    private static final RecipeRepository RECIPE_REPOSITORY = new RecipeRepository();
    public static RecipeRepository getRecipeRepository() {
        return RECIPE_REPOSITORY;
    }
    private final int MAX_RESULTS_PER_MEAL;
    private String apiID;
    private String apiKey;
    private String getrecipeAPIURl;
    private String searchrecipeAPIURL;
    private final Model model = Model.get_instance();
    private LinkedList<String> tempRecipeId;
    private RecipeRepository() {
        apiID = "111c254f";
        apiKey = "a1591ab602b6fc6d2bbffae96db922ac";
        getrecipeAPIURl = "http://api.yummly.com/v1/api/recipe/";
        searchrecipeAPIURL = "http://api.yummly.com/v1/api/recipes?";
        tempRecipeId = new LinkedList<>();
        tempRecipeId.add("Oriental-Inspired-Vegetable-Soup-Recipezaar");
        tempRecipeId.add("Chunky-Rice-And-Bean-Soup-Recipezaar");
        tempRecipeId.add("7-Samurai-Vegan-Soup-Recipezaar");
        tempRecipeId.add("Cabbage-And-Tofu-Soup-Recipezaar");
        MAX_RESULTS_PER_MEAL = 40;

    }


    /**
     * Gets full recipe info from yummly api when given a recipeID
     * @param recipeID id of the recipe to get info on
     * @param currentContext current activity context
     * @return live data object containing the Recipe
     */
    public MutableLiveData<Recipe> getRecipe(final String recipeID, final Context currentContext) {
        //final Recipe requestedRecipe = new Recipe();
        if(model.getStoredRecipes().containsKey(recipeID)) {
            final MutableLiveData<Recipe> data = new MutableLiveData<>();
            data.setValue(model.getStoredRecipes().get(recipeID));
            return data;
        } else {
            final MutableLiveData<Recipe> data = new MutableLiveData<>();
            String requestURL = getrecipeAPIURl + recipeID + "?" + "_app_id="
                    + apiID + "&_app_key=" + apiKey;

            JsonObjectRequest getRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                    requestURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Recipe requestedRecipe = new Recipe();
                    requestedRecipe.setApiID(recipeID);
                    //extract recipe title
                    try {
                        String recipeTitle = response.getString("name");
                        requestedRecipe.setRecipeTitle(recipeTitle);
                    } catch(JSONException e) {
                        requestedRecipe.setRecipeTitle("None");
                    }

                    //extract ingredient list
                    try {
                        JSONArray jsonIngredients = response.getJSONArray("ingredientLines");
                        ArrayList<String> ingredientList = new ArrayList<>();
                        for(int i = 0; i < jsonIngredients.length(); i++) {
                            String ingredientLine = jsonIngredients.getString(i);
                            ingredientList.add(ingredientLine);
                        }
                        requestedRecipe.setIngredients(ingredientList);

                    } catch(JSONException e) {
                        requestedRecipe.setIngredients(new ArrayList<String>());

                    }

                    //Extracting Image url from JSON response
                    if(response.has("images")) {
                        try {
                            JSONArray imgArr = response.getJSONArray("images");
                            JSONObject images = imgArr.getJSONObject(0);

                            if(images.has("hostedLargeUrl")) {
                                String smallImgURL = images.getString("hostedLargeUrl");
                                requestedRecipe.setImgURL(smallImgURL);
                            } else if(images.has("hostedSmallUrl")) {
                                String largeImgURL = images.getString("hostedSmallUrl");
                                requestedRecipe.setImgURL(largeImgURL);
                            }

                        } catch(JSONException e) {
                            requestedRecipe.setImgURL("");
                        }
                    } else {
                        requestedRecipe.setImgURL("");
                    }

                    //Extracting number of servings
                    try {
                        int servings = response.getInt("numberOfServings");
                        requestedRecipe.setNumServings(servings);
                    } catch (JSONException e) {
                        requestedRecipe.setNumServings(0);
                    }

                    //extract total cook time
                    try {
                        int totalTime = response.getInt("totalTimeInSeconds");
                        int timeinMinutes = totalTime / 60;
                        requestedRecipe.setCookTime(timeinMinutes);
                    } catch (JSONException e) {
                        requestedRecipe.setCookTime(0);
                    }

                    //extract recipe source
                    try {
                        JSONObject recipeSource = response.getJSONObject("source");
                        String sourceSiteUrl = recipeSource.getString("sourceSiteUrl");
                        requestedRecipe.setRecipeSource(sourceSiteUrl);
                    } catch(JSONException e) {
                        requestedRecipe.setRecipeSource("");
                    }

                    model.getStoredRecipes().put(recipeID, requestedRecipe);
                    data.setValue(requestedRecipe);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(currentContext, "Request Error", Toast.LENGTH_SHORT);

                }
            });
            ApiRequestQueue.getInstance(currentContext.getApplicationContext()).addToRequestQueue(getRecipeRequest);
            return data;

        }
    }

    /**
     * gets list of recipe objects from list of recipe api ids
     * @param recipeIds list of recipe api ids to query
     * @param currentContext current activity context
     * @return MutableLiveData object of a list of recipes
     */
    public MutableLiveData<List<Recipe>> getRecipesFromList(List<String> recipeIds, final Context currentContext) {
        final MutableLiveData<List<Recipe>> dataList = new MutableLiveData<>();
        dataList.setValue(new LinkedList<>());
        for(int i = 0; i < recipeIds.size(); i++) {
            if(model.getStoredRecipes().containsKey(recipeIds.get(i))) {
                Recipe rep = model.getStoredRecipes().get(recipeIds.get(i));
                dataList.getValue().add(rep);
                dataList.setValue(dataList.getValue());
            } else {
                //final MutableLiveData<Recipe> data = new MutableLiveData<>();
                final String currentID = recipeIds.get(i);
                String requestURL = getrecipeAPIURl + currentID + "?" + "_app_id="
                        + apiID + "&_app_key=" + apiKey;
                JsonObjectRequest getRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                        requestURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Recipe requestedRecipe = new Recipe();
                        requestedRecipe.setApiID(currentID);
                        try {
                            String recipeTitle = response.getString("name");
                            requestedRecipe.setRecipeTitle(recipeTitle);
                        } catch(JSONException e) {
                            requestedRecipe.setRecipeTitle("None");
                        }

                        //extract ingredient list
                        try {
                            JSONArray jsonIngredients = response.getJSONArray("ingredientLines");
                            ArrayList<String> ingredientList = new ArrayList<>();
                            for(int i = 0; i < jsonIngredients.length(); i++) {
                                String ingredientLine = jsonIngredients.getString(i);
                                ingredientList.add(ingredientLine);
                            }
                            requestedRecipe.setIngredients(ingredientList);

                        } catch(JSONException e) {
                            requestedRecipe.setIngredients(new ArrayList<String>());

                        }
                        ArrayList<String> nutrientInfo = new ArrayList<>();
                        try {
                            JSONArray nutrients = response.getJSONArray("nutritionEstimates");
                            for(int i = 0; i < nutrients.length(); i++) {
                                JSONObject nutrientObject = nutrients.getJSONObject(i);
                                int value = nutrientObject.getInt("value");
                                String unitName = nutrientObject.getJSONObject("unit")
                                        .getString("plural");
                                if(unitName.equals("calories")) {
                                    nutrientInfo.add(value + " " + unitName + "\n");
                                } else {
                                    String attribute = nutrientObject.getString("attribute");
                                    if(attribute.equals("NA")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    } else if(attribute.equals("PROCNT")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    } else if(attribute.equals("SUGAR")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    } else if(attribute.equals("CHOCDF")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    } else if(attribute.equals("FASAT")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    } else if(attribute.equals("CHOLE")) {
                                        String description = nutrientObject.getString("description");
                                        String nutrientLine = description + ": " + value + " " + unitName + "\n";
                                        nutrientInfo.add(nutrientLine);
                                    }

                                }
                            }
                            requestedRecipe.setNutritionInfo(nutrientInfo);
                        } catch (JSONException e) {
                            requestedRecipe.setNutritionInfo(nutrientInfo);

                        }

                        //Extracting Image url from JSON response
                        if(response.has("images")) {
                            try {
                                JSONArray imgArr = response.getJSONArray("images");
                                JSONObject images = imgArr.getJSONObject(0);

                                if(images.has("hostedLargeUrl")) {
                                    String smallImgURL = images.getString("hostedLargeUrl");
                                    requestedRecipe.setImgURL(smallImgURL);
                                } else if(images.has("hostedSmallUrl")) {
                                    String largeImgURL = images.getString("hostedSmallUrl");
                                    requestedRecipe.setImgURL(largeImgURL);
                                } else {
                                    String mediumImgURl = images.getString("hostedMediumUrl");
                                    requestedRecipe.setImgURL(mediumImgURl);
                                }

                            } catch(JSONException e) {
                                requestedRecipe.setImgURL("");
                            }
                        } else {
                            requestedRecipe.setImgURL("");
                        }

                        //Extracting number of servings
                        try {
                            int servings = response.getInt("numberOfServings");
                            requestedRecipe.setNumServings(servings);
                        } catch (JSONException e) {
                            requestedRecipe.setNumServings(0);
                        }

                        //extract total cook time
                        try {
                            int totalTime = response.getInt("totalTimeInSeconds");
                            int timeinMinutes = totalTime / 60;
                            requestedRecipe.setCookTime(timeinMinutes);
                        } catch (JSONException e) {
                            requestedRecipe.setCookTime(0);
                        }

                        //extract prep time
                        try {
                            int prepTime = response.getInt("prepTimeInSeconds");
                            int prepTimeinMinutes = prepTime / 60;
                            requestedRecipe.setPrepTime(prepTimeinMinutes);
                        } catch (JSONException e) {
                            requestedRecipe.setPrepTime(0);
                        }

                        //extract recipe source
                        try {
                            JSONObject recipeSource = response.getJSONObject("source");
                            String sourceSiteUrl = recipeSource.getString("sourceSiteUrl");
                            requestedRecipe.setRecipeSource(sourceSiteUrl);
                        } catch(JSONException e) {
                            requestedRecipe.setRecipeSource("");
                        }

                        model.getStoredRecipes().put(currentID, requestedRecipe);
                        dataList.getValue().add(requestedRecipe);
                        dataList.setValue(dataList.getValue());
                        //recipes.add(requestedRecipe);
                        //LinkedList<Recipe> recipesCopy = new LinkedList<>(recipes);
                        //dataList.setValue(recipesCopy);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(currentContext, "Request Error", Toast.LENGTH_SHORT);

                    }
                });
                ApiRequestQueue.getInstance(currentContext.getApplicationContext())
                        .addToRequestQueue(getRecipeRequest);

            }
        }
        return dataList;
    }


    /**
     * Method that makes a search recipe request to Yummly
     * @param currentContext activity context
     * @param newUser User object that holds answers to the questionnaire
     * @return LiveData object containing list of recipe Ids.
     */
    public MutableLiveData<List<String>> searchRecipes(User newUser, final Context currentContext) {
        final MutableLiveData<List<String>> dataList = new MutableLiveData<>();
        dataList.setValue(new LinkedList<>());
        List<String> favoriteMeals = newUser.getFavoriteMealNames();
        List<String> foodAllergies = newUser.getFoodAllergies();
        List<String> dietaryPreferences = newUser.getDietRestriction();
        List<String> hatedFoods = newUser.getHatedFoods();
        int maxTotalTime = newUser.getCookTime();
        String requestURL = searchrecipeAPIURL + "_app_id="
            + apiID + "&_app_key=" + apiKey +"&requirePictures=true";
        //add allergies to search url
        for(int i = 0; i < foodAllergies.size(); i++) {
            String allergy = foodAllergies.get(i);
            requestURL += "&allowedAllergy[]=" + allergy;
        }
        //add hated foods
        for(int i = 0; i < hatedFoods.size(); i++) {
            String hatedFood = hatedFoods.get(i);
            hatedFood = hatedFood.replaceAll("\\s", "+");
            requestURL += "&excludedIngredient[]=" + hatedFood;
        }

        //add dietary preferences
        for(int i = 0; i < dietaryPreferences.size(); i++) {
            String diet = dietaryPreferences.get(i);
            requestURL += "&allowedDiet[]=" + diet;
        }
        int mealCount = 0;
        for(int i = 0; i < favoriteMeals.size(); i++) {
            String faveMeal = favoriteMeals.get(i);
            String requestFaveMealURL =  requestURL + "&q=" + faveMeal + "&maxResult=4";
            JsonObjectRequest searchRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                    requestFaveMealURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    List<String> recipeIds = new LinkedList<>();
                    try {
                        JSONArray matches = response.getJSONArray("matches");
                        for(int i = 0; i < matches.length(); i++) {
                            JSONObject obj = matches.getJSONObject(i);
                            try {
                                String recipeId = obj.getString("id");
                                recipeIds.add(recipeId);
                            } catch(JSONException e) {
                                recipeIds.add("none");
                            }
                        }
                    } catch(JSONException e) {
                        recipeIds.add("Cabbage-And-Tofu-Soup-Recipezaar");
                    }
                    dataList.getValue().addAll(recipeIds);
                    dataList.setValue(dataList.getValue());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    List<String> recipeErrorList = new LinkedList<>();
                    recipeErrorList.addAll(tempRecipeId);
                    dataList.setValue(recipeErrorList);

                }
            });
            ApiRequestQueue.getInstance(currentContext.getApplicationContext())
                    .addToRequestQueue(searchRecipeRequest);
            mealCount += 4;

        }

        int mealsRemaining = MAX_RESULTS_PER_MEAL - mealCount;

        String remainingMealRequest = requestURL + "&maxResult=" + mealsRemaining;

        JsonObjectRequest searchRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                remainingMealRequest, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> recipeIds = new LinkedList<>();
                try {
                    JSONArray matches = response.getJSONArray("matches");
                    for(int i = 0; i < matches.length(); i++) {
                        JSONObject obj = matches.getJSONObject(i);
                        try {
                            String recipeId = obj.getString("id");
                            recipeIds.add(recipeId);
                        } catch(JSONException e) {
                            recipeIds.add("none");
                        }
                    }
                } catch(JSONException e) {
                    recipeIds.add("Simple-Skillet-Green-Beans-2352743");
                }
                dataList.getValue().addAll(recipeIds);
                dataList.setValue(dataList.getValue());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<String> recipeErrorList = new LinkedList<>();
                recipeErrorList.addAll(tempRecipeId);
                dataList.setValue(recipeErrorList);

            }
        });
        ApiRequestQueue.getInstance(currentContext.getApplicationContext())
                .addToRequestQueue(searchRecipeRequest);
        return dataList;
    }

    /**
     * gets a list of the api IDs of recipes the user has saved (the user's "15-30")
     * @param userID the user whose recipes to find
     * @param currentContext current activity context
     * @return livedata object containing a list of api ID strings
     */
    public MutableLiveData<List<String>> getUserRecipeIDs(int userID, final Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/getUserRecipeIDs.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userID + "");
        final MutableLiveData<List<String>> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> ids = new ArrayList<>();
                try {
                    //response = response.getJSONArray(0);
                    for (int i = 0; i < response.length(); i++) {
                        ids.add(response.getJSONObject(i).get("api_id").toString());
                    }
                    jsonresponse.setValue(ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                response.printStackTrace();
            }
        });
        ApiRequestQueue.getInstance(currentContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
        return jsonresponse;
    }



}
