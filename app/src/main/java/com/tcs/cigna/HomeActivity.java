package com.tcs.cigna;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tcs.cigna.fragments.DetailFragment;
import com.tcs.cigna.fragments.ListFragment;

/**
 * Created by ADMIN on 1/12/2016.
 */
public class HomeActivity extends AppCompatActivity implements ListFragment.Callbacks{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListFragment mFragment = new ListFragment();
        setActionBarTitle(getString(R.string.app_name));
        fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame, mFragment, "List");
        fragmentTransaction.commit();
    }

    public void setActionBarTitle(String title){
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ED")));
        mActionBar.setTitle(title);
    }


    @Override
    public void onItemSelected(String ImdbID,String title) {

        setActionBarTitle(title);
        Bundle arguments = new Bundle();
        arguments.putString(DetailFragment.IMDB_ID, ImdbID);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "Details");
        fragmentTransaction.addToBackStack("Details");
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0) {
            fragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
