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
import java.util.Map;

public class InventoryRepository {
    private static final InventoryRepository INVENTORY_REPOSITORY = new InventoryRepository();

    public static InventoryRepository getInventoryRepository() {
        return INVENTORY_REPOSITORY;
    }

    private Model model = Model.get_instance();

    public void addToInventory(String newItem) {


    }

    public void removeFromInventory(String itemToRemove) {

    }

    public MutableLiveData<ArrayList<IngredientQuantity>> getUserInventory(int uid, Context currContext) {
        if (model.getCurrentInventory().getValue() != null
                && !model.getCurrentInventory().getValue().isEmpty()) {
            return model.getCurrentInventory();
        }
        String url = "https://personalvirtualinventories.000webhostapp.com/getUserInventory.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid + "");
        final MutableLiveData<ArrayList<IngredientQuantity>> jsonresponse = new MutableLiveData<>();
        JSONArrayRequest jsObjRequest = new JSONArrayRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DATABASE RESPONSE", response.toString());
                ArrayList<IngredientQuantity> inventoryItems = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        IngredientQuantity item = new IngredientQuantity(obj.getString("ingredient"),
                                obj.getString("amount"));
                        inventoryItems.add(item);
                    }
                    jsonresponse.setValue(inventoryItems);
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
