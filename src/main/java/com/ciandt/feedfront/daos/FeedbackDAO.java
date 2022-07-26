package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private static final String REPOSITORIO_PATH = "src/main/resources/data/feedback/";

    @Override
    public boolean tipoImplementaSerializable() {
        return false;
    }

    @Override
    public List<Feedback> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Stream<Path> repoPath = Files.walk(Paths.get(REPOSITORIO_PATH));
            List<String> files = buscarArquivosPorPath(repoPath);

            files.stream().forEach(file -> {
                try {
                    feedbacks.add(buscar(file));
                } catch (EntidadeNaoSerializavelException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            repoPath.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws IOException, EntidadeNaoSerializavelException {
        ObjectInputStream objstream = null;
        Feedback feedback = null;
        try {
            objstream = new ObjectInputStream(new FileInputStream(REPOSITORIO_PATH + id + ".byte"));
            feedback = (Feedback) objstream.readObject();
            objstream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException();
        }
        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws IOException, EntidadeNaoSerializavelException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(feedback.getArquivo()));
            objectOutputStream.writeObject(feedback);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new EntidadeNaoSerializavelException();
        }
        return feedback;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        try {
            Feedback feedback = buscar(id);
            Files.delete(Paths.get(feedback.getArquivo()));
            listar().remove(feedback);
        }catch (IOException e){
            throw new EntidadeNaoSerializavelException();
        }
        return true;
    }

    private List<String> buscarArquivosPorPath(Stream<Path> paths) {
        return paths.map(p -> p.getFileName().toString())
                .filter(p -> p.endsWith(".byte"))
                .map(y -> y.replace(REPOSITORIO_PATH, ""))
                .map(p -> p.replace(".byte", ""))
                .collect(Collectors.toList());
    }
}
