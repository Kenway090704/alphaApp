package com.alpha.alphaapp.model.mall.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 11:31
 * Email : xiaokai090704@126.com
 */

public class GoodsBean implements Serializable {

    private static String KEY_GOODS_LIST = "goods_list";
    private static String KEY_GOODS_DETAIL = "goods_detail";
    private int goods_id;
    private String goods_name;
    private int goods_type;
    private int goods_classify;
    private int score;
    private int total_count;
    private int remain_count;
    private String undercarriage_time;
    private int is_recommend;
    private int recommend_num;
    private int exchange_count;
    private String detail_text;
    private String goods_no;
    private List<String> pictures;


    /**
     *
     * 获取全部商品信息
     *  result==={"result":0,"msg":"","goods_list":
     *  [{"goods_id":10000,"goods_name":"测试商品","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-07/Ci_7324148888804510000.png"],"score":1,"total_count":3,"remain_count":1,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"app纯文本字段:","goods_no":"123"},
     *  {"goods_id":10002,"goods_name":"爆裂二代飞车装备包","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_98501489398372.jpg"],"score":8000,"total_count":1000,"remain_count":1000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10003,"goods_name":"特别版爆旋猎兵","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_3131489400977.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10004,"goods_name":"特别版风暴圣骑","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_45411489401025.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10005,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_87231489401084.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10006,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_40061489401112.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10007,"goods_name":"特别版精准发射器","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_30991489401150.jpg"],"score":5000,"total_count":5000,"remain_count":4999,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10008,"goods_name":"特别版精准发射器cim","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_51931491028249.jpg"],"score":5000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10009,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_49561491028271.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10010,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_86511491028303.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""}]}
     */

    /**
     * 返回的数据包含个人已兑换数量
     *  result==={"result":0,"msg":"","goods_list":
     *  [{"goods_id":10000,"goods_name":"测试商品","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-07/Ci_7324148888804510000.png"],"score":1,"total_count":3,"remain_count":1,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"app纯文本字段:","goods_no":"123"},
     *  {"goods_id":10002,"goods_name":"爆裂二代飞车装备包","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_98501489398372.jpg"],"score":8000,"total_count":1000,"remain_count":1000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10003,"goods_name":"特别版爆旋猎兵","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_3131489400977.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10004,"goods_name":"特别版风暴圣骑","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_45411489401025.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10005,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_87231489401084.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10006,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_40061489401112.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10007,"goods_name":"特别版精准发射器","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_30991489401150.jpg"],"score":5000,"total_count":5000,"remain_count":4999,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10008,"goods_name":"特别版精准发射器cim","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_51931491028249.jpg"],"score":5000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10009,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_49561491028271.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     *  {"goods_id":10010,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_86511491028303.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""}]}
     * @param str
     * @param key
     * @return
     */

    /**
     * 返回商品包含剩余数量
     * result==={"result":0,"msg":"","goods_list":
     * [{"goods_id":10000,"goods_name":"测试商品","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-07/Ci_7324148888804510000.png"],"score":1,"total_count":3,"remain_count":1,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"app纯文本字段:","goods_no":"123"},
     * {"goods_id":10002,"goods_name":"爆裂二代飞车装备包","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_98501489398372.jpg"],"score":8000,"total_count":1000,"remain_count":1000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10003,"goods_name":"特别版爆旋猎兵","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_3131489400977.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10004,"goods_name":"特别版风暴圣骑","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_45411489401025.jpg"],"score":25000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10005,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_87231489401084.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10006,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_40061489401112.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10007,"goods_name":"特别版精准发射器","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-13/Ci_30991489401150.jpg"],"score":5000,"total_count":5000,"remain_count":4999,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10008,"goods_name":"特别版精准发射器cim","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_51931491028249.jpg"],"score":5000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10009,"goods_name":"特别版漩涡邪凰","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_49561491028271.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""},
     * {"goods_id":10010,"goods_name":"特别版怒岩魔狼","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-04-01/Ci_86511491028303.jpg"],"score":20000,"total_count":5000,"remain_count":5000,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"","goods_no":""}]}
     *
     * @param str
     * @return
     */
    public static GoodsBean objectFromData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getJSONObject(KEY_GOODS_DETAIL).toString(), GoodsBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <p> key="goods_list"</p>
     *
     * @param str
     * @param
     * @return
     */
    public static List<GoodsBean> arrayGoodsBeanFromData(String str) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GoodsBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(KEY_GOODS_LIST).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public int getGoods_classify() {
        return goods_classify;
    }

    public void setGoods_classify(int goods_classify) {
        this.goods_classify = goods_classify;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(int remain_count) {
        this.remain_count = remain_count;
    }

    public String getUndercarriage_time() {
        return undercarriage_time;
    }

    public void setUndercarriage_time(String undercarriage_time) {
        this.undercarriage_time = undercarriage_time;
    }

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public int getRecommend_num() {
        return recommend_num;
    }

    public void setRecommend_num(int recommend_num) {
        this.recommend_num = recommend_num;
    }

    public int getExchange_count() {
        return exchange_count;
    }

    public void setExchange_count(int exchange_count) {
        this.exchange_count = exchange_count;
    }

    public String getDetail_text() {
        return detail_text;
    }

    public void setDetail_text(String detail_text) {
        this.detail_text = detail_text;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_type=" + goods_type +
                ", goods_classify=" + goods_classify +
                ", score=" + score +
                ", total_count=" + total_count +
                ", remain_count=" + remain_count +
                ", undercarriage_time='" + undercarriage_time + '\'' +
                ", is_recommend=" + is_recommend +
                ", recommend_num=" + recommend_num +
                ", exchange_count=" + exchange_count +
                ", detail_text='" + detail_text + '\'' +
                ", goods_no='" + goods_no + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
