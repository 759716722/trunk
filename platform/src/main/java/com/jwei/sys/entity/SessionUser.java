package com.jwei.sys.entity;

import java.io.Serializable;

/**
 * Created by wyb on 2018/6/5.
 */
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 7513135428972265449L;

    private Long id;

    private String name;

    private String loginName;

    private Long deptId;

    private String deptName;

    public SessionUser(Long id, String name, String loginName, Long deptId, String deptName) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLoginName() {
        return loginName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getDeptName() {
        return deptName;
    }
}
