package com.pvi.jd.gt.personalvirtualinventories.Model;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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


    private RecipeRepository() {
        apiID = "111c254f";
        apiKey = "a1591ab602b6fc6d2bbffae96db922ac";
        getrecipeAPIURl = "http://api.yummly.com/v1/api/recipe/";
    }

    /**
     * Gets full recipe info from yummly api when given a recipeID
     * @param recipeID id of the recipe to get info on
     * @param currentContext current activity context
     * @return live data object containing the Recipe
     */
    public LiveData<Recipe> getRecipe(String recipeID, final Context currentContext) {
        //final Recipe requestedRecipe = new Recipe();
        final MutableLiveData<Recipe> data = new MutableLiveData<>();
        String requestURL = getrecipeAPIURl + recipeID + "?" + "_app_id="
                + apiID + "&_app_key=" + apiKey;

        JsonObjectRequest getRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                requestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe requestedRecipe = new Recipe();
                try {
                    String recipeTitle = response.getString("name");
                    requestedRecipe.setRecipeTitle(recipeTitle);
                } catch(JSONException e) {
                    requestedRecipe.setRecipeTitle("None");
                }
                try {
                    String[] ingredients = new Gson().fromJson(
                            response.getString("ingredients"), String[].class);
                    List<String> ingredientList = Arrays.asList(ingredients);
                    requestedRecipe.setIngredients(ingredientList);

                } catch(JSONException e) {
                    requestedRecipe.setIngredients(new ArrayList<String>());

                }
                try {
                    int servings = response.getInt("numberOfServings");
                    requestedRecipe.setNumServings(servings);
                } catch (JSONException e) {
                    requestedRecipe.setNumServings(0);
                }

                try {
                    int totalTime = response.getInt("totalTimeInSeconds");
                    int timeinMinutes = totalTime / 60;
                    requestedRecipe.setCookTime(timeinMinutes);
                } catch (JSONException e) {
                    requestedRecipe.setCookTime(0);
                }


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

    /**
     * Gets a list of full recipes from a list of ids. (Used for the MealPlanView screen)
     * @param recipeIDs List of recipe Ids to search
     * @param currentContext current activity context
     * @return LiveData object containing a list of recipes.
     */
    public LiveData<List<Recipe>> getRecipes(List<String> recipeIDs, final Context currentContext) {
        final MutableLiveData<List<Recipe>> dataList = new MutableLiveData<>();
        final List<Recipe> recipes = new LinkedList<>();

        for(int i = 0; i < recipeIDs.size(); i++) {
            String requestURL = getrecipeAPIURl + recipeIDs.get(i) + "?" + "_app_id="
                    + apiID + "&_app_key=" + apiKey;
            JsonObjectRequest getRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                    requestURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Recipe requestedRecipe = new Recipe();
                    try {
                        String recipeTitle = response.getString("name");
                        requestedRecipe.setRecipeTitle(recipeTitle);
                    } catch(JSONException e) {
                        requestedRecipe.setRecipeTitle("None");
                    }
                    try {
                        String[] ingredients = new Gson().fromJson(
                                response.getString("ingredients"), String[].class);
                        List<String> ingredientList = Arrays.asList(ingredients);
                        requestedRecipe.setIngredients(ingredientList);

                    } catch(JSONException e) {
                        requestedRecipe.setIngredients(new ArrayList<String>());

                    }
                    try {
                        int servings = response.getInt("numberOfServings");
                        requestedRecipe.setNumServings(servings);
                    } catch (JSONException e) {
                        requestedRecipe.setNumServings(0);
                    }

                    try {
                        int totalTime = response.getInt("totalTimeInSeconds");
                        int timeinMinutes = totalTime / 60;
                        requestedRecipe.setCookTime(timeinMinutes);
                    } catch (JSONException e) {
                        requestedRecipe.setCookTime(0);
                    }

                    recipes.add(requestedRecipe);
                    //data.setValue(requestedRecipe);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(currentContext, "Request Error", Toast.LENGTH_SHORT);

                }
            });
            ApiRequestQueue.getInstance(currentContext.getApplicationContext()).addToRequestQueue(getRecipeRequest);
        }
        dataList.setValue(recipes);
        return dataList;
    }

    public MutableLiveData<List<Recipe>> getUserRecipes(int userID, final Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/getUserRecipes.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userID + "");
        final MutableLiveData<List<Recipe>> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Recipe> recipes = new ArrayList<>();
                try {
                    //response = response.getJSONArray(0);
                    for (int i = 0; i < response.length(); i++) {
                        Recipe curr = new Recipe();
                        String api_id = response.getJSONObject(i).get("api_id").toString();
                        curr.setApiID(api_id);
                        recipes.add(curr);
                    }
                    jsonresponse.setValue(recipes);
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
