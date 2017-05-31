package com.olife.o_life.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 推荐商品 的自定义列表 扩展 了子项的空间
 * Created by wuguofei on 2016/12/6.
 */

public class ReGoddsListView extends ListView {

    public ReGoddsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReGoddsListView(Context context) {
        super(context);
    }

    public ReGoddsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
