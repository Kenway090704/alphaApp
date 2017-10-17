package com.alpha.alphaapp.ui.v_1_2.ui.topic;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseActivity;

import com.alpha.alphaapp.ui.v_1_2.ui.adapter.AlbumPhotoAdapter;
import com.alpha.alphaapp.ui.v_1_2.ui.decoration.PhotoSpacesItemDecoration;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/10/9 10:31
 * Email : xiaokai090704@126.com
 * 从相册中选取多张图片
 */

public class MultiImageSelectorActivity extends BaseActivity {

    private TextView tv_cancel, tv_done;
    private RecyclerView rv;

    private List<String> list = new ArrayList<>();
    private AlbumPhotoAdapter adapter;

    private Map<String, Boolean> map = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_img_select;
    }

    @Override
    protected void initView() {
        tv_cancel = (TextView) findViewById(R.id.title_tv_cancel);
        tv_done = (TextView) findViewById(R.id.title_tv_done);
        rv = (RecyclerView) findViewById(R.id.acty_multi_img_selcet_rv);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        rv.addItemDecoration(new PhotoSpacesItemDecoration(8));
        adapter = new AlbumPhotoAdapter(this, list);
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        addPhotoData();

    }

    private void addPhotoData() {

        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            list.add(path);
            map.put(path, false);
        }
        adapter.setMapChoose(map);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择选中图片

                if (!Util.isNull(call))
                    call.onChoosePics(adapter.getChoosePhotoUris());
                finish();
            }
        });
    }


    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context,OnChoosePicUrisCallBack callBack) {
        call=callBack;
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        context.startActivity(intent);
    }

    private static OnChoosePicUrisCallBack call;

    interface OnChoosePicUrisCallBack {
        void onChoosePics(List<String> list);
    }
}
