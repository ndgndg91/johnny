package com.johnny.cs.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@XmlRootElement(name = "response")
public class HolidayResponse {

    @XmlElement(name = "header", required = true)
    private HolidayResponseHeader header;

    @XmlElement(name = "body", required = true)
    private HolidayResponseBody body;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }
}
