package com.johnny.cs.date.domain.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@XmlRootElement(name = "header")
@XmlAccessorType(XmlAccessType.FIELD)
public class HolidayResponseHeader {

    @XmlElement(name = "resultCode", required = true)
    private String resultCode;

    @XmlElement(name = "resultMsg", required = true)
    private String resultMsg;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }
}
