package com.izmyr.infinitelistview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.izmyr.views.InfiniteListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int ITEM_COUNT_LIMIT = 200;
    private final int TIME_TO_LOAD = 1500; //in ms

    private int itemOffset = 0;
    private int itemCount = 25;

    @BindView(R.id.infiniteListView)
    InfiniteListView infiniteListView;

    private View loadingView;
    private ArrayList<String> itemList;
    private MyInfiniteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        itemList = new ArrayList<>();
        adapter = new MyInfiniteListAdapter(this, R.layout.item_text, itemList);

        loadingView = getLayoutInflater().inflate(R.layout.item_loading, null);

        infiniteListView.init(adapter, loadingView);

        refreshList();
    }

    //SIMULATES ITEM LOADING
    public void loadNewItems() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                infiniteListView.startLoading();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(TIME_TO_LOAD);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                if(itemOffset >= ITEM_COUNT_LIMIT) {
                    infiniteListView.setEndOfLoading(true);
                }
                else {
                    //ADD NEW ITEMS TO LIST
                    for (int i = itemOffset; i < itemOffset + itemCount; i++) {
                        String item = "Item #" + i;
                        infiniteListView.addNewItem(item);
                    }
                    itemOffset += itemCount;
                    Log.d("InfiniteListView", itemOffset + " " + itemCount);
                }

                infiniteListView.stopLoading();
            }
        }.execute();
    }

    //DO THIS ON SWIPE-REFRESH
    public void refreshList() {
        itemOffset = 0;
        infiniteListView.setEndOfLoading(false);
        infiniteListView.clearList(); //TRIGGERS ONNEWLOADREQUIRED
    }

    //DO THIS ON ITEM CLICK
    public void clickItem(final int position) {
        Snackbar.make(infiniteListView, "Item clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

    //DO THIS ON ITEM LONG-CLICK
    public void longClickItem(int position) {
        Snackbar.make(infiniteListView, "Item long-clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

}
