package unsw.infs.jingdianli.recipes.models;

import java.util.List;

public class GetRecipeIngredientsbyIDResponse {
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
