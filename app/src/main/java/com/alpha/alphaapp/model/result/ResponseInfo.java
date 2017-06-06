package com.alpha.alphaapp.model.result;

import com.alpha.lib_sdk.app.log.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
     * 从返回的json字符串中获取result的值
     *
     * @param json
     * @return result
     */
    public static int getFromJsonResult(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("result");

            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //10000可以代表字段不存在
        return 10000;
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
//            newResponseInfo.setSskey(jsonObject.getString("sskey"));
            Log.e("res",jsonObject.getString("msg"));
            Log.e("res",jsonObject.getString("result"));
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

    public static ResponseInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ResponseInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ResponseInfo> arrayRegitsterRespInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ResponseInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


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
