package com.acta.acta.app.Other;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wareja on 16. 12. 9.
 *
 *This class comes in hand when the user is scrolling through the story
 * The floating action bar disappear when the user is scrolling through his stories*/

public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior{

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,FloatingActionButton child,View directTargetChild, View target, int nestedScrollAxes)
    {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child,directTargetChild,target,nestedScrollAxes);
    }

    /** This method check for the Y position and determine whether to animate or not*/
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }
    //just for enabling layout inflation
    public ScrollAwareFabBehavior(Context context, AttributeSet attrs)
    {
        super();
    }
}
