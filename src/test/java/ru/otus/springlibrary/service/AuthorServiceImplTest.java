package ru.otus.springlibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.domain.Author;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
class AuthorServiceImplTest {

    private static final String FIRST_NAME = "test first name";

    private static final String LAST_NAME = "test last name";

    @Autowired
    AuthorService authorService;

    @MockBean
    AuthorDao authorDao;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author(1, FIRST_NAME, LAST_NAME);
        when(authorDao.getAllAuthors()).thenReturn(Collections.singletonList(author));
    }

    @Test
    void getAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthors();

        assertEquals(1, allAuthors.size());
        Author author = allAuthors.get(0);
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
    }

    @Test
    void addNewValidAuthor() {
        when(authorDao.insert(any())).thenReturn(author);
        assertTrue(authorService.addAuthor(FIRST_NAME, LAST_NAME));
    }

    @Test
    void tryToAddAlreadyExistingAuthor() {
        when(authorDao.insert(any())).thenThrow(DuplicateKeyException.class);
        assertFalse(authorService.addAuthor(FIRST_NAME, LAST_NAME));
    }
}