package com.biubiu.kit.core;

/**
 * Kit Factory
 * Created by looa on 2018/1/11.
 */

interface IKitFactory {

    void map();

    Object create(String type);
}
