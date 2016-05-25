package com.izmyr.views;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public abstract class InfiniteListAdapter<T> extends ArrayAdapter<T> {

    private ArrayList<T> itemList;

    public InfiniteListAdapter(Activity activity, int itemLayoutRes, ArrayList<T> itemList) {

        super(activity, itemLayoutRes, itemList);

        this.itemList = itemList;

    }

    public abstract void onNewLoadRequired();

    public abstract void onRefresh();

    public abstract void onItemClick(int position);

    public abstract void onItemLongClick(int position);

    protected final void addNewItem(T newItem){
        itemList.add(newItem);
        this.notifyDataSetChanged();
    }

    protected final void clearList(){
        itemList.clear();
        this.notifyDataSetChanged();
    }

}