package com.biubiu.kit.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biubiu.kit.api.R;

/**
 * UI组件接口
 * Created by looa on 2018/1/11.
 */

public abstract class AbsKit {

    protected AbsKit() {
    }

    public static View inflateView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
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
     * @param data View对应的数据
     */
    public abstract void bind(Object data);

}
