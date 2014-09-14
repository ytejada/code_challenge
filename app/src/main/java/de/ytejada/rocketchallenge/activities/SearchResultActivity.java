package de.ytejada.rocketchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.util.List;

import de.ytejada.rocketchallenge.R;
import de.ytejada.rocketchallenge.content.adapter.ClothesItem;
import de.ytejada.rocketchallenge.content.adapter.provider.ContentProviderListener;
import de.ytejada.rocketchallenge.content.adapter.provider.ProductProvider;
import de.ytejada.rocketchallenge.fragments.SearchFragment;
import de.ytejada.rocketchallenge.fragments.SearchResultFragment;


/**
 * Activity containing 2 fragments. One providing search/sorting capabilities and controls,
 * and another one which will present the search result through a list.
 * Interaction
 */
public class SearchResultActivity extends ActionBarActivity implements ContentProviderListener,
        SearchFragment.OnSearchChangeListener, SearchResultFragment.ResultFragmentInteraction {


    private ProductProvider mProvider = new ProductProvider();
    private SearchResultFragment mSearchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_seearch_result);
        if (mSearchResultFragment == null) {
            mSearchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.search_result_list_fragment);
        }

        //Initialize Robospice service
        mProvider.setup(this);
        mProvider.addContentProviderListener(this);
        mProvider.sendRequest(SearchFragment.SortingType.NONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Destroy Robospice service
        mProvider.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seearch_result, menu);
        return true;
    }

    @Override
    public void onNewContent(String requestId, List<ClothesItem> newContent) {
        if (mSearchResultFragment != null) {
            mSearchResultFragment.onResultUptdate(newContent);
        }
    }

    @Override
    public void onError(String requestId, String message) {

    }

    @Override
    public void onSearch(SearchFragment.SortingType sortingType) {
        Toast.makeText(this, "Changing sorting to " + sortingType, Toast.LENGTH_SHORT).show();
        mProvider.sendRequest(sortingType);
    }

    @Override
    public void onItemSelected(int position, ClothesItem data) {
        //Some better preview for the selected data should be implemented here
        Log.d("SearchResultActivity", "Selected item at position :" + position + " selected data " + data);
    }

}
