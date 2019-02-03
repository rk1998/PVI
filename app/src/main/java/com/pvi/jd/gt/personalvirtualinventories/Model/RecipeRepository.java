package com.pvi.jd.gt.personalvirtualinventories.Model;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.json.JSONException;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {

    private String apiID = "111c254f";
    private String apiKey = "a1591ab602b6fc6d2bbffae96db922ac";

    private String getrecipeAPIURl = "http://api.yummly.com/v1/api/recipe/recipe-id?_app_id="
            + apiID + "&_app_key=" + apiKey;

    public LiveData<Recipe> getRecipe(String recipeID, final Context currentContext) {
        //final Recipe requestedRecipe = new Recipe();
        final MutableLiveData<Recipe> data = new MutableLiveData<>();
        String requestURL = getrecipeAPIURl + "&" + recipeID;
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


                data.setValue(requestedRecipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext, "Request Error", Toast.LENGTH_SHORT);

            }
        });
        ApiRequestQueue.getInstance(currentContext).addToRequestQueue(getRecipeRequest);
        return data;
    }


    public LiveData<List<Recipe>> getRecipes(List<String> recipeIDs, final Context currentContext) {
        final MutableLiveData<List<Recipe>> dataList = new MutableLiveData<>();
        return dataList;
    }
}
