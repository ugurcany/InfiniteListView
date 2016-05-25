package com.izmyr.infinitelistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.izmyr.views.InfiniteListAdapter;

import java.util.ArrayList;

public class MyInfiniteListAdapter<T> extends InfiniteListAdapter<T> {

    private MainActivity mainActivity;
    private int itemLayoutRes;
    private ArrayList<T> itemList;

    public MyInfiniteListAdapter(MainActivity mainActivity, int itemLayoutRes, ArrayList<T> itemList) {

        super(mainActivity, itemLayoutRes, itemList);

        this.mainActivity = mainActivity;
        this.itemLayoutRes = itemLayoutRes;
        this.itemList = itemList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mainActivity.getLayoutInflater().inflate(itemLayoutRes, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String text = (String) itemList.get(position);
        if (text != null) {
            holder.text.setText(text);
        }

        return convertView;
    }

    @Override
    public void onNewLoadRequired() {
        mainActivity.loadNewItems();
    }

    @Override
    public void onRefresh() {
        mainActivity.refreshList();
    }

    @Override
    public void onItemClick(int position) {
        mainActivity.clickItem(position);
    }

    @Override
    public void onItemLongClick(int position) {
        mainActivity.longClickItem(position);
    }


    static class ViewHolder {
        TextView text;
    }

}