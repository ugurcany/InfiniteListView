package com.softw4re.views;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

    protected final void addNewItem(ListView listView, T newItem){
        itemList.add(newItem);
        this.notifyDataSetChanged();
    }

    protected final void addAll(ListView listView, List<T> newItems){
        itemList.addAll(newItems);
        this.notifyDataSetChanged();
    }

    protected final void clearList(ListView listView){
        itemList.clear();
        this.notifyDataSetChanged();
    }

}