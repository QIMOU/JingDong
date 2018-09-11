package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.FenleiparentEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class HomeItemAdapter extends BaseAdapter {

    private Context context;
    private List<FenLeiEntity.DataBean.ListBean> mList;

    public HomeItemAdapter(Context context, List<FenLeiEntity.DataBean.ListBean> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public int getCount() {
       return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FenLeiEntity.DataBean.ListBean listBean = mList.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fenlei_item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon =  convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(listBean.getName());
        Uri uri = Uri.parse(listBean.getIcon());
        viewHold.iv_icon.setImageURI(uri);
        return convertView;
    }
    class ViewHold {
        private TextView tv_name;
        private SimpleDraweeView iv_icon;
    }

}
