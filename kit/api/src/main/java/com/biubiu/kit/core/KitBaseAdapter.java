package com.biubiu.kit.core;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 基础的 Adapter
 * Created by looa on 2018/1/11.
 */

public class KitBaseAdapter extends RecyclerView.Adapter {

    private IKitFactory kitFactory;
    private List<Object> list;
    private SparseArray<String> typeArray;//存储item的类型，type为item对应kit的className
    private int index = 9;

    public KitBaseAdapter(List<Object> list) {
        this.list = list;
        typeArray = new SparseArray<>();
        kitFactory = KitFactory.newInstant();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String type = typeArray.get(viewType);
        return new Holder((AbsKit) kitFactory.create(type), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            AbsKit chatKit = ((Holder) holder).getChatKit();
            chatKit.bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        //获取kit组件的className
        String type = KitFactory.translate(list.get(position).getClass());
        int index = typeArray.indexOfValue(type);
        if (index == -1) {
            typeArray.put(this.index, type);
            index = this.index;
            this.index++;
        } else {
            return typeArray.keyAt(index);
        }
        return index;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private static class Holder extends RecyclerView.ViewHolder {

        private AbsKit chatKit;

        private Holder(View itemView) {
            super(itemView);
        }

        Holder(AbsKit kit, ViewGroup viewGroup) {
            this(kit.getKit(viewGroup));
            this.chatKit = kit;
        }

        AbsKit getChatKit() {
            return chatKit;
        }
    }
}
