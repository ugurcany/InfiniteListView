package com.softw4re.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by ugurcan.yildirim on 11.03.2016.
 */
public class InfiniteListView<T> extends FrameLayout {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    private boolean loading = false;
    private View loadingView;

    private boolean hasMore = false;

    private InfiniteListAdapter infiniteListAdapter;

    public InfiniteListView(Context context) {
        super(context);
        this.init(context, null);
    }

    public InfiniteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public InfiniteListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View view = inflate(context, R.layout.infinitelistview, this);

        this.loadingView = LayoutInflater.from(context).inflate(R.layout.item_loading, null, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                infiniteListAdapter.onRefresh();

            }
        });

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setFooterDividersEnabled(false);

        //ATTR CONFIG
        if(attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InfiniteListView, 0, 0);

            try {
                //SWIPE-REFRESH INDICATOR COLOR
                int swipeRefreshIndicatorColor = typedArray.getColor(R.styleable.InfiniteListView_swipeRefreshIndicatorColor, context.getResources().getColor(android.R.color.black));
                swipeRefreshLayout.setColorSchemeColors(swipeRefreshIndicatorColor);

                //SCROLLBAR VISIBILITY
                boolean scrollbarVisible = typedArray.getBoolean(R.styleable.InfiniteListView_scrollbarVisible, true);
                listView.setVerticalScrollBarEnabled(scrollbarVisible);

                //DIVIDER VISIBILITY
                boolean dividerVisible = typedArray.getBoolean(R.styleable.InfiniteListView_dividerVisible, true);
                if(!dividerVisible) {
                    listView.setDividerHeight(0);
                }

            } finally {
                typedArray.recycle();
            }
        }

    }

    public void setAdapter(InfiniteListAdapter<T> infiniteListAdapter){

        this.infiniteListAdapter = infiniteListAdapter;
        listView.setAdapter(infiniteListAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!hasMore)
                    return;

                int lastVisibleItem = visibleItemCount + firstVisibleItem;
                if (lastVisibleItem >= totalItemCount && !loading) {

                    InfiniteListView.this.infiniteListAdapter.onNewLoadRequired();

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InfiniteListView.this.infiniteListAdapter.onItemClick(position);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                InfiniteListView.this.infiniteListAdapter.onItemLongClick(position);

                return true;
            }
        });

    }

    public void addNewItem(T newItem){
        infiniteListAdapter.addNewItem(listView, newItem);
    }

    public void addAll(List<T> newItems){
        infiniteListAdapter.addAll(listView, newItems);
    }

    public void clearList(){
        hasMore = false;
        infiniteListAdapter.clearList(listView);
    }

    public void startLoading(){
        //IF FOOTER ALREADY EXISTS, REMOVE IT
        if(listView.getFooterViewsCount() > 0) {
            listView.removeFooterView(loadingView);
        }

        loading = true;

        if(!swipeRefreshLayout.isRefreshing() && listView.getFooterViewsCount() == 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                listView.addFooterView(loadingView, null, false);
            }
            else{
                listView.setAdapter(null);
                listView.addFooterView(loadingView, null, false);
                listView.setAdapter(infiniteListAdapter);
                listView.setSelection(infiniteListAdapter.getCount() - 1);
            }
        }
    }

    public void stopLoading(){
        if(listView.getFooterViewsCount() > 0) {
            listView.removeFooterView(loadingView);
        }
        swipeRefreshLayout.setRefreshing(false);
        loading = false;
    }

    public void hasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

}
