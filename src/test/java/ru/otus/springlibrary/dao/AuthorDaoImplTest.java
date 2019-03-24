package ru.otus.springlibrary.dao;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoImplTest extends BasicTest {

    private static final String FIRST_NAME = "test insert first name";

    private static final String LAST_NAME = "test insert last name";

    @Autowired
    AuthorDaoImpl authorDao;

    @Test
    void findAuthorById() {
        int id = 1;
        Author author = authorDao.findById(id);

        assertEquals(id, author.getId());
        assertEquals("test db first name", author.getFirstName());
        assertEquals("test db last name", author.getLastName());
    }

    @Test
    void findAuthorByWrongId() {
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.findById(Integer.MAX_VALUE));
    }

    @Test
    void insertValidAuthor() {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        Author authorWithGeneratedId = authorDao.insert(author);
        checkAutoGeneratedId(authorWithGeneratedId.getId());

        assertEquals(FIRST_NAME, authorWithGeneratedId.getFirstName());
        assertEquals(LAST_NAME, authorWithGeneratedId.getLastName());
    }

    @Test
    void insertAuthorWithTooLongFirstName() {
        Author authorWithTooLongFirstName = new Author(Strings.repeat("A", 256), LAST_NAME);
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.insert(authorWithTooLongFirstName));
    }

    @Test
    void update() {
        String testUpdateFirstName = "test update first name";
        String testUpdateLastName = "test update last name";

        Author author = authorDao.insert(new Author(FIRST_NAME, LAST_NAME));
        author.setFirstName(testUpdateFirstName);
        author.setLastName(testUpdateLastName);
        Author updatedAuthor = authorDao.update(author);

        checkAutoGeneratedId(author.getId());
        assertEquals(author.getId(), updatedAuthor.getId());
        assertEquals(testUpdateFirstName, updatedAuthor.getFirstName());
        assertEquals(testUpdateLastName, updatedAuthor.getLastName());
    }

    @Test
    void delete() {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        int id = authorDao.insert(author).getId();
        boolean deleteResult = authorDao.delete(id);
        boolean deleteAgainResult = authorDao.delete(id);

        checkAutoGeneratedId(id);
        assertTrue(deleteResult);
        assertFalse(deleteAgainResult);
        assertThrows(Exception.class, () -> authorDao.findById(id));
    }
}