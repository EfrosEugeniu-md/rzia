package ru.list.sorfe.rzia.beans;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
@Table
public class Station {
    @Id
    private final Integer tpNumber;
    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private List<StationAndCellular> stationAndCellulars = new ArrayList<>();
    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private List<Consumer> consumers = new ArrayList<>();
}
