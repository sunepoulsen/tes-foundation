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

/** Adapts futures to Spring deferred results and waits for their completion. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeferredResults {
    /**
     * Adapts a future using the default internal-server-error mapper.
     *
     * @param <T> result type
     * @param completableFuture future to adapt
     * @return deferred result completed from the future
     */
    public static <T> DeferredResult<T> of(CompletableFuture<T> completableFuture ) {
        return of( completableFuture, ApiInternalServerException::new );
    }

    /**
     * Adapts a future and maps unhandled failures to API exceptions.
     *
     * @param <T> result type
     * @param completableFuture future to adapt
     * @param errorMapper mapper for unhandled failures
     * @return deferred result completed from the future
     */
    public static <T> DeferredResult<T> of(CompletableFuture<T> completableFuture, Function<Throwable, ApiException> errorMapper ) {
        final DeferredResult<T> deferredResult = new DeferredResult<>();

        completableFuture
            .thenAccept(deferredResult::setResult)
            .exceptionally(throwable ->
                errorHandler(deferredResult, throwable, errorMapper)
            );

        return deferredResult;
    }

    /**
     * Wraps a value in a completed deferred result.
     *
     * @param <T> result type
     * @param value result value
     * @return deferred result containing the value
     */
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

    /**
     * Waits with the default timing settings for a result to be set or expire.
     *
     * @param <T> result type
     * @param deferredResult result to inspect
     * @return whether the result was set or expired before the wait ended
     * @throws InterruptedException if interrupted while waiting
     */
    public static <T> boolean wait( DeferredResult<T> deferredResult ) throws InterruptedException {
        return wait(deferredResult, Waits.DEFAULT_DURATION);
    }

    /**
     * Waits with a custom timing argument for a result to be set or expire.
     *
     * @param <T> result type
     * @param deferredResult result to inspect
     * @param duration passed as the sleep interval to the three-argument overload
     * @return whether the result was set or expired before the wait ended
     * @throws InterruptedException if interrupted while waiting
     */
    public static <T> boolean wait( DeferredResult<T> deferredResult, Duration duration ) throws InterruptedException {
        return wait(deferredResult, Waits.DEFAULT_SLEEP_DURATION, duration);
    }

    /**
     * Waits for a result to be set or expire using the supplied timing settings.
     *
     * @param <T> result type
     * @param deferredResult result to inspect
     * @param duration maximum wait duration
     * @param sleep interval between checks
     * @return whether the result was set or expired before the wait ended
     * @throws InterruptedException if interrupted while waiting
     */
    public static <T> boolean wait( DeferredResult<T> deferredResult, Duration duration, Duration sleep ) throws InterruptedException {
        return Waits.waitFor(duration, sleep, deferredResult::isSetOrExpired);
    }

}