package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.weiget.GridViewForScrollView;

import java.util.List;


public class RightAdapter extends BaseAdapter {
    private Context context;
    private List<FenLeiEntity.DataBean> fenleibean;

    public RightAdapter(Context context, List<FenLeiEntity.DataBean> fenleibean) {
        this.context = context;
        this.fenleibean = fenleibean;
    }

    @Override
    public int getCount() {
        return fenleibean.size();
    }

    @Override
    public Object getItem(int i) {
        return fenleibean.size();
        // TODO: 2018/9/8 不会
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<FenLeiEntity.DataBean.ListBean> list = fenleibean.get(position).getList();
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fenlei_right_adapter, null);
            holder= new ViewHolder();
            holder.gridView = convertView.findViewById(R.id.gridView);
            holder.blank = convertView.findViewById(R.id.blank);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, list.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        HomeItemAdapter adapter = new HomeItemAdapter(context, list);
        holder.blank.setText(fenleibean.get(position).getName());
        holder.gridView.setAdapter(adapter);
        return convertView;
    }
    class ViewHolder{
       GridViewForScrollView gridView;
       TextView blank;
    }
}
