package com.jwei.rad.entity;

import java.io.Serializable;

public class RadProductDetail implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer configId;

    private Byte configValue;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getConfigValue() {
        return configValue;
    }

    public void setConfigValue(Byte configValue) {
        this.configValue = configValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadProductDetail that = (RadProductDetail) o;

        if (!configId.equals(that.configId)) return false;
        if (!configValue.equals(that.configValue)) return false;
        if (!productId.equals(that.productId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + configId.hashCode();
        result = 31 * result + configValue.hashCode();
        return result;
    }
}