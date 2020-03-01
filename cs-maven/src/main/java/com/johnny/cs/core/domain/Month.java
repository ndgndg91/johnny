package com.johnny.cs.core.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum  Month {
    /**
     * 2020년 CS 일정
     * index 0 : [, 월, , , , , , 화, , , , , , 수, , , , , , 목, , , , , , 금, , , , , , 토, , 일]
     * index 1 : [, , 전담, , , 교대, 야간, , 전담, , , 교대, 야간, , 전담, , , 교대, 야간, , 전담, , , 교대, 야간, , 전담, , , 교대, 야간, 휴일전체, , 휴일전체]
     * index 2~6 : 1월 담당자
     * index 7~11 : 2월 담당자
     * index 12~17 : 3월 담당자
     * index 18~22 : 4월 담당자
     * index 23~27 : 5월 담당자
     * index 28~32 : 6월 담당자
     * index 33~37 : 7월 담당자
     * index 38~43 : 8월 담당자
     * index 44~48 : 9월 담당자
     * index 49~53 : 10월 담당자
     * index 54~59 : 11월 담당자
     * index 60~64 : 12월 담당자
     */
    JAN(1, 2, 6, 3, 7),
    FEB(2, 7, 11, 8, 12),
    MAR(3, 12, 17, 13, 18),
    APR(4, 18, 22, 19, 23),
    MAY(5, 23, 27, 24, 28),
    JUN(6, 28, 32, 29, 33),
    JUL(7, 33, 37, 34, 38),
    AUG(8, 38, 43, 39, 44),
    SEP(9, 44, 48, 45, 49),
    OCT(10, 49, 53, 50, 54),
    NOV(11, 54, 59, 55, 60),
    DEC(12, 60, 64, 61, 65);

    private int value;
    private int startIndex;
    private int endIndex;

    private int startRowIndex;
    private int endRowIndex;

    Month(int value, int startIndex, int endIndex, int startRowIndex, int endRowIndex) {
        this.value = value;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startRowIndex = startRowIndex;
        this.endRowIndex = endRowIndex;
    }

    public static int getRowIndex(){
        LocalDate now = LocalDate.now();
        return Arrays.stream(Month.values()).filter(mon -> mon.value == now.getMonthValue()).findFirst().orElse(JAN).startRowIndex;
    }
}
