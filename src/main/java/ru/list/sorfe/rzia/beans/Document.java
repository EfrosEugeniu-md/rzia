package ru.list.sorfe.rzia.beans;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIc tpIc;
    LocalDate dateOfWork;
    TypeOfDocument typeOfDocument;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setOfDocuments_st", referencedColumnName = "id")
    private SetOfDocuments setOfDocuments;

    public Document(TpIc tpIc, LocalDate now, SetOfDocuments setOfDocuments, TypeOfDocument typeOfDocument) {
        this.tpIc = tpIc;
        this.dateOfWork = now;
        this.setOfDocuments = setOfDocuments;
        this.typeOfDocument = typeOfDocument;
    }

    public static enum TypeOfDocument {
        CELL, CONS
    }
}
