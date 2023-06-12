package com.hardcore.accounting.model.service;

public class Greeting {
    private final Long id;
    private String name;

//    生成Getter和setter的快捷键: alt + insert
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Greeting(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
