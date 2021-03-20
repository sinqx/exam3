package com.person.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "Requests")
public class Requests {
    Long id;
    String requestName;
    String name;
    Long birthYear;
    String gender;
    Date requestDateTime;
}
