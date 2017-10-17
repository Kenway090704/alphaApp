package com.alpha.lib_stub.comm;

/**
 * Created by kenway on 17/9/19 15:32
 * Email : xiaokai090704@126.com
 * 论坛模块Result
 */

public class ForumStants {
    /**
     * 登录注册论坛接口
     * http://120.76.27.29:8090/aCloud/index.php?a=api&method=customized.user.login&sskey=Zpg47y4vthChG4oUknNZUB0nTk3dzYWvHLenT7Fl
     */
    public interface LOGIN_RESULT {
        /**
         * 登录成功
         */
        String RESULT_OK = "0";
        /**
         * 该用户中心账号未注册为奥飞论坛账号
         */
        String RESULT_NO_REGISTER = "1001";
    }

    /**
     * 无注册时需要调用的接口,为该账号添加一个论坛name
     * http://120.76.27.29:8090/aCloud/index.php?a=api&method=customized.user.register&username=啦啦啦12&sskey=Zpg47y4vthChG4oUknNZUB0nTk3dzYWvHLenT7Fl
     */
    public interface NO_REGISTER_RESULT {
        /**
         * 注册成功
         */
        String RESULT_OK = "0";
        /**
         * 该奥飞通行证已被注册
         */
        String RESULT_HAD_REGISTER = "-1";
    }

    /**
     * 对帖子进行评论时返回的结果
     */
    public interface SEND_REPLY_RESULT {
        /**
         * 发送评论成功
         */
        String RESULT_OK = "0";

    }

    /**
     * 发布新帖
     */
    public interface SEND_TOPIC_RESULT {
        /**
         * 发送成功
         */
        String RESULT_OK = "0";

    }

    /**
     * 发布新帖时上传图片
     */
    public interface SEND_TOPIC_UPLOAD_PIC_RESULT {
        /**
         * 发送成功
         */
        String RESULT_OK = "0";

    }

    //=========================个人========================//

    /**
     * 添加用户关注的结果
     */
    public  interface  ADD_USER_ATTENT_RESULT{
        String RESULT_OK="0";
        /**
         * 你已经关注了该用户
         */
        String RESULT_HAS_ATTENTION="-1";
    }

    /**
     * 取消用户关注的结果
     */
    public  interface  DELETE_USER_ATTENT_RESULT{
        /**
         * 取消关注成功
         */
        String RESULT_OK="0";

        /**
         * 未关注该用户
         */
        String RESULT_HAS_ATTENTION="-1";
    }
}
