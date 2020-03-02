package com.johnny.cs.core.domain.person;

import com.johnny.cs.alarm.domain.Template;

public class RotationCharger extends Charger {
    public RotationCharger(String name, String phone, Template template) {
        super.name = name;
        super.phone = phone;
        super.template = template;
    }
}
