package com.alpha.alphaapp.model.v_1_1.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/21 15:58
 * Email : xiaokai090704@126.com
 */

public class ScoreLogBean {
    public static final String KEY_SCORE_LOGS = "score_logs";
    private int pre_score;
    private int cur_score;
    private int op_score;
    private int reason;
    private String score_desc;
    private int channel_id;
    private String op_time;
    private String reason_text;
    private String channel_id_text;

    public static ScoreLogBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ScoreLogBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ScoreLogBean> arrayScoreLogBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ScoreLogBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getPre_score() {
        return pre_score;
    }

    public void setPre_score(int pre_score) {
        this.pre_score = pre_score;
    }

    public int getCur_score() {
        return cur_score;
    }

    public void setCur_score(int cur_score) {
        this.cur_score = cur_score;
    }

    public int getOp_score() {
        return op_score;
    }

    public void setOp_score(int op_score) {
        this.op_score = op_score;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getScore_desc() {
        return score_desc;
    }

    public void setScore_desc(String score_desc) {
        this.score_desc = score_desc;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getReason_text() {
        return reason_text;
    }

    public void setReason_text(String reason_text) {
        this.reason_text = reason_text;
    }

    public String getChannel_id_text() {
        return channel_id_text;
    }

    public void setChannel_id_text(String channel_id_text) {
        this.channel_id_text = channel_id_text;
    }

    @Override
    public String toString() {
        return "ScoreLogBean{" +
                "pre_score=" + pre_score +
                ", cur_score=" + cur_score +
                ", op_score=" + op_score +
                ", reason=" + reason +
                ", score_desc='" + score_desc + '\'' +
                ", channel_id=" + channel_id +
                ", op_time='" + op_time + '\'' +
                ", reason_text='" + reason_text + '\'' +
                ", channel_id_text='" + channel_id_text + '\'' +
                '}';
    }
}
