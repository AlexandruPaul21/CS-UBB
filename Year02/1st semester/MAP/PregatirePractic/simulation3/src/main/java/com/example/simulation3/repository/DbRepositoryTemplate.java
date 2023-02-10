package com.example.simulation3.repository;

public class DbRepositoryTemplate{
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public DbRepositoryTemplate(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }
}
