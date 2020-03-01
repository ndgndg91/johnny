package com.johnny.cs.core.domain;

import java.util.Arrays;

public enum CSTeam {
    HDH("동호"),
    LHE("하은"),
    YNR("나래");

    private String lastName;

    CSTeam(String lastName) {
        this.lastName = lastName;
    }

    public static boolean isNotCSTeam(Charger charger) {
        return Arrays.stream(CSTeam.values())
                .filter(csTeam -> csTeam.lastName.equals(charger.getName()))
                .findAny()
                .isEmpty();
    }

    public static boolean isNotCSTeam(String name) {
        return Arrays.stream(CSTeam.values())
                .filter(csTeam -> csTeam.lastName.equals(name))
                .findAny()
                .isEmpty();
    }
}
