package unsw.infs.jingdianli.recipes.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import unsw.infs.jingdianli.recipes.models.Recipe;

@Dao
public interface RecipeDao {
    @Insert
    void insertRecipes(Recipe recipe);

    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe getRecipeById(int id);

    @Query("UPDATE recipe SET isFavorite=:isFavorite WHERE id = :id")
    void markFavorite(int isFavorite, int id);

    @Query("SELECT * FROM recipe WHERE isFavorite = :favoriteFlag")
    List<Recipe> getRecipeByIsFavorite(int favoriteFlag);
}
