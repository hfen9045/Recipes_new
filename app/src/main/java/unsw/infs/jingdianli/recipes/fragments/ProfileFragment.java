package unsw.infs.jingdianli.recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.adapters.RecipesAdapter;
import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.getrecipebyisfavorite.AsyncTaskDelegate;
import unsw.infs.jingdianli.recipes.db.getrecipebyisfavorite.GetRecipeByIsFavoriteAsyncTask;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class ProfileFragment extends Fragment implements AsyncTaskDelegate {
    RecyclerView favoritesRecyclerView;
    private AsyncTaskDelegate thisFragment = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        favoritesRecyclerView = view.findViewById(R.id.favorite_recipes_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 3);
        favoritesRecyclerView.setLayoutManager(layoutManager);

        AppDatabase db = AppDatabase.getInstance(context);

        GetRecipeByIsFavoriteAsyncTask getRecipeByIsFavoriteAsyncTask = new GetRecipeByIsFavoriteAsyncTask();
        getRecipeByIsFavoriteAsyncTask.setDatabase(db);
        getRecipeByIsFavoriteAsyncTask.setDelegate(thisFragment);
        getRecipeByIsFavoriteAsyncTask.execute();

        return view;
    }

    @Override
    public void handleTaskResult(List<Recipe> recipeList) {
        RecipesAdapter recipesAdapter = new RecipesAdapter();
        recipesAdapter.setData(recipeList);
        favoritesRecyclerView.setAdapter(recipesAdapter);
    }
}
