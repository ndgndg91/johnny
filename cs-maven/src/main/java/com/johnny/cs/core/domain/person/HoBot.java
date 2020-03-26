package com.johnny.cs.core.domain.person;

import com.johnny.cs.alarm.domain.Template;

public final class HoBot extends Charger{
    private static HoBot hoBot = new HoBot("동호", "821091461117", Template.SEND_TO_HO_BOT);
    private HoBot(){}
    private HoBot(String name, String phone, Template template) {
        super(name, phone, template);
    }

    public static HoBot getInstance(){
        return hoBot;
    }
}
