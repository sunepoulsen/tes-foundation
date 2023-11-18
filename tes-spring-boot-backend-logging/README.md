# TES Spring Boot Backend Logging

## Features

Activates Spring Boot's out of the box request logging.

### Logging of requests

The library have support of logging requests of endpoints by adding a filter to the Spring Boot Controller layer.

This feature can be activated by setting a log level:

```
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
```

and be configured thought application properties:

| Property                                 | Type    | Default value     | Description                                        |
|------------------------------------------|---------|-------------------|----------------------------------------------------|
| `requests.logging.include-query-string`  | Boolean | False             | Log query arguments of a request                   |
| `requests.logging.include-headers`       | Boolean | False             | Log request headers                                |
| `requests.logging.include-payload`       | Boolean | False             | Log request payload                                |
| `requests.logging.max-payload-length`    | Integer | 0                 | Max size in bytes of the payload to log            | 
| `requests.logging.after-message-prefix`  | String  | `REQUEST DATA: `  | Message to prefix when we log the complete request | 

TES Maven adds the ability to lookup metadata XML files for a given Maven Artifact.
