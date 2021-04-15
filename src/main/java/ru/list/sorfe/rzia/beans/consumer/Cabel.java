package ru.list.sorfe.rzia.beans.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Cabel {
    @Id
    private final TpIc id;
    private final Integer s;
    private final Integer l;
    @OneToOne(mappedBy = "cabel")
    private Consumer consumer;
}
