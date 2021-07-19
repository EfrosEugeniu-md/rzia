package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.consumer.Consumer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private TypeOfPosition typeOfPosition;
    private Consumer.TypeOfServiceZonal typeOfServiceZonal;

    public Personal(){}

    public Personal(String name, TypeOfPosition typeOfPosition, Consumer.TypeOfServiceZonal typeOfServiceZonal) {
        this.name = name;
        this.typeOfPosition = typeOfPosition;
        this.typeOfServiceZonal = typeOfServiceZonal;
    }

    public static enum TypeOfPosition {
        MASTER, SEF_GEN, SEF_ADJ, SEF_BRD, CONTROL, MEMBER
    }
}
