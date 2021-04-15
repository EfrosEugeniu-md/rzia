package ru.list.sorfe.rzia.beans.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "consumer")
public class Consumer {
    @Id
    private final TpIc id;
    @AttributeOverrides({
            @AttributeOverride(name = "tpNumber", column = @Column(name = "tpNumber_id")),
            @AttributeOverride(name = "tpType", column = @Column(name = "tpType_id")),
            @AttributeOverride(name = "ic", column = @Column(name = "ic_id"))}
    )
    private final TpIc tpIc;
    private final double kzMax;
    private final double kzMin;
    private final AggregatesOfConsumer x;
    private final double kz;
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "tpNumber", referencedColumnName = "tpNumber"),
            @JoinColumn(name = "tpType", referencedColumnName = "tpType"),
            @JoinColumn(name = "ic", referencedColumnName = "ic")
    })
    private Provider provider;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "tpNumber", referencedColumnName = "tpNumber"),
            @JoinColumn(name = "tpType", referencedColumnName = "tpType"),
            @JoinColumn(name = "ic", referencedColumnName = "ic")
    })
    private Cabel cabel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tpNumber_st", referencedColumnName = "tpNumber")
    private Station station;
}
