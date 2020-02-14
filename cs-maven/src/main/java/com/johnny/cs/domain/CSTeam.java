package com.johnny.cs.domain;

import java.util.Arrays;

public enum CSTeam {
    HDH("동호"),
    LHE("하은"),
    YNR("나래");

    private String lastName;

    CSTeam(String lastName) {
        this.lastName = lastName;
    }

    public static boolean isNotCSTeam(String lastName) {
        return Arrays.stream(CSTeam.values()).filter(csTeam -> csTeam.lastName.equals(lastName)).findAny().isEmpty();
    }
}
