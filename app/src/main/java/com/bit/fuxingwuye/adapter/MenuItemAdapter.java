package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.MenuItem;
import com.BIT.fuxingwuye.views.MenuItemOnClickListener;

import java.util.List;

/**
 * Created by guorui.he on 2016/6/19.
 */
public class MenuItemAdapter extends BaseAdapter{


    private Context context;//运行上下文

    private LayoutInflater listContainer;  //视图容器

    private List<MenuItem> menuItems;

    public MenuItemAdapter(Context _context, List<MenuItem> _menuItems){
        this.context = _context;
        this.listContainer = LayoutInflater.from(_context);
        this.menuItems = _menuItems;
    }
    @Override
    public int getCount() {
        return this.menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= menuItems.size() || position < 0) {
            return null;
        } else {
            return menuItems.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(convertView == null) {
            view = listContainer.inflate(R.layout.menu_item, null);
        }

        MenuItem menuItem = menuItems.get(position);

        TextView textView = (TextView) view.findViewById(R.id.menu_item);
        textView.setText(menuItem.getText());
        textView.setBackgroundResource(R.drawable.blue_btn_selector);

        MenuItemOnClickListener _menuItemOnClickListener =menuItem.getMenuItemOnClickListener();
        if(_menuItemOnClickListener != null) {
            textView.setOnClickListener(_menuItemOnClickListener);
        }
        return view;
    }
}
