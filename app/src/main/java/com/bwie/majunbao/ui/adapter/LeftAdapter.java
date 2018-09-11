package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;

import java.util.List;

public class LeftAdapter extends BaseAdapter {
    private Context context;
    private List<LeftFenLeiEntity.DataBean> leftfenleibean;
    private int selectedPosition=0 ;// 选中的位置
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public LeftAdapter(Context context, List<LeftFenLeiEntity.DataBean> leftfenleibean) {
        this.context = context;
        this.leftfenleibean = leftfenleibean;
    }

    @Override
    public int getCount() {
        return leftfenleibean.size();
    }

    @Override
    public Object getItem(int i) {
        return leftfenleibean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_menu, null);
            holder= new ViewHolder();
            holder.item_name = convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (selectedPosition == position) {
            holder.item_name.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.item_name.setTextColor(Color.parseColor("#000000"));
            holder.item_name.setTextSize(18);
        } else {
            holder.item_name.setBackgroundColor(Color.TRANSPARENT);
            holder.item_name.setTextColor(Color.parseColor("#393939"));
            holder.item_name.setTextSize(12);
        }
        holder.item_name.setText(leftfenleibean.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView item_name;
    }
}