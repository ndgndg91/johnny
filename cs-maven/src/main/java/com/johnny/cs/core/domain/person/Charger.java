package com.johnny.cs.core.domain.person;

import com.johnny.cs.alarm.domain.Template;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Charger {
    protected String name;
    protected String phone;
    protected Template template;

    public Charger(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
