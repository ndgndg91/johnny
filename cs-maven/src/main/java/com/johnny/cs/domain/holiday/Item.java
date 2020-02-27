package com.johnny.cs.domain.holiday;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlElement(name = "dateKind", required = true)
    private String dateKind;

    @XmlElement(name = "dateName", required = true)
    private String dateName;

    @XmlElement(name = "isHoliday", required = true)
    private String isHoliday;

    @XmlElement(name = "locdate", required = true)
    private String locdate;

    @XmlElement(name = "seq", required = true)
    private int seq;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }
}
