package com.alpha.alphaapp.ui.mine.logic;

import android.content.Context;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.ui.mine.logic.bean.AreaBean;
import com.alpha.alphaapp.ui.mine.logic.bean.CityBean;
import com.alpha.alphaapp.ui.mine.logic.bean.ProviceBean;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kenway on 17/6/20 18:51
 * Email : xiaokai090704@126.com
 */

public class GetPCityAreaData {

    private Context context;
    public static final ArrayList<String> itme1datas = new ArrayList<>();
    public static final ArrayList<ArrayList<String>> item2datas = new ArrayList<>();
    public static final ArrayList<ArrayList<ArrayList<String>>> item3datas = new ArrayList<>();
    private static GetPCityAreaData gpcad;
    //启动线程现在地址信息
    public void init(Context context) {
        this.context = context;
        downProvince();
    }

    private GetPCityAreaData() {

    }

    /**
     * 获取省市区的数据单例
     *
     * @return
     */
    public static GetPCityAreaData getInstance() {
        GetPCityAreaData inst = gpcad;
        if (inst == null) {
            synchronized (AccountManager.class) {
                inst = gpcad;
                if (inst == null) {
                    inst = new GetPCityAreaData();
                    gpcad = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 下载省
     */
    private void downProvince() {

        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                List<ProviceBean> provinces = ProviceBean.arrayProvieBeanFromJson(result);
                for (int i = 0; i < provinces.size(); i++) {
                    String province = provinces.get(i).getProvince();
                    itme1datas.add(province);
                    ArrayList<String> city_list = new ArrayList<>();
                    ArrayList<ArrayList<String>> provice_areaList = new ArrayList<>();
                    item2datas.add(city_list);
                    item3datas.add(provice_areaList);
                    downCity(province, city_list, provice_areaList);
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };

        RequestManager.getInstance(context).requestGet(URLConstans.GET_ADDR_URL.PROVINCE, callBack);
    }

    private void downCity(final String provice, final ArrayList<String> city_list, final ArrayList<ArrayList<String>> provice_areaList) {
        HashMap<String, String> map = new HashMap<>();
        map.put("p", provice);
        ReqCallBack<String> callback = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                List<CityBean> citys = CityBean.arrayCityBeanFromStr(result);
                for (int i = 0; i < citys.size(); i++) {
                    String city = citys.get(i).getCity();
                    city_list.add(city);
                    ArrayList<String> city_areas = new ArrayList<>();
                    provice_areaList.add(city_areas);
                    downArea(city, city_areas);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };

        RequestManager.getInstance(context).requestPostByAsynWithForm(URLConstans.GET_ADDR_URL.CITY, map, callback);
    }

    private void downArea(String city, final ArrayList<String> city_areas) {
        HashMap<String, String> map = new HashMap<>();
        map.put("c", city);
        ReqCallBack<String> callback = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                List<AreaBean> areas = AreaBean.arrayAreaBeanFromStr(result);
                for (int i = 0; i < areas.size(); i++) {
                    String area = areas.get(i).getArea();
                    if (Util.isNullOrBlank(area)) {
                        city_areas.add("");
                    } else {
                        city_areas.add(area);
                    }
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };

        RequestManager.getInstance(context).requestPostByAsynWithForm(URLConstans.GET_ADDR_URL.AREA, map, callback);

    }
}
