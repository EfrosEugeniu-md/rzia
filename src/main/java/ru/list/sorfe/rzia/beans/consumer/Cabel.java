package ru.list.sorfe.rzia.beans.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Cabel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIc tpIcProvider;
    @AttributeOverrides({
            @AttributeOverride(name = "tpNumber", column = @Column(name = "tpNumber_c")),
            @AttributeOverride(name = "tpType", column = @Column(name = "tpType_c")),
            @AttributeOverride(name = "ic", column = @Column(name = "ic_c"))}
    )
    private final TpIc tpIcConsumer;
    private final Integer sectionOfCable;
    private final Integer lengthOfCable;
    @OneToOne(mappedBy = "cabel")
    private Consumer consumer;
}
