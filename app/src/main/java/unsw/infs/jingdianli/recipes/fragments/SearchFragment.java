package unsw.infs.jingdianli.recipes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.activities.AllRecipesActivity;
import unsw.infs.jingdianli.recipes.activities.SearchResultsActivity;

public class SearchFragment extends Fragment {
    public static final String SEARCH_REQUEST = "unsw.infs.jingdianli.recipes.fragments.search";
    SearchView searchView;
    ConstraintLayout allRecipesConstraintLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search_recipes);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra(SEARCH_REQUEST, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        allRecipesConstraintLayout = view.findViewById(R.id.all_recipes_constraint_layout);
        allRecipesConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllRecipesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
