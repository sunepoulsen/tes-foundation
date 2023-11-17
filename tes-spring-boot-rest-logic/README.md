# TES Spring Boot REST Logic

Library with logic exceptions and utility classes for Spring Boot Rest applications

## Features

- Logic exceptions
- Patch of persistence entities - see `dk.sunepoulsen.tes.springboot.rest.logic.PatchUtilities`

### Logic exceptions

REST applications have different service layers that are annotated with `@Service` og 
`@Component` in Spring Boot. These service layer classes needs to throw exceptions in 
case of errors.

This library provide some generally used logic exceptions that can be used for common 
used purposes:

- Resource not found
- Persistence errors from the Spring Boot Data layer.
- Resource violations
- Duplicate resources

Each logic exception can transform itself to an api exception that can be used in the controller 
layer like this:

```java
@RestController
class SomeController {
    private SomeService someService;
    
    @RequestMapping( value = "/some-endpoint", method = RequestMethod.GET )
    @ResponseStatus( HttpStatus.OK )
    public ReturnType someEndpoint() {
        try {
            // Implement logic
            return someService.doAction();
        }
        catch (LogicException ex) {
            throw ex.mapApiException();
        }
    }
}
```

Of course it will also be possible to handle the logic exception yourself.
