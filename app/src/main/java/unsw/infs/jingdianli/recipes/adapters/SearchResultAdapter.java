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
import unsw.infs.jingdianli.recipes.activities.SearchResultDetailsActivity;
import unsw.infs.jingdianli.recipes.models.GetSearchResultsResponse.SearchResult;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>{
    public static final String SEARCH_RESULT_ID_KEY = "unsw.infs.jingdianli.recipes.adapters.search_result_id";
    private List<SearchResult> searchResultToAdapter;

    public void setData(List<SearchResult> searchResultToAdapter){
        this.searchResultToAdapter = searchResultToAdapter;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chunk_recipe, parent, false);

        SearchResultViewHolder searchResultViewHolder = new SearchResultViewHolder(view);

        return searchResultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        final SearchResult searchResultAtPosition = searchResultToAdapter.get(position);

        holder.bind(searchResultAtPosition);
    }

    @Override
    public int getItemCount() {
        return searchResultToAdapter.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public ImageView searchResultImage;
        public TextView searchResultTitle;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            searchResultImage = view.findViewById(R.id.chunk_recope_image);
            searchResultTitle = view.findViewById(R.id.chunk_recipes_title);

        }

        public void bind(final SearchResult searchResult){
            final String searchResultId = String.valueOf(searchResult.getId());
            final String imageUrl = "https://spoonacular.com/recipeImages/" + searchResultId + "-556x370.jpg";
            Glide.with(view.getContext()).load(imageUrl).into(searchResultImage);

            searchResultTitle.setText(searchResult.getTitle());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, SearchResultDetailsActivity.class);
                    int searchResultId = searchResult.getId();
                    intent.putExtra(SEARCH_RESULT_ID_KEY, searchResultId);
                    context.startActivity(intent);
                }
            });
        }
    }
}
