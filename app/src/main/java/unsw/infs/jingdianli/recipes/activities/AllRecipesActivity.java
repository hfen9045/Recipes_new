package unsw.infs.jingdianli.recipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.adapters.RecipesAdapter;
import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.insertrecipes.AsyncTaskDelegate;
import unsw.infs.jingdianli.recipes.db.insertrecipes.InsertRecipesAsyncTask;
import unsw.infs.jingdianli.recipes.models.GetAllRecipesResponse;
import unsw.infs.jingdianli.recipes.models.GetSearchResultsResponse;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class AllRecipesActivity extends AppCompatActivity implements AsyncTaskDelegate {
    Context context = this;
    static RecyclerView allRecipesRecyclerView;
    private AsyncTaskDelegate thisActivity = this;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        allRecipesRecyclerView = findViewById(R.id.all_recipes_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        allRecipesRecyclerView.setLayoutManager(layoutManager);

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://api.spoonacular.com/recipes/random?number=24&apiKey=027d674ca3864b91a48ebc30422c32af";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GetAllRecipesResponse getAllRecipesResponse = gson.fromJson(response, GetAllRecipesResponse.class);
                recipeList = getAllRecipesResponse.getRecipes();

                Recipe[] recipeArray = new Recipe[recipeList.size()];
                for (int i = 0; i < recipeList.size(); i++){
                    recipeArray[i] = recipeList.get(i);
                }

                AppDatabase db = AppDatabase.getInstance(context);

                InsertRecipesAsyncTask insertRecipesAsyncTask = new InsertRecipesAsyncTask();
                insertRecipesAsyncTask.setDatabase(db);
                insertRecipesAsyncTask.setDelegate(thisActivity);
                insertRecipesAsyncTask.execute(recipeArray);

                RecipesAdapter recipesAdapter = new RecipesAdapter();
                recipesAdapter.setData(recipeList);
                allRecipesRecyclerView.setAdapter(recipesAdapter);

                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        requestQueue.add(stringRequest);


    }

    @Override
    public void handleTaskResult() {
//        RecipesAdapter recipesAdapter = new RecipesAdapter();
//        recipesAdapter.setData(recipeList);
//        allRecipesRecyclerView.setAdapter(recipesAdapter);
    }
}
