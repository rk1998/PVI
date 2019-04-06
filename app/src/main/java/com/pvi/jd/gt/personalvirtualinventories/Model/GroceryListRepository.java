package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroceryListRepository {
    private static final GroceryListRepository GROCERY_LIST_REPOSITORY = new GroceryListRepository();
    private Model model = Model.get_instance();

    public static GroceryListRepository getGroceryListRepository() {
        return GROCERY_LIST_REPOSITORY;
    }

    /**
     * Generates a user's grocery list given their current meal plan
     * @param mealPlanRecipes recipes selected for their meal plan
     */
    public void generateGroceryList(List<Recipe> mealPlanRecipes) {
        ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<>();
        for(int i = 0; i < mealPlanRecipes.size(); i++) {
            Recipe currRecipe = mealPlanRecipes.get(i);
            ArrayList<String> mealIngredients = currRecipe.getIngredientNames();
            for(String ingredientName: mealIngredients) {
                String amount = currRecipe.getIngredientToUnit().get(ingredientName);
                IngredientQuantity iq = new IngredientQuantity(ingredientName, amount);
                ingredientQuantities.add(iq);
            }
        }
        MutableLiveData<ArrayList<IngredientQuantity>> mld = new MutableLiveData<>();
        mld.setValue(ingredientQuantities);
        model.setCurrentGroceryList(mld);
        //TODO: writeback this data to the database
    }

    /**
     * Retrieve user's current grocery list from database
     * @param uid user database id
     * @param currContext current activity context
     * @return mapping of ingredient to the units needed in different recipes
     */
    public MutableLiveData<ArrayList<IngredientQuantity>> getCurrentGroceryList(int uid, Context currContext) {
        if (model.getCurrentGroceryList().getValue() != null
                && !model.getCurrentGroceryList().getValue().isEmpty()) {
            return model.getCurrentGroceryList();
        }
        String url = "https://personalvirtualinventories.000webhostapp.com/getUserGroceryList.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid + "");
        final MutableLiveData<ArrayList<IngredientQuantity>> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DATABASE RESPONSE", response.toString());
                ArrayList<IngredientQuantity> groceryList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        IngredientQuantity item = new IngredientQuantity(obj.getString("ingredient"),
                                obj.getString("amount"));
                        groceryList.add(item);
                    }
                    jsonresponse.setValue(groceryList);
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
        ApiRequestQueue.getInstance(currContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
        return jsonresponse;
    }


}
