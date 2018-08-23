package com.jwei.rad.entity;

import java.io.Serializable;
import java.util.Date;

public class RadTypeConfig implements Serializable {
    private Integer id;

    private Integer typeId;

    private Integer configId;

    private Long createId;

    private String createName;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadTypeConfig that = (RadTypeConfig) o;

        if (!configId.equals(that.configId)) return false;
        if (!typeId.equals(that.typeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId.hashCode();
        result = 31 * result + configId.hashCode();
        return result;
    }
}