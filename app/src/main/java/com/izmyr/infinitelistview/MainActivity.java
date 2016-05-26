package com.izmyr.infinitelistview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.izmyr.views.InfiniteListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int ITEM_COUNT_TO_LOAD = 25;
    private final int ITEM_COUNT_LIMIT = 200;
    private final int TIME_TO_LOAD = 1500; //in ms

    private int itemOffset = 0;

    private LinearLayout container;
    private InfiniteListView infiniteListView;

    private View loadingView;
    private ArrayList<String> itemList;
    private MyInfiniteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.container);
        infiniteListView = (InfiniteListView) findViewById(R.id.infiniteListView);

        itemList = new ArrayList<>();
        adapter = new MyInfiniteListAdapter(this, R.layout.item_text, itemList);

        loadingView = getLayoutInflater().inflate(R.layout.item_loading, null);

        infiniteListView.init(adapter, loadingView);

        loadNewItems();
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
                    for (int i = itemOffset; i < itemOffset + ITEM_COUNT_TO_LOAD; i++) {
                        String item = "Item #" + i;
                        infiniteListView.addNewItem(item);
                    }
                    itemOffset += ITEM_COUNT_TO_LOAD;
                    Log.d("InfiniteListView", "Current item count = " + itemOffset);

                    infiniteListView.setEndOfLoading(false);
                }

                infiniteListView.stopLoading();
            }
        }.execute();
    }

    //DO THIS ON SWIPE-REFRESH
    public void refreshList() {
        itemOffset = 0;
        infiniteListView.clearList();
        loadNewItems();
    }

    //DO THIS ON ITEM CLICK
    public void clickItem(int position) {
        Snackbar.make(container, "Item clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

    //DO THIS ON ITEM LONG-CLICK
    public void longClickItem(int position) {
        Snackbar.make(container, "Item long-clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

}
