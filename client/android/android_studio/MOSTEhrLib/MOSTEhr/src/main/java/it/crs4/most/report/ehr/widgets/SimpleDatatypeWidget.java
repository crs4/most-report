package it.crs4.most.report.ehr.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.EhrDatatype;

public abstract class SimpleDatatypeWidget<T extends EhrDatatype> extends DatatypeWidget<T> {
    protected ToolTipRelativeLayout mToolTipLayout;
    protected TextView mTitleText;
    protected ToolTipView mToolTipView;

    public SimpleDatatypeWidget(WidgetProvider provider, String name, T datatype, int parentIndex) {
        super(provider, name, datatype, parentIndex);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(getLayoutResource(), null);
        mTitleText = (TextView) mRootView.findViewById(R.id.txt_title);
        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);

        ImageView info = (ImageView) mRootView.findViewById(R.id.image_info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceTooltip();
            }
        });
    }

    public abstract int getLayoutResource();

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#replaceTooltip()
     */
    @Override
    protected void replaceTooltip() {
        if (mToolTipView == null) {
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTitleText);
        }
        else {
            mToolTipView.remove();
            mToolTipView = null;
        }
    }
}
