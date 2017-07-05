package com.alpha.alphaapp.ui.score;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alpha.alphaapp.R;

public class RotatImageView extends View {
	private Paint paint = null; // ����
	private Bitmap mbitmap = null; // ͼƬλͼ
	private Bitmap bitmapDisplay = null;
	private Matrix matrix = null;
	private int mWidth = 0; // ͼƬ�Ŀ��
	private int mHeight = 0; // ͼƬ�ĸ߶�
	private float fAngle = 180.0f; // ͼƬ��ת
	private PaintFlagsDrawFilter mDrawFilter; 

	public RotatImageView(Context context) {
		super(context);
	}
	
	public RotatImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	/**
	 * ��ʼ��һЩ�Զ���Ĳ���
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RotatImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.RotatImageView, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			// ԭʼͼƬ���ڲ��������ȡ
			case R.styleable.RotatImageView_src:
				mbitmap = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr, 0));
				bitmapDisplay = mbitmap;
				break;
			}
		}
		a.recycle();

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		matrix = new Matrix();
	}
	
	/**
	 * ����ؼ��ĸ߶ȺͿ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// ���ÿ��
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		//match_parent�������õľ�ȷֵ��ȡ
		//MeasureSpec.EXACTLY
		if (specMode == MeasureSpec.EXACTLY)
		{
			mWidth = specSize;
		} 
		else
		{
			// ��ͼƬ�����Ŀ�
			//getPaddingLeft(),getPaddingRight()������ֵ�ǿؼ����Ե�����ƫ�Ƶľ���ֵ�����Ե�һ�����
			//������layout_marginLeft,�����ؼ�������ֵ����
			int desireByImg = getPaddingLeft() + getPaddingRight()
					+ mbitmap.getWidth();
			
			// wrap_content
			if (specMode == MeasureSpec.AT_MOST)
			{
				//������С��ֵ����ȵĻ���������ƫ�ƾ���֮��
				mWidth = Math.min(desireByImg, specSize);
			} else

				mWidth = desireByImg;
		}

		// ���ø߶ȣ����ֽ���ͬ��
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		
		//match_parent�������õľ�ȷֵ��ȡ
		//MeasureSpec.EXACTLY
		if (specMode == MeasureSpec.EXACTLY)
		{
			mHeight = specSize;
		} else
		{
			int desire = getPaddingTop() + getPaddingBottom()
					+ mbitmap.getHeight();

			// wrap_content
			if (specMode == MeasureSpec.AT_MOST)
			{
				mHeight = Math.min(desire, specSize);
			} else
				mHeight = desire;
		}

		//����õĿ���Լ��߶���ֵ�����ý�ȥ
		setMeasuredDimension(mWidth, mHeight);
	}

	// ������ת
	public void setRotationLeft() {
		fAngle = fAngle - 20;
		setAngle();
	}

	// ������ת
	public void setRotationRight() {
		fAngle = fAngle + 20;
		setAngle();
	}
	
	private boolean isRoate = false;
	// ������ת����
	private void setAngle() {
		Log.i("Show", String.valueOf(fAngle));
		isRoate = true;
		//����ͼƬ����ת���ģ����ƣ�X,Y��������������ת Ҫ��ת�ĽǶ�
		matrix.preRotate(fAngle, (float)mbitmap.getWidth()/2, (float)mbitmap.getHeight()/2); 
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//�������, ͼƬ��ת��ľ���������ɹ���ʵ�ڲ���ͼƬ��Ե��һЩ��ɫ���ص�
		canvas.setDrawFilter(mDrawFilter); 
		if (isRoate) {
			canvas.drawBitmap(bitmapDisplay, matrix, paint);
			isRoate = false;
		}else {
			canvas.drawBitmap(bitmapDisplay, 0, 0, paint);	
		}
	}
}
