package com.wakanda.emc.model;

public class Membership {
    private long id;
    private long organizationId;
    private long userId;
    private EmcMemberStatus status;
    private EmcRole role;

    public Membership(long organizationId, long userId) {
        this(userId, organizationId, -1);
    }

    public Membership(long id, long organizationId, long userId) {
        this.id = id;
        this.organizationId = organizationId;
        this.userId = userId;
    }

    public Membership() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public EmcMemberStatus getStatus() {
        return status;
    }

    public void setStatus(EmcMemberStatus status) {
        this.status = status;
    }

    public EmcRole getRole() {
        return role;
    }

    public void setRole(EmcRole role) {
        this.role = role;
    }
}
