package com.alpha.alphaapp.model.mall.bean;

import android.app.Activity;

/**
 * Created by kenway on 17/7/17 15:25
 * Email : xiaokai090704@126.com
 * <p>
 * 激活入口的三个item数据
 */

public class ActiveEnterItemInfo {

    private int srcId;
    private String tv_active_time;
    private String tv_active_tiem_2;
    /**
     * 官网地址
     */
    private String web_url;
    /**
     * 产品product_id,
     */
    private int product_id;

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public String getTv_active_time() {
        return tv_active_time;
    }

    public void setTv_active_time(String tv_active_time) {
        this.tv_active_time = tv_active_time;
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
    public String getTv_active_tiem_2() {
        return tv_active_tiem_2;
    }

    public void setTv_active_tiem_2(String tv_active_tiem_2) {
        this.tv_active_tiem_2 = tv_active_tiem_2;
    }
    @Override
    public String toString() {
        return "ActiveEnterItemInfo{" +
                "srcId=" + srcId +
                ", tv_active_time='" + tv_active_time + '\'' +
                ", web_url='" + web_url + '\'' +
                ", product_id=" + product_id +
                '}';
    }
}
