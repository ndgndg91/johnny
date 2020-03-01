package com.johnny.cs.core.domain.person;

import java.util.Arrays;

public enum CSTeam {
    HDH("동호", "010-9146-1117"),
    LHE("하은", "010-8919-6507"),
    YNR("나래", "010-9911-7319");

    private String lastName;
    private String phone;

    CSTeam(String lastName, String phone) {
        this.lastName = lastName;
        this.phone = phone;
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
