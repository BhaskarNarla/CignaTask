package com.tcs.cigna.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tcs.cigna.AppController;
import com.tcs.cigna.R;
import com.tcs.cigna.utils.Utils;
import com.tcs.cigna.pojo.EpisodeDetails;

/**
 * Created by ADMIN on 1/12/2016.
 */
public class DetailFragment extends Fragment implements View.OnClickListener{
    private final String TAG = DetailFragment.class.getSimpleName();
    public static final String IMDB_ID = "ImdbID";
    private String mImdbID;
    private EpisodeDetails episodeDetails;
    private Button mRetryBtn;
    private TextView mYearTV;
    private TextView mRatedTV;
    private TextView mReleasedTV;
    private TextView mSeasonTV;
    private TextView mEpisodeTV;
    private TextView mRuntimeTV;
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImdbID = getArguments().getString(IMDB_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        mYearTV = (TextView)view.findViewById(R.id.year);
        mRatedTV = (TextView)view.findViewById(R.id.rated);
        mReleasedTV = (TextView)view.findViewById(R.id.released);
        mSeasonTV = (TextView)view.findViewById(R.id.season);
        mEpisodeTV = (TextView)view.findViewById(R.id.episode);
        mRuntimeTV = (TextView)view.findViewById(R.id.runtime);

        mRetryBtn = (Button)view.findViewById(R.id.retryBtn);
        mRetryBtn.setOnClickListener(this);

        stringRequest();

        return view;
    }

    /* To update UI with updated data.*/
    private void updateUI(){
        mYearTV.setText(episodeDetails.getYear());
        mRatedTV.setText(episodeDetails.getRated());
        mReleasedTV.setText(episodeDetails.getReleased());
        mSeasonTV.setText(episodeDetails.getSeason());
        mEpisodeTV.setText(episodeDetails.getEpisode());
        mRuntimeTV.setText(episodeDetails.getRuntime());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /* this method is used to make service call and get more details of episode. */
    private void stringRequest(){
        if(Utils.isNetworkAvailable(getActivity())) {
            String url = "http://www.omdbapi.com/?i=IMDBID&plot=short&r=json";
            url = url.replace("IMDBID", mImdbID);
            Log.i(TAG, url);
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    Gson gson = new Gson();
                    episodeDetails = gson.fromJson(response, EpisodeDetails.class);
                    updateUI();
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
    public void onClick(View v) {
        if(v==mRetryBtn){
            if(Utils.isNetworkAvailable(getActivity())) {
                stringRequest();
                mRetryBtn.setVisibility(View.GONE);
            }
        }
    }
}
