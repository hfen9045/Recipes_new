package unsw.infs.jingdianli.recipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.models.Ingredient;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientViewHolder> {
    private List<Ingredient> ingredientToAdapter;

    public void setData(List<Ingredient> ingredientToAdapter){
        this.ingredientToAdapter = ingredientToAdapter;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chunk_ingredient, parent, false);

        RecipeIngredientsAdapter.RecipeIngredientViewHolder recipeIngredientViewHolder = new RecipeIngredientsAdapter.RecipeIngredientViewHolder(view);
        return recipeIngredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        final Ingredient ingredientAtPosition = ingredientToAdapter.get(position);

        holder.bind(ingredientAtPosition);
    }

    @Override
    public int getItemCount() {
        return ingredientToAdapter.size();
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView ingredientName;
        TextView ingredientMetricValue;
        TextView ingredientMetricUnit;
        TextView ingredientUsValue;
        TextView ingredientUsUnit;

        public RecipeIngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ingredientName = itemView.findViewById(R.id.ingredient_name_text);
            ingredientMetricValue = itemView.findViewById(R.id.ingredient_metric_value_text);
            ingredientMetricUnit = itemView.findViewById(R.id.ingredient_metric_unit_text);
            ingredientUsValue = itemView.findViewById(R.id.ingredient_us_value_text);
            ingredientUsUnit = itemView.findViewById(R.id.ingredient_us_unit_text);
        }

        public void bind(final Ingredient ingredient){
            ingredientName.setText(ingredient.getName());
            ingredientMetricValue.setText(String.valueOf(ingredient.getAmount().getMetric().getValue()));
            ingredientMetricUnit.setText(ingredient.getAmount().getMetric().getUnit());
            ingredientUsValue.setText(String.valueOf(ingredient.getAmount().getUs().getValue()));
            ingredientUsUnit.setText(ingredient.getAmount().getUs().getUnit());
        }
    }
}
