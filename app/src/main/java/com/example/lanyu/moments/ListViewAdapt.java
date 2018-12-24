package com.example.lanyu.moments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.User;

/**
 * Created by lanyu on 2018/12/13.
 */
public class ListViewAdapt extends ArrayAdapter{

    private int resourceId;
    public ListViewAdapt(Context context, int resource,List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = (User)getItem(position);
        ListLayout listLayout = new ListLayout();
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            listLayout.usernameView = (TextView) view.findViewById(R.id.username);
            listLayout.contentView = (TextView) view.findViewById(R.id.content);
            listLayout.imgView = (ImageView) view.findViewById(R.id.img);
            view.setTag(listLayout);
        } else {
            view = convertView;
            listLayout = (ListLayout) view.getTag();
        }
        listLayout.usernameView.setText(user.getUsername());
        listLayout.contentView.setText(user.getContent());
        listLayout.imgView.setImageResource(user.getImg());
        return view;
    }
    class ListLayout {
        private TextView usernameView;
        private TextView contentView;
        private ImageView imgView;
    }
}
