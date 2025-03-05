package com.example.customgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonHocAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }

    @Override
    public int getCount() {
        return (monHocList == null) ? 0 : monHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return monHocList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.textName = convertView.findViewById(R.id.textName);
            holder.textDesc = convertView.findViewById(R.id.textDesc);
            holder.imagePic = convertView.findViewById(R.id.imagePic);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu
        MonHoc monHoc = monHocList.get(i);
        holder.textName.setText(monHoc.getName());
        holder.textDesc.setText(monHoc.getDesc());
        holder.imagePic.setImageResource(monHoc.getPic());

        return convertView;
    }

    private static class ViewHolder {
        TextView textName;
        TextView textDesc;
        ImageView imagePic;
    }
}
