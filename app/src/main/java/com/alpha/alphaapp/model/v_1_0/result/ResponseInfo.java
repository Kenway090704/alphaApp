package com.alpha.alphaapp.model.v_1_0.result;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kenway on 17/5/23 15:52
 * Email : xiaokai090704@126.com
 * 访问响应数据的response
 */

public class ResponseInfo {

    private int result;
    private String msg;
    private String sskey;
    private String phone_verify;

    public String getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(String phone_verify) {
        this.phone_verify = phone_verify;
    }

    /**
     * 将返回的json 数据转换为ResponseInfo1对象
     *
     * @param json
     * @return
     */
    public static ResponseInfo getRespInfoFromJsonStr(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            ResponseInfo newResponseInfo = new ResponseInfo();
            newResponseInfo.setMsg(jsonObject.getString("msg"));
            newResponseInfo.setResult(jsonObject.getInt("result"));
            return newResponseInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * postjson数据后返回的数据解析为ResponseInfo1
     *
     * @param json
     * @param hasSessKey 是否有sesskey  默认没有
     * @return
     */
    public static ResponseInfo getRespInfoFromJsonStr(String json, boolean hasSessKey) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            ResponseInfo newResponseInfo = new ResponseInfo();
            if (hasSessKey) {
                newResponseInfo.setSskey(jsonObject.getString("sskey"));
            }
            newResponseInfo.setMsg(jsonObject.getString("msg"));
            newResponseInfo.setResult(jsonObject.getInt("result"));
            return newResponseInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取有验证码的响应
     * @param json
     * @param hasVerify
     * @return
     */
    public static ResponseInfo getRespInfoFromJsonStrHadVerify(String json, boolean hasVerify) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            ResponseInfo newResponseInfo = new ResponseInfo();
            if (hasVerify) {
                newResponseInfo.setPhone_verify(jsonObject.getString("phone_verify"));
            }
            newResponseInfo.setMsg(jsonObject.getString("msg"));
            newResponseInfo.setResult(jsonObject.getInt("result"));
            return newResponseInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", sskey='" + sskey + '\'' +
                '}';
    }
}
