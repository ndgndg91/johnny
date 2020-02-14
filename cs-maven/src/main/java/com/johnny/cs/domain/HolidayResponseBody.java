package com.johnny.cs.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class HolidayResponseBody {

    @XmlElement(name = "items", required = true)
    private Items items;

    @XmlElement(name = "numOfRows", required = true)
    private int numOfRows;

    @XmlElement(name = "pageNo", required = true)
    private int pageNo;

    @XmlElement(name = "totalCount", required = true)
    private int totalCount;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }
}
