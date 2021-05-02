package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.StCellRollPhasa;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
public class StationAndCellular {
    @Id
    private final TpIc id;
    private final StCellRollPhasa.TypeOfSecurityElement typeOfSecurityElement;
    private final Integer numberOfSecurityElement;
    @OneToMany(mappedBy = "stationAndCellular", fetch = FetchType.LAZY)
    private List<Rely> relies = new ArrayList<>();
    @OneToMany(mappedBy = "stationAndCellular", fetch = FetchType.LAZY)
    private List<TransformationCurrent> transformationCurrents = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tpNumber_id", referencedColumnName = "tpNumber")
    private Station station;

    @Override
    public String toString() {

        assert this.id != null;
        StringBuilder stringBuilder = (new StringBuilder("This is " + this.id.toString()));
        if (this.typeOfSecurityElement == StCellRollPhasa.TypeOfSecurityElement.TP) {
            stringBuilder.append("Security Element is  line off the reserve to ST").append(this.numberOfSecurityElement);
        }

        if (this.typeOfSecurityElement == StCellRollPhasa.TypeOfSecurityElement.AGR) {
            stringBuilder.append("Security Element is aggregate nr.").append(this.numberOfSecurityElement);
        }

        stringBuilder.append("\n");

        for (Rely rely : this.getRelies()) {
            stringBuilder.append(rely.toString());
        }

        for (TransformationCurrent transformationCurrent : this.getTransformationCurrents()) {
            stringBuilder.append(transformationCurrent.toString());
        }

        return stringBuilder.toString();
    }
}

