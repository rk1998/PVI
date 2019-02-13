package com.pvi.jd.gt.personalvirtualinventories;

import android.Manifest;
import 	android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.core.executor.testing.*;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

//    @Rule
//    public GrantPermissionRule internetPermissionRule = GrantPermissionRule.grant(Manifest.permission.INTERNET);

    private Context appContext;
    private RecipeRepository repo;
    private UserRepository uRepo;
    private String getrecipeAPIURl;
    private String apiKey;
    private String apiID;


    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getTargetContext();
        repo = RecipeRepository.getRecipeRepository();
        uRepo = UserRepository.getUserRepository();
        apiID = "111c254f";
        apiKey = "a1591ab602b6fc6d2bbffae96db922ac";
        getrecipeAPIURl = "http://api.yummly.com/v1/api/recipe/";

    }

    private <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer= new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(20, TimeUnit.SECONDS);
        return (T) data[0];
    }



    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.pvi.jd.gt.personalvirtualinventories", appContext.getPackageName());
    }

    @Test
    public void testgetRecipe() {
        final String recipeID = "Hot-Turkey-Salad-Sandwiches-Allrecipes";
        final LiveData<Recipe> recipeLiveData = repo.getRecipe(recipeID, appContext.getApplicationContext());
        try {
            Recipe returnedRecipe = getValue(recipeLiveData);
            assertEquals("Hot Turkey Salad Sandwiches", returnedRecipe.getRecipeTitle());
            assertEquals(6, returnedRecipe.getNumServings());
            assertEquals(30, returnedRecipe.getCookTime());
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testgetRecipeList() {
        LinkedList<String> tempRecipeId = new LinkedList<>();
        tempRecipeId.add("Hot-Turkey-Salad-Sandwiches-Allrecipes");
        tempRecipeId.add("Chunky-Rice-And-Bean-Soup-Recipezaar");
        tempRecipeId.add("7-Samurai-Vegan-Soup-Recipezaar");
        tempRecipeId.add("Cabbage-And-Tofu-Soup-Recipezaar");
        //tempRecipeId.add("Falafel-Sandwich-Recipezaar");
//        MutableLiveData<List<Recipe>> recipesLiveData = repo.getRecipes(tempRecipeId, appContext.getApplicationContext());
//        try {
//            List<Recipe> recipes = getValue(recipesLiveData);
//            assertEquals(4, recipes.size());
//        } catch(InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
    }

    @Test
    public void testAPICall() {
        String recipeID = "Vegetarian-Cabbage-Soup-Recipezaar";
        String requestURL = getrecipeAPIURl + recipeID + "?" + "_app_id="
                + apiID + "&_app_key=" + apiKey;
        final Recipe requestedRecipe = new Recipe();
        JsonObjectRequest getRecipeRequest = new JsonObjectRequest(Request.Method.GET,
                requestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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
                    requestedRecipe.setIngredients(new ArrayList<>(ingredientList));

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


                //data.setValue(requestedRecipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext, "Request Error", Toast.LENGTH_SHORT);

            }
        });
        ApiRequestQueue.getInstance(appContext).addToRequestQueue(getRecipeRequest);
        assertEquals("Vegetarian Cabbage Soup", requestedRecipe.getRecipeTitle());
        assertEquals(6, requestedRecipe.getNumServings());
        assertEquals(30, requestedRecipe.getCookTime());


    }

    @Test
    public void testGetUserRecipes() {
        try {
            List<Recipe> x = getValue(repo.getUserRecipes(1, appContext));
            if (x.size() > 0) {
                assertEquals("Vegetarian-Cabbage-Soup-Recipezaar", x.get(0).getApiID());
                assertEquals("Oriental-Inspired-Vegetable-Soup-Recipezaar", x.get(1).getApiID());
                assertEquals("Chunky-Rice-And-Bean-Soup-Recipezaar", x.get(2).getApiID());
                assertEquals("7-Samurai-Vegan-Soup-Recipezaar", x.get(3).getApiID());
                assertEquals("Tomato-Lentil-Soup-Recipezaar_3", x.get(4).getApiID());
                assertEquals("Very-Veggie-Vegetable-Soup-Recipezaar", x.get(5).getApiID());
            } else {
                fail();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchRecipes() {
        try {
            List<String> ids = getValue(repo.searchRecipes(appContext));
            for(int i = 0; i < ids.size(); i++) {
                Log.d("RECIPE ID", ids.get(i));
            }
            assertNotNull(ids);

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthGetUserInfo() {
        MutableLiveData<User> ld = uRepo.authGetUserInfo("fake@fake.com", "password", appContext);
        try {
            User actual = getValue(ld);
            User expected = new User("fake@fake.com", "password");
            expected.setNumFamilyMembers(4);
            expected.setCookTime(45);
            expected.setId(1);
            List<String> apiids = new ArrayList<>();
            apiids.add("Vegetarian-Cabbage-Soup-Recipezaar");
            apiids.add("Oriental-Inspired-Vegetable-Soup-Recipezaar");
            apiids.add("Chunky-Rice-And-Bean-Soup-Recipezaar");
            apiids.add("7-Samurai-Vegan-Soup-Recipezaar");
            Set<Recipe> recipes = actual.getCurrMealPlan().keySet();

            recipes.forEach(recipe -> assertTrue(apiids.contains(recipe.getApiID())));
            //assertEquals(expected, actual);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public void testGetImage() {
//        String imageURL = "http://i2.yummly.com/Hot-Turkey-Salad-Sandwiches-Allrecipes.l.png";
//        final LiveData<Bitmap> imageData = repo.getRecipeImage(imageURL, appContext);
//        try {
//            Bitmap image = getValue(imageData);
//            assertEquals(320, image.getWidth());
//            assertEquals(240, image.getHeight());
//        } catch(InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
