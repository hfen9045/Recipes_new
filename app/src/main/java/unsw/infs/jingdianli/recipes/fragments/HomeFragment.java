package unsw.infs.jingdianli.recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.adapters.RecipesAdapter;
import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.insertrecipes.AsyncTaskDelegate;
import unsw.infs.jingdianli.recipes.db.insertrecipes.InsertRecipesAsyncTask;
import unsw.infs.jingdianli.recipes.models.GetAllRecipesResponse;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class HomeFragment extends Fragment implements AsyncTaskDelegate {
    RecyclerView recipesRecyclerView;
    private List<Recipe> recipeList;
    private AsyncTaskDelegate thisFragment = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recipesRecyclerView = view.findViewById(R.id.home_recipes_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        recipesRecyclerView.setLayoutManager(layoutManager);

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

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
                insertRecipesAsyncTask.setDelegate(thisFragment);
                insertRecipesAsyncTask.execute(recipeArray);

                RecipesAdapter recipesAdapter = new RecipesAdapter();
                recipesAdapter.setData(recipeList);
                recipesRecyclerView.setAdapter(recipesAdapter);

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

        return view;
    }

    @Override
    public void handleTaskResult() {

    }
}
