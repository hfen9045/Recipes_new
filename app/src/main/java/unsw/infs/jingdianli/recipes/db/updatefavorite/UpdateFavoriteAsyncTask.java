package unsw.infs.jingdianli.recipes.db.updatefavorite;

import android.os.AsyncTask;

import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.db.insertrecipes.AsyncTaskDelegate;

public class UpdateFavoriteAsyncTask extends AsyncTask<Void, Void, Void> {
    int isFavorite;
    int recipeId;

    public UpdateFavoriteAsyncTask(int isFavorite, int recipeId) {
        this.isFavorite = isFavorite;
        this.recipeId = recipeId;
    }

    private AsyncTaskDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        database.recipeDao().markFavorite(isFavorite, recipeId);

        return null;
    }
}
