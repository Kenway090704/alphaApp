package com.alpha.alphaapp.ui.widget.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.alpha.lib_sdk.app.unitily.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者： HP 创建于on 2017/7/1.
 * @邮箱：xiaokai090704@126.com
 */
public class WheelView extends View {
    private static final String TAG = "WheelView";

    public static final int FONT_COLOR = Color.BLACK;
    public static final int FONT_COLOR_WHITE = Color.WHITE;

    public static final int FONT_SIZE = 28;
    public static final int PADDING = 10;
    public static final int SHOW_COUNT = 3;
    public static final int SELECT = 0;
    //总体宽度，高度，Item的高度
    private int width;
    private int height;
    private int itemHeight;
    //需要显示的行数
    private int showCount = SHOW_COUNT;
    //当前默认选择的位置
    private int select = SELECT;
    //字体颜色，大小，补白
    private int fontColor = FONT_COLOR;
    private int fontSize = FONT_SIZE;
    private int padding = PADDING;
    //文本列表
    private List<String> lists;
    //选中项的辅助文本，可为空
    private String selectTip;
    //每一项Item和选中项
    private List<WheelItem> wheelItems = new ArrayList<>();
    private WheelSelect wheelSelect = null;
    //手点击的Y坐标
    private float mTouchY;
    /**
     * 设置是否选中中间位置
     */
    private boolean isCenter = true;
    //监听器
    private OnWheelViewItemSelectListener listener;
    /**
     * 选中的位置
     */
    private int index;

    private Context context;

