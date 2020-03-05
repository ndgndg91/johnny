package com.johnny.cs.core.domain.person;

public enum SixshopWorker {
    LSM(new Charger("상민", "821091710956"), true),
    CUC(new Charger("의철", "821094981469"), true),
    SYM(new Charger("윤모", "821033850233"), true),
    KKN(new Charger("경남", "821073678167"), true),
    SJY(new Charger("주영", "821041420285"), true),
    LSJ(new Charger("승현", "821083610420"), true),
    LSH(new Charger("승환", "821043317026"), true),
    LJS(new Charger("재석", "821091658447"), true),
    AHG(new Charger("홍근", "821050949036"), true),
    HDH(new Charger("동호", "821091461117"), true),
    NDG(new Charger("동길", "82072255198"), true),
    LKS(new Charger("기승", "82024991180"), true),
    LJH(new Charger("지혜", "82094967434"), true),
    YNR(new Charger("나래", "82099117319"), true),
    HEB(new Charger("은비", "82090633308"), true),
    LSB(new Charger("석봉", "82067899444"), true),
    LHE(new Charger("하은", "82089196507"), true),
    JSW(new Charger("선우", "82033350548"), true),
    CWS(new Charger("우석", "82083121478"), true),
    SYJ(new Charger("영지", "4044012013"), false),
    KYJ(new Charger("용진", "82041797900"), true),
    KSJ(new Charger("선진", "82093605445"), true);

    private Charger charger;
    private boolean isDomestic;

    SixshopWorker(Charger charger, boolean isDomestic) {
        this.charger = charger;
        this.isDomestic = isDomestic;
    }

    public Charger getCharger() {
        return charger;
    }
}
