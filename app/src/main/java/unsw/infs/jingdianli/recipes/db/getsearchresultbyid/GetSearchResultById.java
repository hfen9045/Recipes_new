package unsw.infs.jingdianli.recipes.db.getsearchresultbyid;

import android.content.Intent;
import android.os.AsyncTask;

import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.getrecipebyid.AsyncTaskDelegate;
import unsw.infs.jingdianli.recipes.models.Recipe;

import static unsw.infs.jingdianli.recipes.adapters.RecipesAdapter.RECIPE_ID_KEY;
import static unsw.infs.jingdianli.recipes.adapters.SearchResultAdapter.SEARCH_RESULT_ID_KEY;

public class GetSearchResultById extends AsyncTask<Intent, Void, Recipe> {
    private AsyncTaskDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Recipe doInBackground(Intent... intents) {
        int id = intents[0].getIntExtra(SEARCH_RESULT_ID_KEY, 0);
        Recipe recipe = database.recipeDao().getRecipeById(id);
        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        if (delegate != null){
            delegate.handleTaskResult(recipe);
        }
    }
}
