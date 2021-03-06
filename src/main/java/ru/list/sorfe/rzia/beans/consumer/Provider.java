package ru.list.sorfe.rzia.beans.consumer;

import lombok.*;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIc tpIc;
    @Column(name = "_power")
    private final int power;
    private final int mtzI;
    private final double mtzT;
    @Column(name = "_o")
    private final int to;
    private final double kzMax;
    private final double kzMin;
    @OneToOne(mappedBy = "provider")
    private Consumer consumer;

    public String toString() {
        return "Provider is " + this.id + "from " + this.getConsumer().getId().toString();
    }
}

