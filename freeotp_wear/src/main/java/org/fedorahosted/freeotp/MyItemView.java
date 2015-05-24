package org.fedorahosted.freeotp;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by letroll on 24/05/15.
 */
public final class MyItemView extends FrameLayout implements WearableListView.OnCenterProximityListener {

    final CircledImageView imgView;
    final TextView txtView;
    private float mScale;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;
    private float mDefaultCircleRadius;
    private float mSelectedCircleRadius;

    public MyItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.row_advanced_item_layout, this);
        imgView = (CircledImageView) findViewById(R.id.image);
        txtView = (TextView) findViewById(R.id.text);
        mFadedCircleColor = getResources().getColor(android.R.color.darker_gray);
        mChosenCircleColor = getResources().getColor(android.R.color.holo_blue_dark);
    }

    @Override
    public void onCenterPosition(boolean b) {
        //Animation example to be ran when the view becomes the centered one
        imgView.animate().scaleX(1f).scaleY(1f).alpha(1).setDuration(250);
        txtView.animate().scaleX(1f).scaleY(1f).alpha(1).setDuration(250);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        //Animation example to be ran when the view is not the centered one anymore
        imgView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        txtView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
    }
}