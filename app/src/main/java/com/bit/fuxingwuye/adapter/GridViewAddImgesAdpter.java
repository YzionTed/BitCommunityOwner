package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.BIT.fuxingwuye.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yuandl on 2015/12/24.
 *         添加上传图片适配器
 */
public class GridViewAddImgesAdpter extends BaseAdapter {
    private List<Map<String, Object>> datas;
    private Context context;
    private LayoutInflater inflater;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 4;

    public GridViewAddImgesAdpter(List<Map<String, Object>> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    public List<Bitmap> getDatas(){
        List<Bitmap> list = new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            Bitmap bitmap = (Bitmap) datas.get(i).get("path");
            list.add(bitmap);
        }
        return list;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataSetChanged(List<Map<String, Object>> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datas != null && position < datas.size()) {
//            final File file = new File(datas.get(position).get("path").toString());
//            Glide.with(context)
//                    .load(file)
//                    .priority(Priority.HIGH)
//                    .into(viewHolder.ivimage);
            final Bitmap bitmap = (Bitmap) datas.get(position).get("path");
            viewHolder.ivimage.setImageBitmap(bitmap);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (file.exists()) {
//                        file.delete();
//                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {

            Glide.with(context)
                    .load(R.mipmap.icon_plus)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
        }

        return convertView;

    }

    public class ViewHolder {
        public final ImageView ivimage;
        public final Button btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_image);
            btdel = (Button) root.findViewById(R.id.bt_del);
            this.root = root;
        }
    }
}
