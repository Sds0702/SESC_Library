package uk.ac.leedsbeckett.Library.Portal.Controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;
import uk.ac.leedsbeckett.Library.Portal.model.Book;
import uk.ac.leedsbeckett.Library.Portal.model.BookRepository;

import javax.management.InvalidApplicationException;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;
import java.security.cert.CertPathValidatorSpi;
import java.util.EmptyStackException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static java.nio.file.Files.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    CertPathValidatorSpi = {'isbn': '9781231231231', 'title': 'Book Title', 'author': 'An Author', 'year': 2020, 'copies':2 };
    InvalidApplicationException = {'isbn': '9', 'title': 232, 'author': 100, 'year': 'TwentyTwenty', 'copies':-1};
    EmptyStackException = '';
    @BeforeEach
    public void setUp() {
        Book account1 = new Book("9781231231231", "Book Title", "An Author", "2020", "2");
        account1.setBookIsbn(1L);
        Book account2 = new Book("9","232","100","TwentyTwenty","-1");
        account2.setBookIsbn(2L);
        List.of(account1, account2).equals(bookRepository);
    }

    @Test
    public void test_create_book_with_individual_valid_args_creates_Book() throws Exception {
        mvc.perform(get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.bookIsbn").value("9781231231231"))
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.author").value("An Author"))
                .andExpect(jsonPath("$.year").value("2020"))
                .andExpect(jsonPath("$.copies").value("2"));
    }

    @Test
    public void b_givenBook_whenDelete_thenStatus204() throws Exception {
        mvc.perform(delete("/book/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_create_book_with_dictionary_of_valid_args_creates_Book() throws Exception {
        mvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.bookList[0].bookIsbn").value("BookIsbn"))
                .andExpect(jsonPath("$._embedded.bookList[1].title").value("title"))
                .andExpect(jsonPath("$._embedded.bookList[2].author").value("author"))
                .andExpect(jsonPath(".$._embedded.bookList[3].year").value("year"))
                .andExpect(jsonPath(".$._embedded.bookList[4].copies").value("copies"));
    }

    @Test
    public void givenNoBooks_whenGetBooks_thenStatus200_andLinkToSelf() throws Exception {
        bookRepository.deleteAll();
        mvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._links.self.href").isNotEmpty());
    }


   /* @Test
    public void givenNoAccount_whenGetAccountById_thenStatus404() throws Exception {
        mvc.perform(get("/books/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAccount_whenGetAccountByStudentId_thenStatus200() throws Exception {
        mvc.perform(get("/accounts/student/c6666666")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.studentId").value("c6666666"));
    }

    @Test
    public void givenNoAccount_whenGetAccountByStudentId_thenStatus404() throws Exception {
        mvc.perform(get("/accounts/student/c0000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNoAccount_whenPostNewAccount_thenStatus201() throws Exception {
        mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\": \"c3429928\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value("c3429928"))
                .andExpect(jsonPath("$.hasOutstandingBalance").value(false));
    }

    @Test
    public void givenExistingAccount_whenPostNewAccount_thenStatus422() throws Exception {
        mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\": \"c6666666\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("An account already exists for student ID c6666666."));
    }

    @Test
    public void whenPostNewAccount_withEmptyAccountValue_thenStatus422() throws Exception {
        mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\": \"\"}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void whenPostNewAccount_withEmptyJson_thenStatus400() throws Exception {
        mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNoAccount_whenDelete_thenStatus404() throws Exception {
        mvc.perform(delete("/accounts/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find account 1000"));
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }*/
}
