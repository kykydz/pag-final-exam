package org.app.uas.entity;

public class Student {
    private String nik;
    private String name;
    private String address;
    private String sex;
    private int age;

    // Default constructor
    public Student() {}

    // Parameterized constructor
    public Student(String nik, String name, String address, String sex, int age) {
        this.nik = nik;
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.age = age;
    }

    // Getter and Setter methods
    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
