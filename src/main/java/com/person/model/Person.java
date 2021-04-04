package com.person.model;

import lombok.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "person")
public class Person {
    private Long id;
    private String name;
    private String gender;
    private Long birthYear;
    private Date created_date_time;


    public String msg() {
        String age = "лет\"";

        if (birthYear < 121){
            if(birthYear > 10 && birthYear < 15 || birthYear %10 == 0 || birthYear %10 > 4 &&
                    birthYear %10 <= 9 || birthYear > 110 && birthYear < 115 )
            {
                age = " лет\"";
            }
            else if(birthYear % 10 == 1 && birthYear % 100 == 1)
            {
                age = " год.\"";
            }
            else if(birthYear %10 > 1 && birthYear %10 < 5)
            {
                age = " года.\"";
            }
        }

        if (gender.equals("m")){ name = "\"Уважаемый " + name ; }
        else if(gender.equals("f")){ name = "\"Уважаемая " + name; }
        else { return "error gender";}

        return name + ", ваш год рождения " + birthYear + ", вам " + (2021 - birthYear) + age;
     }
}
