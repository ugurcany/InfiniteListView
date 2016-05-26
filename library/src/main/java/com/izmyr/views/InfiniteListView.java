package com.izmyr.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                infiniteListAdapter.onRefresh();

            }
        });

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setFooterDividersEnabled(false);

        //XML CONFIG
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

    public void init(InfiniteListAdapter<T> infiniteListAdapter, final View loadingView){

        this.infiniteListAdapter = infiniteListAdapter;
        listView.setAdapter(infiniteListAdapter);

        this.loadingView = loadingView;

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
        infiniteListAdapter.addNewItem(newItem);
    }

    public void clearList(){
        hasMore = false;
        infiniteListAdapter.clearList();
    }

    public void startLoading(){
        loading = true;
        if(!swipeRefreshLayout.isRefreshing()) {
            listView.addFooterView(loadingView);
        }
    }

    public void stopLoading(){
        if(!swipeRefreshLayout.isRefreshing()) {
            listView.removeFooterView(loadingView);
        }
        swipeRefreshLayout.setRefreshing(false);
        loading = false;
    }

    public void hasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

}
