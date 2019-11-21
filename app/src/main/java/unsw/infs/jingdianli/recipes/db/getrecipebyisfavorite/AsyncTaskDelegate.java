package unsw.infs.jingdianli.recipes.db.getrecipebyisfavorite;

import java.util.List;

import unsw.infs.jingdianli.recipes.models.Recipe;

public interface AsyncTaskDelegate {
    void handleTaskResult(List<Recipe> recipeList);
}
