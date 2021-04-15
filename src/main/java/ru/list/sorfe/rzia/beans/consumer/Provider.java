package ru.list.sorfe.rzia.beans.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Provider {
    @Id
    private final TpIc id;
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
}

