package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackServiceTest {

    private final LocalDate localDate = LocalDate.now();
    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private DAO<Feedback> feedbackDAO;

    private FeedbackService service;
    private Service<Employee> employeeService;

    @BeforeEach
    public void initEach() throws IOException, BusinessException {
        feedbackDAO = (DAO<Feedback>) Mockito.mock(DAO.class);
        employeeService = (Service<Employee>) Mockito.mock(Service.class);
        service = new FeedbackService();

        service.setDAO(feedbackDAO);
        service.setEmployeeService(employeeService);

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        when(employeeService.buscar(autor.getId())).thenReturn(autor);
        when(employeeService.buscar(proprietario.getId())).thenReturn(proprietario);
        service.salvar(feedback);
    }

    @Test
    public void listar() throws IOException {
        when(feedbackDAO.listar()).thenReturn(List.of(feedback));

        List<Feedback> lista = assertDoesNotThrow(() -> service.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void salvar() throws IOException, BusinessException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");

        Feedback feedbackValido1 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        Feedback feedbackValido2 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        Feedback feedbackInvalido1 = new Feedback(localDate, null, null, "feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(localDate, null, employeeNaoSalvo, "feedback sem autor e proprietario");

        when(feedbackDAO.salvar(feedbackInvalido1)).thenReturn(feedbackInvalido1);
        when(feedbackDAO.salvar(feedbackValido2)).thenReturn(feedbackValido2);
        when(employeeService.buscar(employeeNaoSalvo.getId())).thenThrow(new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));

        assertDoesNotThrow(() -> service.salvar(feedbackValido1));
        assertDoesNotThrow(() -> service.salvar(feedbackValido2));

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> service.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> service.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class, () -> service.salvar(feedbackInvalido2));

        assertEquals("employee inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o employee", exception3.getMessage());
    }

    @Test
    public void buscar() throws IOException, BusinessException {
        Feedback feedbackNaoSalvo = new Feedback(localDate, autor, proprietario, "tt");

        when(feedbackDAO.buscar(feedback.getId())).thenReturn(feedback);
        when(feedbackDAO.buscar(feedbackNaoSalvo.getId())).thenThrow(FileNotFoundException.class);

        assertDoesNotThrow(() -> service.buscar(feedback.getId()));
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscar(feedbackNaoSalvo.getId()));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

}
