package de.ytejada.rocketchallenge.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.ytejada.rocketchallenge.R;

/**
 * A {@link android.app.Fragment} subclass, which displays controls for selecting sorting types.
 * Interaction with this fragment it´s done through the methods declared in {@link OnSearchChangeListener#onSearch(de.ytejada.rocketchallenge.fragments.SearchFragment.SortingType)},
 * which request a search with the given sorting type.
 */
public class SearchFragment extends android.support.v4.app.Fragment {


    private OnSearchChangeListener mListener;
    private Button mSortByNameButton;
    private Button mSortByBrandButton;
    private Button mSortByPopularity;
    private Button mSortByPrice;


    public enum SortingType {

        BRAND,
        POPULARITY,
        NAME,
        PRICE,
        NONE
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup fullLayout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        mSortByNameButton = (Button) fullLayout.findViewById(R.id.sort_name_button);
        mSortByBrandButton = (Button) fullLayout.findViewById(R.id.sort_brand_button);
        mSortByPopularity = (Button) fullLayout.findViewById(R.id.sort_popularity_button);
        mSortByPrice = (Button) fullLayout.findViewById(R.id.sort_price_button);
        return fullLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSearchChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {

                    //By Default
                    final SortingType searchType;
                    if (v.getId() == mSortByBrandButton.getId()) {
                        searchType = SortingType.BRAND;
                    } else if (v.getId() == mSortByPopularity.getId()) {
                        searchType = SortingType.POPULARITY;
                    } else if (v.getId() == mSortByNameButton.getId()) {
                        searchType = SortingType.NAME;
                    } else if (v.getId() == mSortByPrice.getId()) {
                        searchType = SortingType.PRICE;
                    } else {
                        searchType = SortingType.NONE;
                        Log.e("SearchFragment", " Unknown sorting type selected");
                    }
                    mListener.onSearch(searchType);
                }

            }
        };

        mSortByBrandButton.setOnClickListener(listener);
        mSortByNameButton.setOnClickListener(listener);
        mSortByPopularity.setOnClickListener(listener);
        mSortByPrice.setOnClickListener(listener);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSearchChangeListener {
        /*
         *Callback for changing the search/sorting type. This call will be used for fragment interaction with the attached Activity
         */
        public void onSearch(SortingType sortingType);
    }

}
