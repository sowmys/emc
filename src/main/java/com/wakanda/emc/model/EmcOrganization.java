package com.wakanda.emc.model;

public class EmcOrganization {
    private String name;
    private String creator;
    private String description;
    private long id;

    public EmcOrganization(String name, String description, String creator) {
        this(name, description, creator, -1);
    }

    public EmcOrganization(String name, String description, String creator, long id) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.id = id;
    }

    public EmcOrganization() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long nextId) {
        this.id = nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
