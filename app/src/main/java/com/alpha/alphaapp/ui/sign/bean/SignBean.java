package com.alpha.alphaapp.ui.sign.bean;

/**
 * Created by kenway on 17/7/11 10:43
 * Email : xiaokai090704@126.com
 */

public class SignBean {

    private boolean isChoose;
    private String pro_id;
    private int pic;
    private String msg;

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
