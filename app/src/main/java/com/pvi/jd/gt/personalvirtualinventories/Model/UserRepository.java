package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final UserRepository USER_REPOSITORY = new UserRepository();
    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }


    public MutableLiveData<User> authGetUserInfo(final String email, final String password,
            final Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/authGetUserInfo.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        final MutableLiveData<User> jsonresponse = new MutableLiveData<>();
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener< JSONObject >() {
            @Override
            public void onResponse(JSONObject response) {
                //example output{"id":"1","fname":"John","lname":"Smith","meals_week":"4",
                // "min_meal":"45","num_fam_members":"4","mealplan":
                // [{"api_id":"Vegetarian-Cabbage-Soup-Recipezaar","completed":"0"},
                // {"api_id":"Oriental-Inspired-Vegetable-Soup-Recipezaar","completed":"0"},
                // {"api_id":"Chunky-Rice-And-Bean-Soup-Recipezaar","completed":"0"},
                // {"api_id":"7-Samurai-Vegan-Soup-Recipezaar","completed":"0"}]}
                if (!response.toString().equals("null")) {
                    try {
                        User user = new User(email, password);
                        user.setId(response.getInt("id"));
                        user.setCookTime(response.getInt("min_meal"));
                        user.setNumFamilyMembers(response.getInt("num_fam_members"));
                        JSONArray mealplan = response.getJSONArray("meal_plan");
                        for (int i = 0; i < mealplan.length(); i++) {
                            JSONObject meal = mealplan.getJSONObject(i);
                            Recipe recipe = new Recipe();
                            recipe.setApiID(meal.getString("api_id"));
                            user.getCurrMealPlan().put(recipe, meal.getInt("completed")!=0);
                        }
                        jsonresponse.setValue(user);
                    } catch(JSONException e) {
                        jsonresponse.setValue(new User("sad", "wrong :("));
                        e.printStackTrace();
                    }
                } else {
                    jsonresponse.setValue(new User("wrong", "wrong :("));
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
