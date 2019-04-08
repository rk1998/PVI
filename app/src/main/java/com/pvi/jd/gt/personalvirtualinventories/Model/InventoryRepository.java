package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pvi.jd.gt.personalvirtualinventories.Controller.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class InventoryRepository {
    private static final InventoryRepository INVENTORY_REPOSITORY = new InventoryRepository();

    public static InventoryRepository getInventoryRepository() {
        return INVENTORY_REPOSITORY;
    }

    private Model model = Model.get_instance();

    public IngredientQuantity convertToInventoryItem(IngredientQuantity newItem) {
        String amountString = newItem.getAmount();
        if(amountString.isEmpty()) {
            String defaultAmount = "3 oz.";
            newItem.setAmount(defaultAmount);
        } else {
            String regex = "(\\d+(\\.\\d+)?(/\\d+)?)\\s";
            Pattern p = Pattern.compile(regex);
            String[] results = p.split(amountString);
            String unit = "";
            if(results.length != 0) {
                unit = results[0];
            }
            Random rng = new Random();
            int amount = rng.nextInt(10) + 1;
            String newAmount = "" + amount + " " + unit;
            newItem.setAmount(newAmount);
        }
//        if(model.getCurrentInventory().getValue() == null) {
//            ArrayList<IngredientQuantity> inventory = new ArrayList<>();
//            inventory.add(newItem);
//            model.getCurrentInventory().setValue(inventory);
//        } else {
//            model.getCurrentInventory().getValue().add(newItem);
//            model.getCurrentInventory().setValue(model.getCurrentInventory().getValue());
//        }

        return newItem;

    }

    public void updateUserInventory(int uid, ArrayList<IngredientQuantity> add,
                                      ArrayList<IngredientQuantity> remove, Context currContext) {
        String url = "https://personalvirtualinventories.000webhostapp.com/editUserInventory.php";
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
    private Map<String, String> getParamMap(int uid, ArrayList<IngredientQuantity> add, ArrayList<IngredientQuantity> remove) {
        JSONArray addjson = new JSONArray();
        for (IngredientQuantity iq : add) {
            //IngredientQuantity newIq = convertToInventoryItem(iq);
            model.getCurrentInventory().getValue().add(iq);
            model.getCurrentInventory().setValue(model.getCurrentInventory().getValue());
            JSONObject entry = new JSONObject();
            try {
                entry.put("name", iq.getIngredient());
                entry.put("amount", iq.getAmount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addjson.put(entry);
        }
        JSONArray rmjson = new JSONArray();
        for (IngredientQuantity iq : remove) {
            JSONObject entry = new JSONObject();
            model.getCurrentInventory().getValue().remove(iq);
            model.getCurrentInventory().setValue(model.getCurrentInventory().getValue());
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
                    model.getCurrentInventory().setValue(inventoryItems);
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
