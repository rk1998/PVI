package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private final Model model = Model.get_instance();

    public MutableLiveData<User> getCurrUser() {
        return model.getCurrentUser();
    }

    //dummy user: email: fake@fake.com ; password: password
    public MutableLiveData<User> authGetUserInfo(final String email, final String password,
                                                 final Context currentContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/authGetUserInfo.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        final MutableLiveData<User> jsonresponse = new MutableLiveData<>();
        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url, params, new Response.Listener< JSONObject >() {
            //TODO: SPECIFIC RESPONSE FOR FAILED AUTHENTICATION
            @Override
            public void onResponse(JSONObject response) {
                //example output{"id":"1","fname":"John","lname":"Smith","meals_week":"4",
                // "min_meal":"45","num_fam_members":"4"}
                if (!response.toString().equals("null")) {
                    try {
                        User user = new User(email, password);
                        user.setId(response.getInt("id"));
                        user.setCookTime(response.getInt("min_meal"));
                        user.setNumFamilyMembers(response.getInt("num_fam_members"));
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
}
