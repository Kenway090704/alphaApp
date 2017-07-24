package com.alpha.lib_sdk.app.anim;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.alpha.lib_sdk.app.log.Log;

/**
 * Created by kenway on 17/7/7 16:57
 * Email : xiaokai090704@126.com
 */

public class AnimUtils {

    private  static  final  String TAG="AnimUtils";
    /**
     * 动态给ImageView添加动画
     *
     * @param imageView
     * @param count      第几个动画
     * @param frameCount
     * @return
     */
    public static AnimationDrawable getAnim(Context context, ImageView imageView, String count, int frameCount) {
        AnimationDrawable anim = new AnimationDrawable();
        for (int i = 1; i < frameCount; i++) {
            int id;
            if (i >= 10) {
                id = context.getResources().getIdentifier("app_animation" + count + "_000" + i, "drawable", context.getPackageName());
            } else {
                id = context.getResources().getIdentifier("app_animation" + count + "_0000" + i, "drawable",  context.getPackageName());
            }
            Drawable drawable = context.getResources().getDrawable(id);
            anim.addFrame(drawable, 40);
        }
        anim.setOneShot(true);
        imageView.clearAnimation();
        imageView.setImageDrawable(anim);
        return anim;
    }

    /**
     * 按图片顺序反着播放
     * @param context
     * @param imageView
     * @param count
     * @param frameCount
     * @return
     */
    public static AnimationDrawable getReverseAnim(Context context, ImageView imageView, String count, int frameCount) {
        AnimationDrawable anim = new AnimationDrawable();
        for (int i = frameCount-1; i >=0; i--) {

            Log.e(TAG,"app_animation" + count + "_000" + i);
            int id;
            if (i >= 0&&i<10) {
                id = context.getResources().getIdentifier("app_animation" + count + "_0000" + i, "drawable", context.getPackageName());
            } else {
                id = context.getResources().getIdentifier("app_animation" + count + "_000" + i, "drawable",  context.getPackageName());
            }

            Log.e(TAG,"id=="+id);
            Drawable drawable = context.getResources().getDrawable(id);
            anim.addFrame(drawable, 40);
        }
        anim.setOneShot(true);
        imageView.clearAnimation();
        imageView.setImageDrawable(anim);
        return anim;
    }

}
