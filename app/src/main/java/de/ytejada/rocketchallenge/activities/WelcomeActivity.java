package de.ytejada.rocketchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.ytejada.rocketchallenge.R;

/**
 * A simple welcome activity displaying a welcome text and a log. When touching the screen
 * {@link SearchResultActivity} will be launched, whit no sorting type selected.
 */
public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a basic layout of a welcome text and and image, which
     * reacts to click events, starting a {@link SearchResultActivity}
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

            //Handle click events o root layout. Show toast to user and start search activity.
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(container.getContext(), "Starting search...", Toast.LENGTH_SHORT).show();
                    final Intent i = new Intent(getActivity(), SearchResultActivity.class);
                    startActivity(i);
                }
            });
            return rootView;
        }
    }
}
