package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
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
    public void generateGroceryList(int uid, List<Recipe> mealPlanRecipes, Context currContext) {
        ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<>();
        for(int i = 0; i < mealPlanRecipes.size(); i++) {
            Recipe currRecipe = mealPlanRecipes.get(i);
            ArrayList<String> mealIngredients = currRecipe.getIngredientNames();
            for(String ingredientName: mealIngredients) {
                if(currRecipe.getIngredientToUnit().containsKey(ingredientName)) {
                    String amount = currRecipe.getIngredientToUnit().get(ingredientName);
                    IngredientQuantity iq = new IngredientQuantity(ingredientName, amount);
                    ingredientQuantities.add(iq);
                } else {
                    String amount = "";
                    IngredientQuantity iq = new IngredientQuantity(ingredientName, amount);
                    ingredientQuantities.add(iq);
                }
            }
        }
        MutableLiveData<ArrayList<IngredientQuantity>> mld = new MutableLiveData<>();
        mld.setValue(ingredientQuantities);
        model.setCurrentGroceryList(mld);
        updateUserGroceryList(uid, ingredientQuantities, new ArrayList<>(), currContext);
    }

    /**
     * Retrieve user's current grocery list from database
     * @param uid user database id
     * @param currContext current activity context
     * @return mapping of ingredient to the units needed in different recipes
     */
    public MutableLiveData<ArrayList<IngredientQuantity>> getUserGroceryList(int uid, Context currContext) {
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
                    model.getCurrentGroceryList().setValue(groceryList);
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

    public void updateUserGroceryList(int uid, ArrayList<IngredientQuantity> add,
                                      ArrayList<IngredientQuantity> remove, Context currContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/editUserGroceryList.php";
        for(int i = 0; i < add.size(); i++) {
            if(!model.getCurrentGroceryList().getValue().contains(add.get(i))) {
                model.getCurrentGroceryList().getValue().add(add.get(i));
                model.getCurrentGroceryList().setValue(model.getCurrentGroceryList().getValue());
            }
        }
        Map<String, String> params = getParamMap(uid, add, remove);
        final MutableLiveData<ArrayList<IngredientQuantity>> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DATABASE RESPONSE", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                response.printStackTrace();
            }
        });
        ApiRequestQueue.getInstance(currContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    @NonNull
    private Map<String, String> getParamMap(int uid, ArrayList<IngredientQuantity> itemsToAdd, ArrayList<IngredientQuantity> remove) {
        JSONArray addjson = new JSONArray();
        for (int j = 0; j < itemsToAdd.size(); j++) {
            Log.d("ADDING GROCERY ITEM", itemsToAdd.get(j).getIngredient());
            IngredientQuantity iq = itemsToAdd.get(j);
            JSONObject entry = new JSONObject();
           // model.getCurrentGroceryList().getValue().add(iq);
            //model.getCurrentGroceryList().setValue(model.getCurrentGroceryList().getValue());
            try {
                entry.put("name", iq.getIngredient());
                entry.put("amount", iq.getAmount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addjson.put(entry);
        }
        JSONArray rmjson = new JSONArray();
        for (int j = 0; j < remove.size(); j++) {
            IngredientQuantity iq = remove.get(j);
            JSONObject entry = new JSONObject();
//            model.getCurrentGroceryList().getValue().remove(iq);
//            model.getCurrentGroceryList().setValue(model.getCurrentGroceryList().getValue());
            try {
                entry.put("name", iq.getIngredient());
                entry.put("amount", iq.getAmount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rmjson.put(entry);
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid + "");
        params.put("add", addjson.toString());
        params.put("remove", rmjson.toString());
        return params;
    }
}
