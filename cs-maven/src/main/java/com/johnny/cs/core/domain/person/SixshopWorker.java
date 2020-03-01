package com.johnny.cs.core.domain.person;

public enum SixshopWorker {
    LSM(new Charger("상민", "01091710956"), true),
    CUC(new Charger("의철", "01094981469"), true),
    SYM(new Charger("윤모", "01033850233"), true),
    KKN(new Charger("경남", "01073678167"), true),
    SJY(new Charger("주영", "01041420285"), true),
    LSJ(new Charger("승현", "01083610420"), true),
    LSH(new Charger("승환", "01043317026"), true),
    LJS(new Charger("재석", "01091658447"), true),
    AHG(new Charger("홍근", "01050949036"), true),
    HDH(new Charger("동호", "01091461117"), true),
    NDG(new Charger("동길", "01072255198"), true),
    LKS(new Charger("기승", "01024991180"), true),
    LJH(new Charger("지혜", "01094967434"), true),
    YNR(new Charger("나래", "01099117319"), true),
    HEB(new Charger("은비", "01090633308"), true),
    LSB(new Charger("석봉", "01067899444"), true),
    LHE(new Charger("하은", "01089196507"), true),
    JSW(new Charger("선우", "01033350548"), true),
    CWS(new Charger("우석", "01083121478"), true),
    SYJ(new Charger("영지", "4044012013"), false),
    KYJ(new Charger("용진", "01041797900"), true),
    KSJ(new Charger("선진", "01093605445"), true);

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
