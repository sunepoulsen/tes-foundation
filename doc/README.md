# TES Foundation

## Libraries

The foundation defines the following libraries that is designed to be used in production code:

- [TES JSON](tes-json/README.md): Common way to work with JSON.

### Dependencies

This diagram shows the dependencies between all the libraries in TES Foundation

```mermaid
flowchart LR
    subgraph "Legends"
        legProdLib["Production Library"]:::prodLibrary
        legTestLib["Test Library"]:::testLibrary
    end

    subgraph "Libraries"
        tesJson["TES JSON"]:::prodLibrary
    end

    classDef prodLibrary fill:#d9ead3,color:#000
    classDef testLibrary fill:#d9d2e9,color:#000
```
