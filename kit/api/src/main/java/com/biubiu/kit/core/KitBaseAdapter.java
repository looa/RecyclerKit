package com.biubiu.kit.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 基础的 Adapter
 * Created by looa on 2018/1/11.
 */

public class KitBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final IKitFactory kitFactory;
    private final List<Object> list;
    // 存储item的类型，type为item对应kit的className
    private final SparseArray<String> typeArray;
    private int index = 9;

    private OnItemClickListener listener;

    public KitBaseAdapter(Context context, List<Object> list) {
        this(context, list, context.getApplicationInfo().processName);
    }

    public KitBaseAdapter(Context context, List<Object> list, String application) {
        this.list = list;
        typeArray = new SparseArray<>();
        kitFactory = KitFactory.newInstant(application);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        String type = typeArray.get(viewType);
        AbsKit kit = (AbsKit) kitFactory.create(type);
        kit.onCreate(this);
        return new Holder(kit, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            AbsKit chatKit = ((Holder) holder).getChatKit();
            chatKit.bind(getRealPosition(position), list.get(getRealPosition(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        //获取kit组件的className
        String type = KitFactory.translate(list.get(getRealPosition(position)).getClass());
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

    public int getRealPosition(int position) {
        return position;
    }


    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    OnItemClickListener listener() {
        return listener;
    }

    boolean hasListener() {
        return listener != null;
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
