package com.biubiu.sample;

import android.view.View;
import android.view.ViewGroup;

import com.biubiu.kit.annotation.Kit;
import com.biubiu.kit.core.AbsKit;
import com.biubiu.recyclerkit.R;

/**
 * title
 * Created by looa on 2018/4/12.
 */

public class CardTextKit extends AbsKit {
    @Override
    public View getKit(ViewGroup viewGroup) {
        return inflateView(viewGroup, R.layout.item_card_text);
    }

    @Override
    public void bind(Object data) {

    }

    @Kit(ui = CardTextKit.class)
    public static class Data {

    }
}
