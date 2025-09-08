#!/bin/bash
psql=( psql -v ON_ERROR_STOP=1)
#[["${psql[@]}"]]#  --username $masterUsername <<-ENDOFSQL

    CREATE ROLE IF NOT EXISTS $adminUser.getUsername()
        PASSWORD '$adminUser.password'
        NOCREATEDB
        NOCREATEROLE
        NOSUPERUSER
        LOGIN
    ;

    CREATE ROLE IF NOT EXISTS $applicationUser.username
        PASSWORD '$applicationUser.password'
        NOCREATEDB
        NOCREATEROLE
        NOSUPERUSER
        LOGIN
    ;

    CREATE DATABASE $databaseName
        OWNER $adminUser.username
        ENCODING '$databaseEncoding'
    ;

    REVOKE ALL PRIVILEGES ON DATABASE $databaseName
        FROM PUBLIC
    ;

ENDOFSQL

#[["${psql[@]}"]]#  --username $adminUser.username --dbname $databaseName <<-ENDOFSQL

    GRANT CONNECT ON DATABASE $databaseName
        TO $applicationUser.username
    ;
#foreach ( $schema in $schemas )

    CREATE SCHEMA $schema
        AUTHORIZATION $adminUser.username
    ;

    GRANT USAGE ON SCHEMA $schema
        TO $applicationUser.username
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA $schema
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO $applicationUser.username
    ;

    ALTER DEFAULT PRIVILEGES IN SCHEMA $schema
        GRANT USAGE, SELECT ON SEQUENCES TO $applicationUser.username
    ;
#end
ENDOFSQL
