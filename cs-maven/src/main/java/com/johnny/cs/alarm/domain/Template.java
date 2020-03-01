package com.johnny.cs.alarm.domain;

public enum Template {

    SEND_TOMORROW_WEEKLY_CHARGER("group_six_cs_1", "쟈니_주간_하루 전", "#{CS_NAME}님, 내일은 주간 CS 담당일입니다. 잊지 마세요!(굿)"),
    SEND_TODAY_WEEKLY_CHARGER("group_six_cs_2", "쟈니_주간_당일 정시", "#{CS_NAME}님, 오늘은 주간 CS 담당일입니다. 잘 부탁드려요!(씨익)"),
    SEND_TODAY_NIGHTTIME_CHARGER("group_six_cs_3", "쟈니_야간_5분 전", "#{CS_NAME}님, 오늘 야간 CS 담당일입니다. 수당을 위해 달리세요!(컴온)"),

    SEND_TOMORROW_HOLIDAY_CHARGER("group_six_cs_4", "쟈니_휴일_하루 전", "#{CS_NAME}님, 내일은 휴일 CS 담당일입니다. 잊지 마세요! :)(굿)"),
    SEND_TODAY_HOLIDAY_CHARGER("group_six_cs_5", "쟈니_휴일_5분 전", "5분 전: #{CS_NAME}님, 오늘 휴일 CS 담당일입니다. 수당을 위해 달리세요!(컴온)"),
    SEND_UN_REPLIED_MAIL_CHARGER("group_six_cs_6", "쟈니_야간휴일_미답변 쟈니..?", "#{CS_NAME}님, 30분째 답장하지 않은 고객 문의가 있습니다.\n    소중한 수당을 잃을 수 있어요. 얼른 확인하세요!!(심각)"),
    SEND_CS_END_CHARGER("group_six_cs_8", "쟈니_야간휴일_종료2", "(경)이것을 마무리로 CS 수당을 지켜냈습니다.(축)\n    CS 담당 시간이 종료되었어요. #{CS_NAME}님, 오늘도 고생 많았습니다!(뽀뽀)");

    private String templateCode;
    private String templateName;
    private String pattern;

    Template(String templateCode, String templateName, String pattern) {
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.pattern = pattern;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getPattern() {
        return pattern;
    }
}
