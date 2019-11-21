package unsw.infs.jingdianli.recipes.models;

import java.util.List;

public class GetAllRecipesResponse {
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
