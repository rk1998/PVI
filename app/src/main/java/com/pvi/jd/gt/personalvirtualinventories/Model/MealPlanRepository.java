package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paige on 2/11/2019.
 */

//TODO: meal plan view screen queries DB for meal plan rather than just model

public class MealPlanRepository {

    private static final MealPlanRepository MEALPLAN_REPOSITORY = new MealPlanRepository();
    public static MealPlanRepository getMealPlanRepository() {
        return MEALPLAN_REPOSITORY;
    }

    private Model model = Model.get_instance();

    public MutableLiveData<MealPlan> getCurrMealPlan(MutableLiveData<User> user, Context currContext) {
        if (model.getCurrentMealPlan().getValue() != null) {
            return model.getCurrentMealPlan();
        } else {
            //MutableLiveData<MealPlan> mp = getCurrMealPlanFromDB(user.getValue().getId(), currContext);
            //model.setCurrentMealPlan(mp);
            //return mp;
            return null; //TODO
        }
    }

    public void setCurrMealPlan(MutableLiveData<MealPlan> mp) {
        model.setCurrentMealPlan(mp);
    }

    private MutableLiveData<MealPlan> getCurrMealPlanFromDB(int userID, Context currContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/getCurrMealPlan.php";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userID + "");
        final MutableLiveData<MealPlan> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //TODO: WHAT IF NO MEAL PLAN EXISTS
                MealPlan mp = new MealPlan();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject curr = response.getJSONObject(i);
                        String api_id = curr.get("api_id").toString();
                        boolean completed = curr.getInt("completed")!=0;
                        mp.addMeal(api_id, completed);
                    }
                    jsonresponse.setValue(mp);
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

    public void createCurrMealPlan(List<Recipe> recipes, Context currContext) {
        MealPlan mp = new MealPlan();
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : recipes) {
            mp.addMeal(recipe, false);
            jsonArray.put(recipe.getApiID());
        }
        MutableLiveData<MealPlan> mmp = new MutableLiveData<>();
        mmp.setValue(mp);
        model.setCurrentMealPlan(mmp);
        //addNewMealPlanToDB(model.getCurrentUser().getValue().getId(), jsonArray, currContext);
        addNewMealPlanToDB(1, jsonArray, currContext);

    }

    private void addNewMealPlanToDB(int uid, JSONArray recipeids, Context currContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/createNewCurrMealPlan.php";
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid + "");
        params.put("recipes", recipeids.toString());
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("HELLO", "DATABASE RESPONSE: " +  response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                response.printStackTrace();
            }
        });
        ApiRequestQueue.getInstance(currContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    public void setCompleteStatus(Recipe recipe, boolean complete, Context currContext) {
        MutableLiveData<MealPlan> mp = model.getCurrentMealPlan();
        if (mp.getValue() != null) {
            List<Meal> meals = mp.getValue().getMealPlan();
            for (Meal meal : meals) {
                if (meal.getApiID().equals(recipe.getApiID())) {
                    meal.setCompleted(complete);
                }
            }
        }
        //if (model.getCurrentUser().getValue() != null) {
            //setCompleteStatusInDB(model.getCurrentUser().getValue().getId(), recipe.getApiID(),
            //        complete, currContext);
        //}
        setCompleteStatusInDB(1, recipe.getApiID(), complete, currContext);
    }

    private void setCompleteStatusInDB(int uid, String recipeApiID, boolean complete, Context currContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/changeMealCompletedStatus.php";
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid + "");
        params.put("recipe", recipeApiID);
        params.put("completed", (complete ? 1 : 0) + "");
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("HELLO", "DATABASE RESPONSE: " +  response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                response.printStackTrace();
            }
        });
        ApiRequestQueue.getInstance(currContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
    }
}
