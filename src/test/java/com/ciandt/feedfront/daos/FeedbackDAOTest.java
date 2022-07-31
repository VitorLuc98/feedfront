package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackDAOTest {
    private Feedback feedback;
    private DAO<Feedback> feedbackDAO;

    private Employee employeeAutor;
    private Employee employeeProprietario;

    private final LocalDate data = LocalDate.now();
    private final String DESCRICAO = "Texto de descrição do feedback";

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        Files.walk(Paths.get("src/main/resources/data/feedback/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        employeeAutor = new Employee("Isabela", "Santos", "isa@gmail.com");
        employeeProprietario = new Employee("Marcelo", "Turing", "ma.turing@gmail.com");

        feedbackDAO = new FeedbackDAO();
        feedback = new Feedback(data, employeeAutor, employeeProprietario, DESCRICAO);

        feedbackDAO.salvar(feedback);
    }

    @Test
    public void listar() throws IOException {
        List<Feedback> result = feedbackDAO.listar();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void buscar() throws IOException {
        assertThrows(IOException.class, () -> feedbackDAO.buscar(UUID.randomUUID().toString()));

        Feedback feedbackSalvo = feedbackDAO.buscar(feedback.getId());

        assertEquals(feedback, feedbackSalvo);
    }

    @Test
    public void salvar(){
        Feedback novoFeedback = new Feedback(data, employeeAutor, employeeProprietario, DESCRICAO);

        Feedback feedbackSalvo = assertDoesNotThrow(() -> feedbackDAO.salvar(novoFeedback));
        assertEquals(feedbackSalvo, novoFeedback);
    }



}
