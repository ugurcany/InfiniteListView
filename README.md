# InfiniteListView

A custom Android ListView that gets extended at each time new items are loaded by swiping to the bottom of list.

See `app` module for sample usage of **InfiniteListView**.

#### <a name="infinitelistview"></a>InfiniteListView (extends FrameLayout)
- Includes the following UI components:
    - SwipeRefreshLayout
    - ListView
    
- Initialize it as follows:
    - `infiniteListView.init(adapter,loadingView);`
        - `adapter` (**InfiniteListAdapter**)
            - Extend it to create your own adapter
                - Override its `onNewLoadRequired()` method to load new items when required
                - Override its `onRefresh()` method to set what to do on swipe-to-refresh
                - Override its `onItemClick(position)` method to set what to do on item click
                - Override its `onItemLongClick(position)` method to set what to do on item long-click
        - `loadingView` (View)
            - Footer view to be displayed while loading new items
            
- Includes the following methods:
    - `infiniteListView.addNewItem(item);` -> adds new item to list
    - `infiniteListView.clearList();` -> clears entire list (and triggers `onNewLoadRequired()`)
    - `infiniteListView.startLoading();` -> call this before item loading starts
    - `infiniteListView.stopLoading();` -> call this after item loading ends
    - `infiniteListView.setEndOfLoading();` -> call this when there is no more item to load
    
- Custom XML attributes:
    - `swipeRefreshIndicatorColor` (color)
    
    
    
#### <a name="infinitelistadapter"></a>InfiniteListAdapter (abstract class, extends ArrayAdapter)
- Constructor takes the following params:
    - `activity` (Activity)
    - `itemLayoutRes` (int)
        - e.g., `R.layout.item_text`
    - `itemList` (ArrayList)
    
- Includes the following abstract methods
    - `onNewLoadRequired()`
    - `onRefresh()`
    - `onItemClick(position)`
    - `onItemLongClick(position)`
    
    
### <a name="license"></a>License
```
The MIT License (MIT)

Copyright (c) 2016 Ugurcan Yildirim

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```