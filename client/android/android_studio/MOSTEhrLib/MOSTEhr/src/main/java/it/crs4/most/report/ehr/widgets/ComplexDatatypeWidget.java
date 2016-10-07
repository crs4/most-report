package it.crs4.most.report.ehr.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.EhrDatatype;

public abstract class ComplexDatatypeWidget<T extends EhrDatatype> extends DatatypeWidget<T> {
    protected boolean mWidgetsVisible = false;
    protected TextView mTitleText;
    protected ToolTipView mToolTipView;
    protected ToolTipRelativeLayout mToolTipLayout;
    protected LinearLayout mWidgetsContainer;

    public ComplexDatatypeWidget(WidgetProvider provider, String name, T datatype, int parentIndex) {
        super(provider, name, datatype, parentIndex);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(getLayoutRes(), null);
        mWidgetsContainer = (LinearLayout) mRootView.findViewById(R.id.widgets_container);

        ImageView info = (ImageView) mRootView.findViewById(R.id.image_info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceTooltip();
            }
        });

        mTitleText = (TextView) mRootView.findViewById(R.id.txt_title);
        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWidgetsVisible) {
                    removeWidgets();
                }
                else {
                    addWidgets();
                }
            }
        });
        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);
        updateLabelsContent();
    }

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

    protected void addWidgets() {
        mWidgetsVisible = true;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_less_white, 0);
    }

    protected void removeWidgets() {
        mWidgetsContainer.removeAllViews();
        mWidgetsVisible = false;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_white, 0);
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(T datatype) {
    }


    protected abstract int getLayoutRes();

}
