package com.johnny.cs.domain;

public enum Charger {
    LSM("상민"),
    CUC("의철"),
    KGN("경남"),
    SJY("주영"),
    LSH("승현"),
    LSH_D("승환"),
    LJS("재석"),
    LSB("석봉"),
    AHG("홍근"),
    JSW("선우"),
    LKS("기승"),
    CWS("우석"),
    NDG("동길"),
    LJH("지혜"),
    KYJ("용진");

    private String lastName;

    Charger(String lastName) {
        this.lastName = lastName;
    }
}
