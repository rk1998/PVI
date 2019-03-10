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

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final UserRepository USER_REPOSITORY = new UserRepository();
    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }
    private final Model model = Model.get_instance();

    public MutableLiveData<User> getCurrUser() {
        return model.getCurrentUser();
    }

    /*public User getCurrUser() {
        return model.getCurrentUser();
    }*/


    /**
     * Sets temp user with initial email and pw
     * @param user initial user info
     */
    public void setTempUser(User user) {
        model.setTempUser(user);
    }

    /**
     * Gets temporary user used for registration/account setup
     * @return tempUser
     */
    public User getTempUser() {
        return model.getTempUser();
    }

    public void setCurrUser(MutableLiveData<User> user) {
        model.setCurrentUser(user);
    }

    /*public void setCurrUser(User user) {
        model.setCurrentUser(user);
    }*/

    //dummy user: email: fake@fake.com ; password: password
    public MutableLiveData<User> authGetUserInfo(final String email, final String password,
                                                 final Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/authGetUserInfo.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        try {
            byte[] bytesOfMessage = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            String hashtext = bigInt.toString(16);
            params.put("password", hashtext);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final MutableLiveData<User> jsonresponse = new MutableLiveData<>();
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener< JSONObject >() {
            @Override
            public void onResponse(JSONObject response) {
                //example output{"id":"1","fname":"John","lname":"Smith","meals_week":"4",
                // "min_meal":"45","num_fam_members":"4"}
                if (!response.toString().equals("null")) {
                    try {
                        User user = new User(email, password);
                        if (response.getBoolean("auth")) {
                            user.setId(response.getInt("id"));
                            user.setCookTime(response.getInt("min_meal"));
                            user.setNumFamilyMembers(response.getInt("num_fam_members"));
                            user.setAuth(true);
                        } else {
                            user.setAuth(false);
                        }
                        jsonresponse.setValue(user);
                        MutableLiveData<User> mUser = new MutableLiveData<>();
                        mUser.setValue(user);
                        model.setCurrentUser(mUser);
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

    public MutableLiveData<Integer> createUserInDB(final User user, Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/createNewUser.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", user.getEmail());
        try {
            byte[] bytesOfMessage = user.getPassword().getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            String hashtext = bigInt.toString(16);
            params.put("password", hashtext);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.put("cooktime", user.getCookTime() + "");
        params.put("numfammembers", user.getNumFamilyMembers() + "");
        params.put("mealsperweek", user.getMealsPerWeek() + "");
        params.put("diets", new JSONArray(user.getDietRestriction()).toString());
        params.put("allergies", new JSONArray(user.getFoodAllergies()).toString());
        params.put("hatedfoods", new JSONArray(user.getHatedFoods()).toString());
        JSONArray jsonArray = new JSONArray();
        user.getRecipes().forEach(recipe -> jsonArray.put(recipe.getApiID()));
        params.put("recipes", jsonArray.toString());
        final MutableLiveData<Integer> id = new MutableLiveData<>();
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener< JSONObject >() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DATABASE RESPONSE", response.toString());
                try {
                    id.setValue(response.getInt("id"));
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
        return id;
    }

    public MutableLiveData<Boolean> checkIfEmailInUse(String email, Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/checkIfEmailInUse.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        final MutableLiveData<Boolean> out = new MutableLiveData<>();
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener< JSONObject >() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DATABASE RESPONSE", response.toString());
                try {
                    if (response.getBoolean("inUse")) {
                        out.setValue(true);
                    } else {
                        out.setValue(false);
                    }
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
        return out;
    }

}
