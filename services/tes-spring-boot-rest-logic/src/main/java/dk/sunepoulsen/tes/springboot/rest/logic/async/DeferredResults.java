package dk.sunepoulsen.tes.springboot.rest.logic.async;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiInternalServerException;
import dk.sunepoulsen.tes.springboot.rest.logic.exceptions.LogicException;
import dk.sunepoulsen.tes.utils.Waits;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeferredResults {
    public static <T> DeferredResult<T> of(CompletableFuture<T> completableFuture ) {
        return of( completableFuture, ApiInternalServerException::new );
    }

    public static <T> DeferredResult<T> of(CompletableFuture<T> completableFuture, Function<Throwable, ApiException> errorMapper ) {
        final DeferredResult<T> deferredResult = new DeferredResult<>();

        completableFuture
            .thenAccept(deferredResult::setResult)
            .exceptionally(throwable ->
                errorHandler(deferredResult, throwable, errorMapper)
            );

        return deferredResult;
    }

    public static <T> DeferredResult<T> of(T value ) {
        return of( CompletableFuture.completedFuture( value ) );
    }

    private static <T> Void errorHandler(DeferredResult<T> deferredResult, Throwable throwable, Function<Throwable, ApiException> errorMapper) {
        try {
            if( throwable instanceof ApiException ) {
                deferredResult.setErrorResult( throwable );
                return null;
            }

            if( throwable instanceof LogicException logicException ) {
                errorHandler( deferredResult, logicException.mapApiException(), errorMapper );
                return null;
            }

            if( throwable instanceof CompletionException) {
                errorHandler(deferredResult, throwable.getCause(), errorMapper);
                return null;
            }

            if( throwable instanceof UnsupportedOperationException ) {
                deferredResult.setErrorResult( throwable );
                return null;
            }

            ApiException apiThrowable = errorMapper.apply( throwable );
            if( apiThrowable != null ) {
                deferredResult.setErrorResult( apiThrowable );
                return null;
            }

            deferredResult.setErrorResult( new ApiInternalServerException( throwable ) );
        }
        catch( Exception ex ) {
            deferredResult.setErrorResult( new ApiInternalServerException( ex ) );
        }

        return null;
    }

    public static <T> boolean wait( DeferredResult<T> deferredResult ) throws InterruptedException {
        return wait(deferredResult, Waits.DEFAULT_DURATION);
    }

    public static <T> boolean wait( DeferredResult<T> deferredResult, Duration duration ) throws InterruptedException {
        return wait(deferredResult, Waits.DEFAULT_SLEEP_DURATION, duration);
    }

    public static <T> boolean wait( DeferredResult<T> deferredResult, Duration duration, Duration sleep ) throws InterruptedException {
        return Waits.waitFor(duration, sleep, deferredResult::isSetOrExpired);
    }

}