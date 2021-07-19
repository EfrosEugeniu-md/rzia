package ru.list.sorfe.rzia.beans;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
public class SetOfDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String description;
    LocalDate dateOfCreate;
    @OneToMany(mappedBy = "setOfDocuments", fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    public SetOfDocuments(LocalDate localDate) {
        this.dateOfCreate=localDate;
    }
}
