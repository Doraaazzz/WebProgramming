package com.dora;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ResultsManager implements Serializable {

    private List<Result> results;
    private EntityManagerFactory resultEntityManagers;

    public ResultsManager() {
        this.resultEntityManagers = Persistence.createEntityManagerFactory("result");
        this.results = new ArrayList<>();
    }

    public List<Result> getResults() {
        return results;
    }

    public void insert(Result result) {
        this.results.add(result);

        EntityManager entityManager = resultEntityManagers.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(result);
        entityManager.getTransaction().commit();
    }
}
