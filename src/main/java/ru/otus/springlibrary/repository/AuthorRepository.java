package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.otus.springlibrary.domain.Author;

@CrossOrigin(origins = "*")
public interface AuthorRepository extends PagingAndSortingRepository<Author, ObjectId> {
}
