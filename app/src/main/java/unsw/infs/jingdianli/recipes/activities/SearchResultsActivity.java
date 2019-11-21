package unsw.infs.jingdianli.recipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.adapters.SearchResultAdapter;
import unsw.infs.jingdianli.recipes.db.AppDatabase;
import unsw.infs.jingdianli.recipes.models.GetSearchResultsResponse;

import static unsw.infs.jingdianli.recipes.fragments.SearchFragment.SEARCH_REQUEST;

public class SearchResultsActivity extends AppCompatActivity {
    Context context = this;

    RecyclerView searchResultsRecyclerView;
    private List<GetSearchResultsResponse.SearchResult> searchResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResultsRecyclerView = findViewById(R.id.search_results_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        searchResultsRecyclerView.setLayoutManager(layoutManager);

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        String searchRequest = getIntent().getStringExtra(SEARCH_REQUEST);

        String url = "https://api.spoonacular.com/recipes/search?query=" + searchRequest + "&apiKey=027d674ca3864b91a48ebc30422c32af";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GetSearchResultsResponse getSearchResultsResponse = gson.fromJson(response, GetSearchResultsResponse.class);
                searchResultsList = getSearchResultsResponse.getSearchResultList();

//                GetSearchResultsResponse.SearchResult[] searchResultArray = new GetSearchResultsResponse.SearchResult[searchResultsList.size()];
//                for (int i = 0; i < searchResultsList.size(); i++){
//                    searchResultArray[i] = searchResultsList.get(i);
//                }

                SearchResultAdapter searchResultAdapter = new SearchResultAdapter();
                searchResultAdapter.setData(searchResultsList);
                searchResultsRecyclerView.setAdapter(searchResultAdapter);

                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        requestQueue.add(stringRequest);
    }
}
