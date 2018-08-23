package com.jwei.sys.entity;

import java.io.Serializable;

public class SysRoleMenu implements Serializable {
    private Long id;

    private Long roleId;

    private Long menuId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRoleMenu that = (SysRoleMenu) o;

        if (!menuId.equals(that.menuId)) return false;
        if (!roleId.equals(that.roleId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + menuId.hashCode();
        return result;
    }
}