    public WheelView(Context context) {
        super(context);
        this.context = context;
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 设置字体的颜色，不设置的话默认为黑色
     *
     * @param fontColor
     * @return
     */
    public WheelView fontColor(int fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * 设置字体的大小，不设置的话默认为30
     *
     * @param fontSize
     * @return
     */
    public WheelView fontSize(int fontSize) {
        this.fontSize = DensityUtils.sp2px(context, fontSize);
        return this;
    }

    /**
     * 设置文本到上下两边的补白，不合适的话默认为10
     *
     * @param padding
     * @return
     */
    public WheelView padding(int padding) {
        this.padding = padding;
        return this;
    }

    /**
     * 设置选中项的复制文本，可以不设置
     *
     * @param selectTip
     * @return
     */
    public WheelView selectTip(String selectTip) {
        this.selectTip = selectTip;
        return this;
    }

    /**
     * 设置文本列表，必须在builde方法之前设置
     *
     * @param lists
     * @return
     */
    public WheelView lists(List<String> lists) {
        this.lists = lists;
        return this;
    }

    /**
     * 设置显示行数，不设置的话默认为3
     * 这里必须设置为奇数
     *
     * @param showCount
     * @return
     */
    public WheelView showCount(int showCount) {
        if (showCount % 2 == 0) {
            throw new IllegalStateException("the showCoununt musb be odd ");
        }
        this.showCount = showCount;
        return this;
    }

    /**
     * 设置默认选中的文本索引，不设置默认为0
     *
     * @param select
     * @return
     */
    public WheelView select(int select) {
        this.select = select;
        return this;
    }

    /**
     * 设置是选中位置是否为中间,
     *
     * @param isCenter true--选中中间位置,false--选中第一个位置
     * @return
     */
    public WheelView setSelectLocation(boolean isCenter) {
        this.isCenter = isCenter;
        return this;
    }

    /**
     * 最后调用的方法，判断是否有必要函数没有被调用
     *
     * @return
     */
    public WheelView build() {
        if (lists == null) {
            throw new IllegalStateException("this method must invoke after the methou=d [lists]");
        }
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //得到总体宽度
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        //得到每一个item的高度
        Paint mPaint = new Paint();
        mPaint.setTextSize(fontSize);
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        itemHeight = (int) (metrics.bottom - metrics.top) + 2 * padding;
        //初始化每一个WheelItem
        initWheelItems(width, itemHeight);
        //初始化WheelSelect
        int selectLocation = 0;
        if (isCenter) {
            selectLocation = showCount / 2 * itemHeight;
        }
        wheelSelect = new WheelSelect(selectLocation, width,
                itemHeight, selectTip, fontColor, fontSize, padding);
        //得到所有的高度
        height = itemHeight * showCount;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 创建显示个数为+2个WheelItem
     *
     * @param width
     * @param itemHeight
     */
    private void initWheelItems(int width, int itemHeight) {
        wheelItems.clear();
        for (int i = 0; i < showCount + 2; i++) {
            int startY = itemHeight * (i - 1);
            int stringIndex = select - showCount / 2 - 1 + i;
            if (stringIndex < 0) {
                stringIndex = lists.size() + stringIndex;
            }
            wheelItems.add(new WheelItem(context, startY, width, itemHeight, fontColor, fontSize, lists.get(stringIndex)));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                mTouchY = event.getY();
                handleMove(dy);
                break;
            case MotionEvent.ACTION_UP:
                handleUp();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 处理移动操作
     */
    private void handleMove(float dy) {
        //调整坐标
        for (WheelItem item : wheelItems) {
            item.adjust(dy);
        }
        invalidate();
        //调整
        adjust();
    }

    /**
     * 处理抬起手的操作
     */
    private void handleUp() {

        index = -1;
        //得到应该选择的那一项
        for (int i = 0; i < wheelItems.size(); i++) {
            WheelItem item = wheelItems.get(i);
            //如果startY在selectItem的中点上，则将该项作为选择项
            if (item.getStartY() > wheelSelect.getStartY()
                    && item.getStartY() < (wheelSelect.getStartY() + itemHeight / 2)) {
                index = i;
                break;
            }

            //如果startY在selectItem的中点下面，则将上一项作为选中项
            if (item.getStartY() >= (wheelSelect.getStartY() + itemHeight / 2)
                    && item.getStartY() < (wheelSelect.getStartY() + itemHeight)) {

                index = i - 1;
                break;
            }
        }
        //如果没哟找到或者其他因素,直接返回
        if (index == -1) {
            return;
        }
        //得到偏移的位移
        float dy = wheelSelect.getStartY() - wheelItems.get(index).getStartY();
        //调整坐标
        for (WheelItem item1 : wheelItems) {
            item1.adjust(dy);
        }
        invalidate();
        //调整
        adjust();
        //设置选择项
        int stringIndex = lists.indexOf(wheelItems.get(index).getText());
        if (stringIndex != -1) {
            select = stringIndex;
            if (listener != null) {
                listener.onItemSelect(select);
            }
        }

    }

    /**
     * 调整item移动和循环
     */
    private void adjust() {
        //如果向下滑动超出半个item的高度，则调整容器
        if (wheelItems.get(0).getStartY() >= -itemHeight / 2) {
            //移除最后一个item重用
            WheelItem item = wheelItems.remove(wheelItems.size() - 1);
            //设置起点坐标
            item.setStartY(wheelItems.get(0).getStartY() - itemHeight);
            //得到文本在容器中的索引
            int index = lists.indexOf(wheelItems.get(0).getText());
            if (index == -1) {
                return;
            }
            index -= 1;
            if (index < 0) {
                index = lists.size() + index;
            }
            //设置文本
            item.setText(lists.get(index));
            //添加到最开始
            wheelItems.add(0, item);
            invalidate();
            return;
        }
        //如果向上滑超出半个item的高度，则调整容器
        if (wheelItems.get(0).getStartY() <= (-itemHeight / 2 - itemHeight)) {
            //移除第一个item重用
            WheelItem item = wheelItems.remove(0);
            //设置起点Y坐标
            item.setStartY(wheelItems.get(wheelItems.size() - 1).getStartY() + itemHeight);
            //得到文本在容器中的索引
            int index = lists.indexOf(wheelItems.get(wheelItems.size() - 1).getText());
            if (index == -1) {
                return;
            }

            index += 1;
            if (index >= lists.size()) {
                index = 0;
            }
            //设置文本
            item.setText(lists.get(index));
            //添加到最后面
            wheelItems.add(item);
            invalidate();
            return;
        }
    }

    /**
     * 得到当前的选中项
     */
    public int getSelectItem() {
        return select;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        if (wheelSelect != null) {
            wheelSelect.onDraw(canvas);
        }
//        //绘制每一项item
//        for (WheelItem item : wheelItems) {
//
//            item.onDraw(canvas);
//        }

        for (int i = 0; i < wheelItems.size(); i++) {
            if (i == index) {
                //将选中内容变为白色
                wheelItems.get(i).onFirstItemDraw(canvas);
            } else {
                wheelItems.get(i).onDraw(canvas);
            }
        }

    }

    public WheelView listener(OnWheelViewItemSelectListener listener) {
        this.listener = listener;
        return this;

    }

    public interface OnWheelViewItemSelectListener {
        void onItemSelect(int index);
    }
}