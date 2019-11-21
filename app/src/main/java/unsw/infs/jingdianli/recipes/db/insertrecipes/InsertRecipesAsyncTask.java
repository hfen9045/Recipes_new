package unsw.infs.jingdianli.recipes.db.insertrecipes;

import android.os.AsyncTask;

import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class InsertRecipesAsyncTask extends AsyncTask<Recipe, Void, Void> {
    private AsyncTaskDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        if (delegate != null){
            for(int i = 0; i < recipes.length; i++){
                if (database.recipeDao().getRecipeById(recipes[i].getId()) == null){
                    database.recipeDao().insertRecipes(recipes[i]);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (delegate != null){
            delegate.handleTaskResult();
        }
    }
}
