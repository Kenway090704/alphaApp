package com.alpha.alphaapp.ui.mine.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alpha.alphaapp.R;

import java.util.List;

/**
 * Created by kenway on 17/6/29 12:05
 * Email : xiaokai090704@126.com
 * 底部弹出对话学校的ListView
 */

public class SchoolListAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas;

    public SchoolListAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.widget_school_adapter_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.shcool_adapter_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setText(datas.get(position));
        return convertView;
    }

    public class ViewHolder {
        TextView tv;

        public void setText(String txt) {
            tv.setText(txt);
        }
    }
}
