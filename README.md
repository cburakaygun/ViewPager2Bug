### About the Project

This project contains an Activity which hosts a ViewPager2 and some buttons.

There are 3 pages on ViewPager and its `offscreenPageLimit` is set to 2.

`Page {0,1,2}` buttons select the page with position {0,1,2} on ViewPager
via `setCurrentItem({0,1,2}, /* smoothScroll = */ false)` method (`false` is the key factor for the bug).

`Reset ViewPager Adapter` button assigns ViewPager a new instance of a pager Adapter. (`viewPager.adapter = ...`)

Each page of the ViewPager is a simple `Fragment` which only contains a TextView and prints the position in the ViewPager.




### The Bug (via Logcat Outputs)

1. Activity starts (Page0 becomes visible)
```
... D/ViewPager2Bug: PagerFragment (0) | onCreate
... D/ViewPager2Bug: PagerFragment (0) | onStart
... D/ViewPager2Bug: PagerFragment (0) | onResume

... D/ViewPager2Bug: PagerFragment (1) | onCreate
... D/ViewPager2Bug: PagerFragment (1) | onStart

... D/ViewPager2Bug: PagerFragment (2) | onCreate
... D/ViewPager2Bug: PagerFragment (2) | onStart
```

2. "Page 1" button gets clicked (Page1 becomes visible)
```
... D/ViewPager2Bug: MainActivity | Button1 Click
... D/ViewPager2Bug: PagerFragment (0) | onPause
... D/ViewPager2Bug: PagerFragment (1) | onResume
```

3. "Reset Viewpager Adapter" button gets clicked (Page0 becomes visible)
```
... D/ViewPager2Bug: MainActivity | ButtonResetAdapter Click

... D/ViewPager2Bug: PagerFragment (2) | onStop
... D/ViewPager2Bug: PagerFragment (2) | onDestroy

... D/ViewPager2Bug: PagerFragment (0) | onStop
... D/ViewPager2Bug: PagerFragment (0) | onDestroy

... D/ViewPager2Bug: PagerFragment (1) | onPause
... D/ViewPager2Bug: PagerFragment (1) | onStop
... D/ViewPager2Bug: PagerFragment (1) | onDestroy

... D/ViewPager2Bug: PagerFragment (0) | onCreate
... D/ViewPager2Bug: PagerFragment (0) | onStart
... D/ViewPager2Bug: PagerFragment (0) | onResume

... D/ViewPager2Bug: PagerFragment (1) | onCreate
... D/ViewPager2Bug: PagerFragment (1) | onStart

... D/ViewPager2Bug: PagerFragment (2) | onCreate
... D/ViewPager2Bug: PagerFragment (2) | onStart
```

4. "Page 1" button gets clicked (Page1 becomes visible)
```
... D/ViewPager2Bug: MainActivity | Button1 Click
```

At this point, even though Page1 becomes visible, its `onResume` callback is not called.
(Also, `onPause` callback of Page0 is not called.)

There is an instance of `ScrollEventAdapter` in ViewPager2 which takes part in handling programmatic scroll.
When we assign a new adapter to ViewPager2, it seems like `ScrollEventAdapter` is not being reset properly.
In this case, it still thinks we are on page1 after the new adapter is assigned.
Hence, when page1 is selected again programmatically, it does not invoke related listeners/callbacks.

**NOTE:** This problem is not encountered when the page is selected by swiping on the ViewPager,
or scrollig programatically with smooth scroll enabled (i.e., `setcurrentItem(<position>, true)`).
