package com.alpha.alphaapp.model.v_1_2.logic;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.model.v_1_2.bean.ForumBean;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.URLConstans;

import java.util.List;

/**
 * Created by kenway on 17/8/15 15:15
 * Email : xiaokai090704@126.com
 */

public class ForumLogic {

    public static   void  getForumList(final OnModelCallback<List<ForumBean>> callBack){


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                         List<ForumBean> list=ForumBean.parseData(result);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(list);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGet(URLConstans.FORUM_URL.HOTHIT_POST_URL, call);
    }
}
