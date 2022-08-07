package com.ciandt.feedfront.services;



import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//TODO: IMPLEMENTE AS CLASSES E MAPEIE A CLASSE PARA O SPRINGBOOT


@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedBackRepository;

    private final EmployeeService employeeService;


    @Override
    public List<Feedback> listar() {
        return feedBackRepository.findAll();
    }

    @Override
    public Feedback buscar(long id) throws BusinessException {
        return feedBackRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o feedback"));
    }

    @Override
    public Feedback salvar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        if (feedback == null) {
            throw new IllegalArgumentException("feedback inválido");
        }
        if (feedback.getAutor() == null && feedback.getProprietario() == null){
            throw new IllegalArgumentException("employee inválido");
        }
        employeeService.buscar(feedback.getProprietario().getId());
        return feedBackRepository.save(feedback);
    }

}
