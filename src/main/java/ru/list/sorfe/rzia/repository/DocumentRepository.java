package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import ru.list.sorfe.rzia.beans.Document;

public interface DocumentRepository  extends CrudRepository<Document, Long> {
}
