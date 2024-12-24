# Design of TES SUT Engine

## Diagrams

### System Under Test Descriptor 

```mermaid
classDiagram
    class SystemUnderTestDescriptor {
    }

    class RepositoryDescriptor {
        -Map~String,TestDataGenerator~String~~ values
    }
    
    class ServiceDescriptor {
        <<abstract>>
        
        -id: String
        -imageName: String
        -imageTag: String
        -networkAliases: List~String~
    }

    class PostgresDescriptor {
        -adminPassword: String
    }

    class DatabaseDescriptor {
        -name: String
        -owner: DatabaseUserDescriptor
    }

    class DatabaseSchemaDescriptor {
        -name: String
        -user: DatabaseUserDescriptor
    }

    class DatabaseUserDescriptor {
        -username: String
        -password: String
    }

    class DatabaseUserAccessDescriptor {
        -tables: List~String~
        -sequences: List~String~
    }

    SystemUnderTestDescriptor "1" --> "1" RepositoryDescriptor
    SystemUnderTestDescriptor "1" --> "n" ServiceDescriptor
    
    PostgresDescriptor --|> ServiceDescriptor
    PostgresDescriptor "1" --> "n" DatabaseDescriptor
    DatabaseDescriptor "1" --> "n" DatabaseSchemaDescriptor
    DatabaseSchemaDescriptor "1" --> "n" DatabaseUserDescriptor
    DatabaseUserDescriptor "1" --> "1" DatabaseUserAccessDescriptor 
```

### System Under Test

```mermaid
classDiagram
    class SystemUnderTest {
        -profile: String
        -state: DeploymentState
        +deploy()
        +undeploy()
    }

    class SutService {
        -state: DeploymentState
        +deploy()
        +undeploy()
    }

    class SutWebService {
        -state: DeploymentState
        +deploy()
        +undeploy()
    }

    class GenericContainer {
        -waitStrategy: WaitStrategy
    }

    SutService <|-- SutWebService

    SystemUnderTest "1" --> "n" SutService
    SutService "1" --> "1" GenericContainer
    
```

### System Under Test Engine

## Classes & Interfaces

### System description

- `SystemUnderTestDescriptor`: `SystemUnderTestDescriptor` is the main class to describe a system under tests.
- `RepositoryDescriptor`: Describes a set of values that can be accessed from `ServiceDescriptor`
