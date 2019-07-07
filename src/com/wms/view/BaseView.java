package com.wms.view;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 15:52<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public abstract class BaseView {

    protected Double width;

    protected Double height;

    public BaseView(Double width, Double height) {
        this.width = width;
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public abstract void show();

}
