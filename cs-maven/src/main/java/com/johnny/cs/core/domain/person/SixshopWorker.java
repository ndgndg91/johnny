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
    NDG(new Charger("동길", "821072255198"), true),
    LKS(new Charger("기승", "821024991180"), true),
    LJH(new Charger("지혜", "821094967434"), true),
    YNR(new Charger("나래", "821099117319"), true),
    HEB(new Charger("은비", "821090633308"), true),
    LSB(new Charger("석봉", "821067899444"), true),
    LHE(new Charger("하은", "821089196507"), true),
    JSW(new Charger("선우", "821033350548"), true),
    CWS(new Charger("우석", "821083121478"), true),
    SYJ(new Charger("영지", "14044012013"), false),
    KSJ(new Charger("선진", "821093605445"), true),
    PSH(new Charger("세호", "821091346807"), true),
    LDG(new Charger("동국", "821051048003"), true),
    LHB(new Charger("한별", "821085715939"), true),
    CNS(new Charger("나실", "821052620702"), true),
    KJB(new Charger("정빈", "821067613095"), true),

    LSM_HOLIDAY(new Charger("상민(휴)", "821091710956"), true),
    CUC_HOLIDAY(new Charger("의철(휴)", "821094981469"), true),
    SYM_HOLIDAY(new Charger("윤모(휴)", "821033850233"), true),
    KKN_HOLIDAY(new Charger("경남(휴)", "821073678167"), true),
    SJY_HOLIDAY(new Charger("주영(휴)", "821041420285"), true),
    LSJ_HOLIDAY(new Charger("승현(휴)", "821083610420"), true),
    LSH_HOLIDAY(new Charger("승환(휴)", "821043317026"), true),
    LJS_HOLIDAY(new Charger("재석(휴)", "821091658447"), true),
    AHG_HOLIDAY(new Charger("홍근(휴)", "821050949036"), true),
    HDH_HOLIDAY(new Charger("동호(휴)", "821091461117"), true),
    NDG_HOLIDAY(new Charger("동길(휴)", "821072255198"), true),
    LKS_HOLIDAY(new Charger("기승(휴)", "821024991180"), true),
    LJH_HOLIDAY(new Charger("지혜(휴)", "821094967434"), true),
    YNR_HOLIDAY(new Charger("나래(휴)", "821099117319"), true),
    HEB_HOLIDAY(new Charger("은비(휴)", "821090633308"), true),
    LSB_HOLIDAY(new Charger("석봉(휴)", "821067899444"), true),
    LHE_HOLIDAY(new Charger("하은(휴)", "821089196507"), true),
    JSW_HOLIDAY(new Charger("선우(휴)", "821033350548"), true),
    CWS_HOLIDAY(new Charger("우석(휴)", "821083121478"), true),
    SYJ_HOLIDAY(new Charger("영지(휴)", "14044012013"), false),
    KSJ_HOLIDAY(new Charger("선진(휴)", "821093605445"), true),
    PSH_HOLIDAY(new Charger("세호(휴)", "821091346807"), true),
    LDG_HOLIDAY(new Charger("동국(휴)", "821051048003"), true),
    LHB_HOLIDAY(new Charger("한별(휴)", "821085715939"), true),
    CNS_HOLIDAY(new Charger("나실(휴)", "821052620702"), true),
    KJB_HOLIDAY(new Charger("정빈(휴)", "821067613095"), true);

    private final Charger charger;
    private final boolean isDomestic;

    SixshopWorker(Charger charger, boolean isDomestic) {
        this.charger = charger;
        this.isDomestic = isDomestic;
    }

    public Charger getCharger() {
        return charger;
    }
}
