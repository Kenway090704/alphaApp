package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.SectionSimpleBean;
import com.alpha.alphaapp.ui.v_1_2.ui.section.SectionActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/9/20 17:29
 * Email : xiaokai090704@126.com
 * <p>
 * 版块入口父版块与子版块cell
 */

public class SectionSimpleCell extends RVBaseCell<SectionSimpleBean> {

    public SectionSimpleCell(SectionSimpleBean sectionSimpleBean) {
        super(sectionSimpleBean);
    }

    @Override
    public int getItemType() {
        return Entry.SECTIONSIMPLECELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_setion_enter, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        SpannableString spannableString = new SpannableString(mData.getForumname() + "(" + mData.getChild().get(0).getTodaypost() + ")");
        ForegroundColorSpan span = new ForegroundColorSpan(ResourceUtil.resToColor(holder.getItemView().getContext(), R.color.common_tv_dark_red));
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.8f);
        spannableString.setSpan(sizeSpan01, mData.getForumname().length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span, mData.getForumname().length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.setText(R.id.cell_section_enter_tv_big_section_name, spannableString);

        ImageView iv_logo = holder.getImageView(R.id.cell_section_enter_iv_section_logo);
        ImageLoader.load(iv_logo.getContext(),mData.getChild().get(0).getIcon(),iv_logo);

        holder.setText(R.id.cell_section_enter_tv_child_section_name, mData.getChild().get(0).getForumname());
        holder.setText(R.id.cell_section_enter_tv_subject_count, mData.getChild().get(0).getTodaypost());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入版块列表页
                SectionActivity.actionStart(v.getContext(), mData.getChild().get(0).getForumname(), mData.getChild().get(0).getFid());

            }
        });


    }
}
