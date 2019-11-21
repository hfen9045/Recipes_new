package unsw.infs.jingdianli.recipes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import unsw.infs.jingdianli.recipes.models.GetSearchResultsResponse.SearchResult;
import unsw.infs.jingdianli.recipes.models.Recipe;

@Database(entities = {Recipe.class}, version = 3, exportSchema = false)  // Replace "Book.class" with whatever your Book entity class is.
//@TypeConverters({ArrayTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();// Replace BookDao with whatever you name your DAO

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "recipesDb")
                    //.allowMainThreadQueries()   // <== IMPORTANT TO NOTE:
                    //     This is NOT correct to do in a completed app.
                    //     Next week we will fix it, but for now this
                    //     line is necessary for the app to work.
                    //     This line will basically allow the database
                    //     queries to freeze the app.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
