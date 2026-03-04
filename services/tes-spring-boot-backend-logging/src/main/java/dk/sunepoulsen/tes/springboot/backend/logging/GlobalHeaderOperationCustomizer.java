package dk.sunepoulsen.tes.springboot.backend.logging;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.UUIDSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Component
public class GlobalHeaderOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        operation.addParametersItem(new HeaderParameter()
            .name(RequestTransaction.OPERATION_ID_HEADER_NAME)
            .description("ID of the operation associated with using the REST endpoint")
            .required(false)
            .schema(new UUIDSchema()
                .example("65c885f6-e7d2-4056-8c5d-0c2221f9b5a1")
            )
        );

        operation.addParametersItem(new HeaderParameter()
            .name(RequestTransaction.TRANSACTION_ID_HEADER_NAME)
            .description("Unique ID of the transaction associated with using the REST endpoint")
            .required(false)
            .schema(new StringSchema()
                .examples(List.of(
                    "48a95918-1564-4344-9605-a6198e5d4205",
                    "48a95918-1564-4344-9605-a6198e5d4205:1",
                    "48a95918-1564-4344-9605-a6198e5d4205:6:3:9"
                ))
            )
        );

        return operation;
    }

}
