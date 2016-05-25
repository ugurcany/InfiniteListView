# InfiniteListView
[![](https://jitpack.io/v/ugurcany/InfiniteListView.svg)](https://jitpack.io/#ugurcany/InfiniteListView)
[![API](https://img.shields.io/badge/API-11%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=11)
[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](http://opensource.org/licenses/MIT)

**InfiniteListView** is a custom Android ListView that gets extended at each time new items are loaded by swiping to the bottom of list. It also supports swipe-to-refresh behavior.

See the `app` module for the sample usage of **InfiniteListView**.



## Table of Contents
1. [How it works](#howitworks)
2. [Classes](#classes)
    - [InfiniteListView](#inflistview)
    - [InfiniteListAdapter](#inflistadapter)
3. [How to use](#howtouse)
4. [License](#license)



## <a name="howitworks"></a>How it works
![How it works](https://raw.githubusercontent.com/ugurcany/InfiniteListView/master/gif/infinitelistview2.gif)



## <a name="classes"></a>Classes
#### <a name="inflistview"></a>InfiniteListView (extends FrameLayout)
- Includes the following UI components:
    - SwipeRefreshLayout
    - ListView
- Initialize it as follows:
    - `infiniteListView.init(adapter, loadingView);`
        - `adapter` (InfiniteListAdapter)
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



#### <a name="inflistadapter"></a>InfiniteListAdapter (abstract class, extends ArrayAdapter)
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



## <a name="howtouse"></a>How to use
**Step 1.** Add the JitPack repository in your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

**Step 2.** Add the dependency:
```
dependencies {
    compile 'com.github.ugurcany:InfiniteListView:X.Y.Z'
}
```



## <a name="license"></a>License
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