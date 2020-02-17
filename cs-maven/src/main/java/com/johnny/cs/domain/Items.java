package com.johnny.cs.domain;


import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {

    @XmlElement(name = "item")
    private List<Item> items;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("items", items)
            .toString();
    }
}
