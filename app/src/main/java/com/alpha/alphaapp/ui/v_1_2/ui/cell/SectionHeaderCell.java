package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.SectionDeatalBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/10/12 13:43
 * Email : xiaokai090704@126.com
 * 版块详情页中,版块头部
 */

public class SectionHeaderCell extends RVBaseCell<SectionDeatalBean> {
    public SectionHeaderCell(SectionDeatalBean sectionDeatalBean) {
        super(sectionDeatalBean);
    }

    @Override
    public int getItemType() {
        return Entry.SECIONTHEADERCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_section_header, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        ImageView iv = holder.getImageView(R.id.cell_section_hear_iv_logo);
        ImageLoader.load(iv.getContext(), mData.getLogo(), iv);
        holder.setText(R.id.cell_section_hear_tv_name, mData.getForumname());

        holder.setText(R.id.cell_section_hear_tv_topic_count, ResourceUtil.resToStr(iv.getContext(), R.string.topic_count_) + "接口中没有此数据");

        //设置版主信息
        String manager = mData.getManager();
        String section_manger = ResourceUtil.resToStr(iv.getContext(), R.string.section_manager_);
        if (Util.isNullOrBlank(manager)) {
            String no = ResourceUtil.resToStr(iv.getContext(), R.string.no);

            holder.setText(R.id.cell_section_hear_tv_manger, section_manger + no);
        } else {
            String managers = manager.replace(",", " ");
            holder.setText(R.id.cell_section_hear_tv_manger, section_manger + managers);
        }
        //设置描述语

        holder.setText(R.id.cell_section_hear_tv_des, ResourceUtil.resToStr(iv.getContext(), R.string.intro_) + mData.getDescrip());

    }
}
