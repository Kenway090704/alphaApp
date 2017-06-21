/**
 * Project Name:WechatLoginDemo
 * File Name:WxAccessTokenResp.java
 * Package Name:com.example.chenzhengjun.wechatlogindemo
 * Date:11/5/2015
 * Copyright (c) 2015, iczjun@gmail.com All Rights Reserved.
 */
package com.alpha.alphaapp.wxapi;

/**
 * @author kenway090704
 */
public interface WxAccessTokenResp {
    void onSuccess(WxAccessTokenInfo result);

    void onFailed(String error);
}
