package com.alpha.alphaapp.model;

/**
 * Created by kenway on 17/7/20 10:08
 * Email : xiaokai090704@126.com
 */

public interface OnModelLoginForumCallback<T>  extends OnModelCallback<T> {
    /**
     * 奥飞通行证账号未注册论坛
     */
   void   onModelNoRegitser();
}
