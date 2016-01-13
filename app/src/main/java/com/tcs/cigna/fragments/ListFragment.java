package com.tcs.cigna.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tcs.cigna.AppController;
import com.tcs.cigna.HomeActivity;
import com.tcs.cigna.R;
import com.tcs.cigna.utils.Utils;
import com.tcs.cigna.adapters.CustomBaseAdapter;
import com.tcs.cigna.pojo.Episodes;
import com.tcs.cigna.pojo.Season;


/**
 * Created by ADMIN on 1/12/2016.
 */
public class ListFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private final String TAG = "ListFragment";

    private Season mSeason;
    private CustomBaseAdapter mAdapter;
    private ListView mListview;
    private Button mRetryBtn;
    private Callbacks mCallbacks = sDummyCallbacks;
    public ListFragment() {
    }


    public interface Callbacks {
        public void onItemSelected(String id,String title);
    }
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id,String title) {
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach");
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRetryBtn = (Button)view.findViewById(R.id.retryBtn);
        mRetryBtn.setOnClickListener(this);

        mListview = (ListView) view.findViewById(R.id.listview);
        mListview.setOnItemClickListener(this);
        mListview.setEmptyView(view.findViewById(R.id.empty_list));

        stringRequest();
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v==mRetryBtn){
            if(Utils.isNetworkAvailable(getActivity())) {
                stringRequest();
                mRetryBtn.setVisibility(View.GONE);
            }
        }
    }


    /* this method is used to make service call and get list of episoded in mSeason. */
    private void stringRequest(){
        if(Utils.isNetworkAvailable(getActivity())) {
            String url = "http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1";
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Log.i(TAG, response);

                    Gson gson = new Gson();
                    mSeason = gson.fromJson(response, Season.class);
                    mAdapter = new CustomBaseAdapter(getActivity(), mSeason.getEpisodes());
                    mListview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    ((HomeActivity)mCallbacks).setActionBarTitle(mSeason.getTitle());
                    pDialog.hide();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, TAG);
        }else {
            //Toast.makeText(getActivity(), getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            Utils.showAlert(getActivity());
            mRetryBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Episodes episodes = mSeason.getEpisodes().get(position);
        mCallbacks.onItemSelected(episodes.getImdbID(),episodes.getTitle());
    }
}
