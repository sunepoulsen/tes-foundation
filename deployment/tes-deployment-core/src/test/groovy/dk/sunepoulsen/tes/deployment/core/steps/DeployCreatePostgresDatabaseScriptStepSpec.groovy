package dk.sunepoulsen.tes.deployment.core.steps

import dk.sunepoulsen.tes.data.generators.DataGenerator
import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.deployment.core.data.DeployUser
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException
import dk.sunepoulsen.tes.templates.VelocityEngineFactory
import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class DeployCreatePostgresDatabaseScriptStepSpec extends Specification {

    private DataGenerator<String> passwordGenerator
    private VelocityEngine velocityEngine

    void setup() {
        this.passwordGenerator = Generators.passwordGenerator()
        this.velocityEngine = VelocityEngineFactory.newInstance()
    }

    void "Test timer name"() {
        given:
            DeployCreatePostgresDatabaseScriptStep sut = new DeployCreatePostgresDatabaseScriptStep('key', this.velocityEngine)

        expect:
            sut.timerName() == 'key'
    }

    void "Test creating PostgreSQL database script successfully"() {
        given:
            DeployCreatePostgresDatabaseScriptStep sut = new DeployCreatePostgresDatabaseScriptStep('key', this.velocityEngine)
            sut.filename = 'filename.sql'
            sut.masterUsername = Generators.fixedGenerator('postgres')
            sut.databaseName = Generators.fixedGenerator('features')
            sut.databaseEncoding = Generators.fixedGenerator('UTF8')
            sut.schemaNames = Generators.fixedGenerator(['featureSchema1', 'featureSchema2', 'featureSchema3'])

        and:
            sut.adminUser = new AtomicDataSupplier(DeployUser.builder()
                .username('features_admin')
                .password(passwordGenerator.generate())
                .build()
            )

        and:
            sut.applicationUser = new AtomicDataSupplier(DeployUser.builder()
                .username('features_app')
                .password(passwordGenerator.generate())
                .build()
            )

        when:
            sut.execute()

        then:
            sut.createdScript.get().orElseThrow().filename == 'filename.sql'
            sut.createdScript.get().orElseThrow().getContentAsString(StandardCharsets.UTF_8) ==
                """#!/bin/bash
psql=( psql -v ON_ERROR_STOP=1)
"\${psql[@]}"  --username ${sut.masterUsername.generate()} <<-ENDOFSQL

    CREATE ROLE IF NOT EXISTS features_admin
        PASSWORD '${sut.adminUser.get().orElseThrow().password.replace('\'', '\'\'')}'
        NOCREATEDB
        NOCREATEROLE
        NOSUPERUSER
        LOGIN
    ;

    CREATE ROLE IF NOT EXISTS features_app
        PASSWORD '${sut.applicationUser.get().orElseThrow().password.replace('\'', '\'\'')}'
        NOCREATEDB
        NOCREATEROLE
        NOSUPERUSER
        LOGIN
    ;

    CREATE DATABASE features
        OWNER features_admin
        ENCODING 'UTF8'
    ;

    REVOKE ALL PRIVILEGES ON DATABASE features
        FROM PUBLIC
    ;

ENDOFSQL

"\${psql[@]}"  --username features_admin --dbname features <<-ENDOFSQL

    GRANT CONNECT ON DATABASE features
        TO features_app
    ;

    CREATE SCHEMA featureSchema1
        AUTHORIZATION features_admin
    ;

    GRANT USAGE ON SCHEMA featureSchema1
        TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema1
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema1
        GRANT USAGE, SELECT ON SEQUENCES TO features_app
    ;

    CREATE SCHEMA featureSchema2
        AUTHORIZATION features_admin
    ;

    GRANT USAGE ON SCHEMA featureSchema2
        TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema2
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema2
        GRANT USAGE, SELECT ON SEQUENCES TO features_app
    ;

    CREATE SCHEMA featureSchema3
        AUTHORIZATION features_admin
    ;

    GRANT USAGE ON SCHEMA featureSchema3
        TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema3
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO features_app
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA featureSchema3
        GRANT USAGE, SELECT ON SEQUENCES TO features_app
    ;
ENDOFSQL
"""
    }

    void "Test creating PostgreSQL database script without an Admin User value"() {
        given:
            DeployCreatePostgresDatabaseScriptStep sut = new DeployCreatePostgresDatabaseScriptStep('key', this.velocityEngine)
            sut.filename = 'filename.sql'
            sut.masterUsername = Generators.fixedGenerator('postgres')
            sut.databaseName = Generators.fixedGenerator('features')
            sut.databaseEncoding = Generators.fixedGenerator('UTF8')
            sut.schemaNames = Generators.fixedGenerator(['featureSchema1', 'featureSchema2', 'featureSchema3'])

        and:
            sut.adminUser = new AtomicDataSupplier()

        and:
            sut.applicationUser = new AtomicDataSupplier(DeployUser.builder()
                .username('features_app')
                .password(passwordGenerator.generate())
                .build()
            )

        when:
            sut.execute()

        then:
            FlowStepException ex = thrown(FlowStepException)
            ex.message == 'Admin user has not been set'
    }

    void "Test creating PostgreSQL database script without an Application User value"() {
        given:
            DeployCreatePostgresDatabaseScriptStep sut = new DeployCreatePostgresDatabaseScriptStep('key', this.velocityEngine)
            sut.filename = 'filename.sql'
            sut.masterUsername = Generators.fixedGenerator('postgres')
            sut.databaseName = Generators.fixedGenerator('features')
            sut.databaseEncoding = Generators.fixedGenerator('UTF8')
            sut.schemaNames = Generators.fixedGenerator(['featureSchema1', 'featureSchema2', 'featureSchema3'])

        and:
            sut.adminUser = new AtomicDataSupplier(DeployUser.builder()
                .username('features_admin')
                .password(passwordGenerator.generate())
                .build()
            )

        and:
            sut.applicationUser = new AtomicDataSupplier()

        when:
            sut.execute()

        then:
            FlowStepException ex = thrown(FlowStepException)
            ex.message == 'Application user has not been set'
    }

}