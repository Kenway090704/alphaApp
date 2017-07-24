package com.alpha.alphaapp.model.mall.bean;

import android.app.Activity;
import android.view.View;

/**
 * Created by kenway on 17/7/17 15:25
 * Email : xiaokai090704@126.com
 * <p>
 * 积分兑换入口信息类
 */

public class ScoreExchangeEnterItemInfo {

    private int srcId;
    private String tv_curr_score;
    private String web_url;
    private int product_id;

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public String getTv_curr_score() {
        return tv_curr_score;
    }

    public void setTv_curr_score(String tv_curr_score) {
        this.tv_curr_score = tv_curr_score;
    }



    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "ScoreExchangeEnterItemInfo{" +
                "srcId=" + srcId +
                ", tv_curr_score='" + tv_curr_score + '\'' +
                ", web_url='" + web_url + '\'' +
                ", product_id=" + product_id +
                '}';
    }
}
