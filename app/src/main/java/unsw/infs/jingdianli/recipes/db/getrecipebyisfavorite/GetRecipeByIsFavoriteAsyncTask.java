package unsw.infs.jingdianli.recipes.db.getrecipebyisfavorite;

import android.os.AsyncTask;

import java.util.List;

import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class GetRecipeByIsFavoriteAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
    private AsyncTaskDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        List<Recipe> recipeList = database.recipeDao().getRecipeByIsFavorite(1);
        return recipeList;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipeList) {
        if (delegate != null){
            delegate.handleTaskResult(recipeList);
        }
    }
}
