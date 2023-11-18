# TES JSON

TES JSON Library provide common JSON handling with the following features:

- Encoding of classes as JSON with Jackson
- Decoding of JSON to classes with Jackson
- Loading of Jackson modules from the classpath

## Usage

### Encoding JSON

To encode a class into JSON do the following:

```java
String json = JsonMapper.encode(value);
```

### Decoding JSON

To decode json into a class do the following:

```java
String json = "{...}"
ClassType value = JsonMapper.decode(json, ClassType.class);
```

### Customize the use of JsonMapper

To use a custom `ObjectMapper` with JsonMapper do:

```java
ObjectMapper objectMapper = new ObjectMapper();

// Config objectMapper
// ...
// ...

JsonMapper jsonMapper = new JsonMapper(objectMapper);

// Use jsonMapper
String json = jsonMapper.encode(value);
ClassType value = jsonMapper.decode(json, ClassType.class);
```
