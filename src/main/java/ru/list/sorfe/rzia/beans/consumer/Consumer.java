package ru.list.sorfe.rzia.beans.consumer;

import lombok.*;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;


import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIc tpIc;
    private final double kzMax;
    private final double kzMin;
    private final AggregatesOfConsumer x;
    private final double kz;
    private final int dispatcherAddress;
    private final TypeOfServiceZonal serviceZonal;
    @OneToOne
    private Provider provider;
    @OneToOne
    private Cabel cabel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tpNumber_st", referencedColumnName = "tpNumber")
    private Station station;

    public static enum TypeOfServiceZonal {
        Centr, Scule, Munch, Buicn, Botan, Cecan, None
    }

    @Override
    public String toString() {

        return "Consumer is " + this.id.toString() + "whit " + this.x.getNumberOfAggregates() + "Aggregates.";
    }
}
