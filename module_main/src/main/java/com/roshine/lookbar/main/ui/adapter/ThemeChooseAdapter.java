package com.roshine.lookbar.main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.roshine.lookbar.commonlib.utils.ThemeColorBean;
import com.roshine.lookbar.commonlib.wight.CircleView;
import com.roshine.lookbar.main.R;


/**
 *
 * @author DJT
 * @date 2017/3/24 16:33
 * @desc 主题颜色选择Adapter
 *
 */
public class ThemeChooseAdapter extends BaseAdapter {
    private ThemeColorBean[] colors;
    private Context mContext;

    public ThemeChooseAdapter(Context context, ThemeColorBean[] colors){
        mContext = context;
        this.colors = colors;
    }
    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int i) {
        return colors[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder holder;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.main_item_theme_color,null);
            holder = new ViewHolder();
            holder.viewItemThemeColor = (CircleView)view.findViewById(R.id.view_theme_color);
            holder.ivChecked = (ImageView) view.findViewById(R.id.iv_theme_color_sure);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.viewItemThemeColor.setBackgroundColor(mContext.getResources().getColor(colors[position].getColorValue()));
        return view;
    }

    private static class ViewHolder{
        CircleView viewItemThemeColor;
        ImageView ivChecked;
    }
}
