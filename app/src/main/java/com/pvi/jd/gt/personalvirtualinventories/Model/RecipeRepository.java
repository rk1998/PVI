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
    private String apiID;
    private String apiKey;
    private String getrecipeAPIURl;
    private String searchrecipeAPIURL;
    private final Model model = Model.get_instance();
    private Map<String, Recipe> storedRecipes;
    private LinkedList<String> tempRecipeId;
    private LinkedList<String> mealPlanRecipesIDs;
    private RecipeRepository() {
        apiID = "111c254f";
        apiKey = "a1591ab602b6fc6d2bbffae96db922ac";
        getrecipeAPIURl = "http://api.yummly.com/v1/api/recipe/";
        searchrecipeAPIURL = "http://api.yummly.com/v1/api/recipes?";
        storedRecipes = new HashMap<>();
        tempRecipeId = new LinkedList<>();
        mealPlanRecipesIDs = new LinkedList<>();
        tempRecipeId.add("Oriental-Inspired-Vegetable-Soup-Recipezaar");
        tempRecipeId.add("Chunky-Rice-And-Bean-Soup-Recipezaar");
        tempRecipeId.add("7-Samurai-Vegan-Soup-Recipezaar");
        tempRecipeId.add("Cabbage-And-Tofu-Soup-Recipezaar");

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
     * Gets a list of full recipes from a list of ids. (Used for the MealPlanView screen)
     * @param recipeIDs List of recipe Ids to search
     * @param currentContext current activity context
     * @return List of live data objects
     */
    public List<MutableLiveData<Recipe>> getRecipes(List<String> recipeIDs, final Context currentContext) {
        //final MutableLiveData<List<Recipe>> dataList = new MutableLiveData<>();
        final LinkedList<MutableLiveData<Recipe>> dataList = new LinkedList<>();
        for(int i = 0; i < recipeIDs.size(); i++) {
            if(model.getStoredRecipes().containsKey(recipeIDs.get(i))) {
                final MutableLiveData<Recipe> data = new MutableLiveData<>();
                Recipe rep = model.getStoredRecipes().get(recipeIDs.get(i));
                data.setValue(rep);
                dataList.add(data);
            } else {
                final MutableLiveData<Recipe> data = new MutableLiveData<>();
                final String currentID = recipeIDs.get(i);
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

                        //extract recipe source
                        try {
                            JSONObject recipeSource = response.getJSONObject("source");
                            String sourceSiteUrl = recipeSource.getString("sourceSiteUrl");
                            requestedRecipe.setRecipeSource(sourceSiteUrl);
                        } catch(JSONException e) {
                            requestedRecipe.setRecipeSource("");
                        }

                        model.getStoredRecipes().put(currentID, requestedRecipe);
                        data.setValue(requestedRecipe);
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
                dataList.add(data);
            }

        }
        //dataList.setValue(recipes);
        return dataList;
    }

    /**
     * Method that makes a search recipe request to Yummly
     * @param currentContext activity context
     * @return LiveData object containing list of recipe Ids.
     */
    public MutableLiveData<List<String>> searchRecipes(final Context currentContext) {
        final MutableLiveData<List<String>> dataList = new MutableLiveData<>();
        String requestURL = searchrecipeAPIURL + "_app_id="
            + apiID + "&_app_key=" + apiKey +"&requirePictures=true"
                + "&excludedIngredient[]=peanuts&excludedIngredient[]=alcohol"
                + "&allowedDiet[]=388^Lacto+vegetarian"
                + "&maxTotalTimeInSeconds=1900" + "&maxResult=10&start=10";
        JsonObjectRequest searchRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                requestURL, null, new Response.Listener<JSONObject>() {
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
                dataList.setValue(recipeIds);

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

    /**
     * Adds the selected recipes in planRecipes to mealPlanRecipeIds
     * @param planRecipes List of recipe IDs that will be in the User's meal plan
     */
    public void setMealPlanRecipes(List<String> planRecipes) {
        mealPlanRecipesIDs.addAll(planRecipes);
    }

    /**
     * Clears the current meal plan
     */
    public void clearMealPlanRecipes() {
        mealPlanRecipesIDs.clear();
    }


}
