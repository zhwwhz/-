package com.FreeMarket;

public class Person {
    private String name;
    private int agr;
    private String sex;

    public Person(String name, int agr, String sex) {
        this.name = name;
        this.agr = agr;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgr() {
        return agr;
    }

    public void setAgr(int agr) {
        this.agr = agr;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
