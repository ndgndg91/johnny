package com.johnny.cs.core.domain.person;

import com.johnny.cs.alarm.domain.Template;

public class NighttimeCharger extends Charger {
    public NighttimeCharger(String name, String phone, Template template) {
        super.name = name;
        super.phone = phone;
        super.template = template;
    }
}
