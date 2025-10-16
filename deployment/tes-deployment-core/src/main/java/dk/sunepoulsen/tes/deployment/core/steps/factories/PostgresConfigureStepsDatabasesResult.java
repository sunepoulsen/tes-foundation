package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.flows.FlowStep;

import java.util.*;

public class PostgresConfigureStepsDatabasesResult implements AbstractStepsResult {
    private final Map<String, PostgresConfigureStepsDatabaseResult> databases;

    public PostgresConfigureStepsDatabasesResult() {
        this.databases = new HashMap<>();
    }

    public PostgresConfigureStepsDatabasesResult(PostgresConfigureStepsDatabaseResult databaseResult) {
        this();
        addDatabase(databaseResult);
    }

    public void addDatabase(PostgresConfigureStepsDatabaseResult database) {
        databases.put(database.getDatabaseName(), database);
    }

    public List<PostgresConfigureStepsDatabaseResult> getDatabases() {
        return new ArrayList<>(databases.values());
    }

    public Optional<PostgresConfigureStepsDatabaseResult> findDatabase(String databaseName) {
        return Optional.ofNullable(databases.get(databaseName));
    }

    @Override
    public List<FlowStep> steps() {
        return databases.values().stream()
            .flatMap(db -> db.steps().stream())
            .toList();
    }

}
