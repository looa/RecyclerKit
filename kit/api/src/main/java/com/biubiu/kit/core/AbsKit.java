package com.biubiu.kit.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * UI组件接口
 * Created by looa on 2018/1/11.
 */

public abstract class AbsKit {

    private KitBaseAdapter adapter;

    protected AbsKit() {
    }

    public static View inflateView(LayoutInflater inflater, ViewGroup viewGroup, int layoutId) {
        if (inflater == null) inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(layoutId, viewGroup, false);
    }

    public static View inflateView(ViewGroup viewGroup, int layoutId) {
        return inflateView(null, viewGroup, layoutId);
    }

    /**
     * 创建Kit的时候绑定adapter
     *
     * @param adapter 宿主adapter
     */
    public void onCreate(KitBaseAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 获取组件
     *
     * @return 自定义样式和功能的View
     */
    public abstract View getKit(ViewGroup viewGroup);

    /**
     * 绑定或者更新数据
     *
     * @param position View对应的位置
     * @param data     View对应的数据
     */
    public abstract void bind(int position, Object data);

    /**
     * 判断宿主adapter是否设定了OnItemClickListener
     *
     * @return has listener
     */
    protected boolean hasListener() {
        return adapter != null && adapter.hasListener();
    }

    /**
     * 获取宿主adapter的OnItemClickListener
     *
     * @return OnItemClickListener
     */
    protected OnItemClickListener listener() {
        return adapter == null ? null : adapter.listener();
    }

}
