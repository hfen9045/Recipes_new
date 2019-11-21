package unsw.infs.jingdianli.recipes.db.getrecipebyid;

import unsw.infs.jingdianli.recipes.models.Recipe;

public interface AsyncTaskDelegate {
    void handleTaskResult(Recipe recipe);
}
