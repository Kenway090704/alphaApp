package com.alpha.alphaapp.model.v_1_2.logic;

import java.util.HashMap;

/**
 * Created by kenway on 17/9/27 14:41
 * Email : xiaokai090704@126.com
 * 处理论坛中post数据的工具类
 */

public class ForumNetPostUtil {
    public static HashMap<String, String> map;
    private static ForumNetPostUtil net = new ForumNetPostUtil();

    //key
    public static final String KEY_A = "a";
    public static final String KEY_METHOD = "method";
    public static final String KEY_SSKEY = "sskey";
    public static final String KEY_FID = "fid";
    public static final String KEY_TID = "tid";
    public static final String KEY_UID = "uid";
    public static final String KEY_UID2 = "uid2";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_ORDER = "order";
    public static final String KEY_PAGE = "page";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_TAGNAMES = "tagnames[]";
    public static final String KEY_FLASHATT = "flashatt";
    public static final String KEY_FROMUID = "fromuid";
    public static final String KEY_TOUID = "touid";
    //获取广告轮播的字段
    public static final String KEY_MODULEID = "moduleid";

    public static final String KEY_FILEDATA = "Filedata";

    //value
    public static final String VALUE_API = "api";
    public static final String VALUE_LASTPOST = "lastpost";//今日热门order
    public static final String VALUE_POSTDATE = "postdate";//最新发帖order


    public static ForumNetPostUtil initBaseMap() {
        map = new HashMap<>();
        map.put(KEY_A, VALUE_API);
        return net;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public ForumNetPostUtil addMethod(String method) {
        map.put(KEY_METHOD, method);
        return net;
    }

    public ForumNetPostUtil addSskey(String sskey) {
        map.put(KEY_SSKEY, sskey);
        return net;
    }

    public ForumNetPostUtil addUserName(String username) {
        map.put(KEY_USERNAME, username);
        return net;
    }


    public ForumNetPostUtil addOffset(String offset) {
        map.put(KEY_OFFSET, offset);
        return net;
    }


    public ForumNetPostUtil addLimit(String limit) {
        map.put(KEY_LIMIT, limit);
        return net;
    }

    public ForumNetPostUtil addOrder(String order) {
        map.put(KEY_ORDER, order);
        return net;
    }

    public ForumNetPostUtil addFid(String fid) {
        map.put(KEY_FID, fid);
        return net;
    }

    public ForumNetPostUtil addTid(String tid) {
        map.put(KEY_TID, tid);
        return net;
    }

    public ForumNetPostUtil addPage(String page) {
        map.put(KEY_PAGE, page);
        return net;
    }

    public ForumNetPostUtil addUid(String uid) {
        map.put(KEY_UID, uid);
        return net;
    }

    public ForumNetPostUtil addUid2(String uid2) {
        map.put(KEY_UID2, uid2);
        return net;
    }

    public ForumNetPostUtil addContent(String content) {
        map.put(KEY_CONTENT, content);
        return net;
    }


    public ForumNetPostUtil addTitle(String title) {
        map.put(KEY_TITLE, title);
        return net;
    }

    public ForumNetPostUtil addSubject(String subject) {
        map.put(KEY_SUBJECT, subject);
        return net;
    }

    public ForumNetPostUtil addFileName(String fileName) {
        map.put(KEY_FILEDATA, fileName);
        return net;
    }

    public ForumNetPostUtil addModuleid(String moduleid) {
        map.put(KEY_MODULEID, moduleid);
        return net;
    }

    public ForumNetPostUtil addTagNames(String tagnemes) {
        map.put(KEY_TAGNAMES, tagnemes);
        return net;
    }

    public ForumNetPostUtil addFlashatts(String flashAtts) {
        map.put(KEY_FLASHATT, flashAtts);
        return net;
    }



    public ForumNetPostUtil addFromUid(String fromuid) {
        map.put(KEY_FROMUID, fromuid);
        return net;
    }

    public ForumNetPostUtil addToUid(String touid) {
        map.put(KEY_TOUID, touid);
        return net;
    }

}
