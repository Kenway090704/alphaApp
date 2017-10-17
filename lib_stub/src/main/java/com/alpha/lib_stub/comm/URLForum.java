package com.alpha.lib_stub.comm;

/**
 * Created by kenway on 17/9/27 15:07
 * Email : xiaokai090704@126.com
 */

public class URLForum {
    public static final String FORUM_BASE_POST_URL = "http://120.76.27.29:8090/aCloud/index.php";

    public interface FORUM_METHOD {
        /**
         * 登录论坛
         * 需要sskey
         */
        String FORUM_LOGIN = "customized.user.login";

        /**
         * 未注册时,设置forum_name
         * <p>
         * username,sskey
         */
        String FORUM_SET_NAME = "customized.user.register";
        //================================版块===========================//
        /**
         * 获取全部版块热点推荐帖子列表(offset,limit, order)
         */
        String ALL_HOT = "customized.thread.getlist";

        /**
         * 获取版块列表
         */
        String GET_SECTION_LIST_URL = "customized.forum.all.get";
        /**
         * 获取版块详情(fid)
         */
        String GET_SECTION_DETAIL_URL = "customized.forum.get";

        /**
         * 获取指定版块今日热门帖子列表列表(fid,offset,limit,order=lastpost)
         */
        String GET_SECTION_HOT_TOPIC_LIST_URL = "customized.thread.getByFid";

        /**
         * 获取指定版块最新发帖列表(fid,offset,limit,order=postdate)
         */
        String GET_SECTION_POSTDATA_TOPIC_LIST_URL = "customized.thread.getByFid";

        //===========================帖子=================================//
        /**
         * 获取 帖子内容的接口(tid)
         */
        String GET_TOPIC_CONTENT_URL = "customized.thread.get";

        /**
         * 获取帖子的评论列表(tid,page,offset,limit)
         */
        String GET_TOPIC_REPLY_LIST_URL = "customized.post.getLatestPost";

        /**
         * 对帖子发起评论(tid,uid,title,content)
         */
        String SEND_NEW_REPLY_URL = "customized.post.send";

        /**
         * 发布新帖(uid,fid,subject,content)
         */
        String CREATE_NEW_TOPIC_URL = "customized.thread.send";

        /**
         * 发布新帖上传图片
         */
        String CREATE_NEW_TOPIC_UPLOAD_PIC = "customized.thread.upload";

        /**
         * 获取所有表情的接口
         */
        String GET_EMOTION_URL = "customized.thread.emotion";
        /**
         * 获取今日热门banner的接口
         */
        String GET_BANNER_URL = "customized.thread.design";

        //======================话题=======================//
        String GET_THEME_URL = "customized.thread.tag";


        //=====================个人======================//
        /**
         * 添加对其他用户的关注
         */
        String  ADD_USER_ATTETNTON_URL="customized.friend.follow.add";
        /**
         * 取消对其他用户的关注
         */
        String  CANCEL_USER_ATTETNTON_URL="customized.friend.follow.delete";

        /**
         * TA的主题(uid,offset,limit)
         */
        String GET_USER_TOPICS_URL="customized.thread.getByUid" ;
        //=======================私信========================//


        /**
         * 获取未读消息数(uid)
         */
        String  GET_NO_READ_MESSAGE_COUNT_URL="customized.message.unread.count";
        /**
         *获取私信消息列表(uid,offset,limit)
         */

        String GET_MESSAGE_LIST_URL="customized.message.gets";
        /**
         *c 发送消息与回复消息为同一接口(fromuid,touid,content)
         */
        String   SEND_MESSAGE_URL="customized.message.send";

    }
}
