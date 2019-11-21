package unsw.infs.jingdianli.recipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import unsw.infs.jingdianli.recipes.adapters.RecipeIngredientsAdapter;
import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.getrecipebyid.AsyncTaskDelegate;
import unsw.infs.jingdianli.recipes.db.getrecipebyid.GetRecipeByIdAsyncTask;
import unsw.infs.jingdianli.recipes.db.updatefavorite.UpdateFavoriteAsyncTask;
import unsw.infs.jingdianli.recipes.models.GetAllRecipesResponse;
import unsw.infs.jingdianli.recipes.models.GetRecipeIngredientsbyIDResponse;
import unsw.infs.jingdianli.recipes.models.Ingredient;
import unsw.infs.jingdianli.recipes.models.Recipe;

import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_ID_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_IMAGE_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_INSTRUCTION_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_READY_IN_MINUTES_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_SERVINGS_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_SOURCE_URL_KEY;
import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_TITLE_KEY;

public class RecipeDetailsActivity extends AppCompatActivity implements AsyncTaskDelegate {
    private Context context = this;
    ImageView recipeImage;
    TextView recipeTitle;
    TextView recipeReadyInMinutes;
    TextView recipeServings;
    RecyclerView recipeIngredients;
    TextView recipeInstructions;
    ImageView recipeShare;
    ImageView recipeFavorite;
    static int isFavorite;
    private AsyncTaskDelegate thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeTitle = findViewById(R.id.recipe_title);
        recipeTitle.setText(getIntent().getStringExtra(RECIPE_TITLE_KEY));

        recipeReadyInMinutes = findViewById(R.id.recipe_ready_in_minutes);
        String minutes = String.valueOf(getIntent().getIntExtra(RECIPE_READY_IN_MINUTES_KEY, 0));
        String cookingTime = minutes + "MIN";
        recipeReadyInMinutes.setText(cookingTime);

        recipeServings = findViewById(R.id.recipe_servings);
        recipeServings.setText(String.valueOf(getIntent().getIntExtra(RECIPE_SERVINGS_KEY, 0)));

        recipeInstructions = findViewById(R.id.recipe_instructions_text);
        recipeInstructions.setText(getIntent().getStringExtra(RECIPE_INSTRUCTION_KEY));

        recipeFavorite = findViewById(R.id.recipe_favorite);

        recipeFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getInstance(context);
                if (isFavorite == 0){
                    recipeFavorite.setImageResource(R.drawable.ic_turned_in_black_24dp);
                    UpdateFavoriteAsyncTask updateFavoriteAsyncTask = new UpdateFavoriteAsyncTask(1, getIntent().getIntExtra(RECIPE_ID_KEY, 0));
                    updateFavoriteAsyncTask.setDatabase(db);
                    updateFavoriteAsyncTask.execute();
                    isFavorite = 1;
                }else {
                    recipeFavorite.setImageResource(R.drawable.ic_turned_in_not_black_24dp);
                    UpdateFavoriteAsyncTask updateFavoriteAsyncTask = new UpdateFavoriteAsyncTask(0, getIntent().getIntExtra(RECIPE_ID_KEY, 0));
                    updateFavoriteAsyncTask.setDatabase(db);
                    updateFavoriteAsyncTask.execute();
                    isFavorite = 0;
                }
            }
        });

        recipeShare = findViewById(R.id.recipe_share);

        recipeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_phrase) + getIntent().getStringExtra(RECIPE_SOURCE_URL_KEY));
                intent.setType("text/plain");
                context.startActivity(intent);
            }
        });

        recipeImage = findViewById(R.id.recipe_image);
        String imageUrl = getIntent().getStringExtra(RECIPE_IMAGE_KEY);
        Glide.with(this).load(imageUrl).into(recipeImage);

        recipeIngredients = findViewById(R.id.recipe_ingredients_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeIngredients.setLayoutManager(layoutManager);

        final RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter();

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        String recipeId = String.valueOf(getIntent().getIntExtra(RECIPE_ID_KEY, 0));

        String url = "https://api.spoonacular.com/recipes/"+recipeId+"/ingredientWidget.json?apiKey=027d674ca3864b91a48ebc30422c32af";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GetRecipeIngredientsbyIDResponse getRecipeIngredientsbyIDResponse = gson.fromJson(response, GetRecipeIngredientsbyIDResponse.class);
                List<Ingredient> recipeIngredientList = getRecipeIngredientsbyIDResponse.getIngredients();

                recipeIngredientsAdapter.setData(recipeIngredientList);
                recipeIngredients.setAdapter(recipeIngredientsAdapter);

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

        Intent intent = getIntent();

        AppDatabase db = AppDatabase.getInstance(context);

        GetRecipeByIdAsyncTask getRecipeByIdAsyncTask = new GetRecipeByIdAsyncTask();
        getRecipeByIdAsyncTask.setDatabase(db);
        getRecipeByIdAsyncTask.setDelegate(thisActivity);
        getRecipeByIdAsyncTask.execute(intent);
    }

    @Override
    public void handleTaskResult(Recipe recipe) {
        isFavorite = recipe.getIsFavorite();
        if (isFavorite == 0){
            recipeFavorite.setImageResource(R.drawable.ic_turned_in_not_black_24dp);
        }else {
            recipeFavorite.setImageResource(R.drawable.ic_turned_in_black_24dp);
        }
    }
}
