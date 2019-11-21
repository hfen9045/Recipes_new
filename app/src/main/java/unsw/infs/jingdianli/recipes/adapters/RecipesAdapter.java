package unsw.infs.jingdianli.recipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.activities.RecipeDetailsActivity;
import unsw.infs.jingdianli.recipes.models.Recipe;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    public static final String RECIPE_ID_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_id";
    public static final String RECIPE_IMAGE_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_image";
    public static final String RECIPE_INSTRUCTION_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_instruction";
    public static final String RECIPE_READY_IN_MINUTES_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_ready_in_minutes";
    public static final String RECIPE_SERVINGS_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_servings";
    public static final String RECIPE_TITLE_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_title";
    public static final String RECIPE_SOURCE_URL_KEY = "unsw.infs.jingdianli.recipes.adapters.recipe_source_url";
    private List<Recipe> recipeToAdapter;

    public void setData(List<Recipe> recipeToAdapter){
        this.recipeToAdapter = recipeToAdapter;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chunk_recipe, parent, false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipeAtPosition = recipeToAdapter.get(position);

        holder.bind(recipeAtPosition);
    }

    @Override
    public int getItemCount() {
        return recipeToAdapter.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView recipeImage;
        TextView recipeTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            recipeImage = itemView.findViewById(R.id.chunk_recope_image);
            recipeTitle = itemView.findViewById(R.id.chunk_recipes_title);
        }

        public void bind(final Recipe recipe){
            final String imageUrl = recipe.getImage();
            Glide.with(view.getContext()).load(imageUrl).into(recipeImage);

            recipeTitle.setText(recipe.getTitle());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra(RECIPE_ID_KEY, recipe.getId());
                    intent.putExtra(RECIPE_IMAGE_KEY, recipe.getImage());
                    intent.putExtra(RECIPE_INSTRUCTION_KEY, recipe.getInstructions());
                    intent.putExtra(RECIPE_READY_IN_MINUTES_KEY, recipe.getReadyInMinutes());
                    intent.putExtra(RECIPE_SERVINGS_KEY, recipe.getServings());
                    intent.putExtra(RECIPE_TITLE_KEY, recipe.getTitle());
                    intent.putExtra(RECIPE_SOURCE_URL_KEY, recipe.getSourceUrl());
                    context.startActivity(intent);
                }
            });
        }
    }
}
