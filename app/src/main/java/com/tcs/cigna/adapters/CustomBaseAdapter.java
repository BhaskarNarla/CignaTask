package com.tcs.cigna.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tcs.cigna.R;
import com.tcs.cigna.pojo.Episodes;

import java.util.List;

/**
 * Created by ADMIN on 1/12/2016.
 */
public class CustomBaseAdapter extends BaseAdapter {
    private final Activity mContext;
    private final List<Episodes> mList;

    public CustomBaseAdapter(Activity context, List<Episodes> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_row, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.mEpisodeNameTV.setText(mList.get(position).getTitle());
        return v;
    }

    class ViewHolder {
        public final TextView mEpisodeNameTV;
        public ViewHolder(View base) {
            mEpisodeNameTV = (TextView) base.findViewById(R.id.episode_name);
        }
    }
}
