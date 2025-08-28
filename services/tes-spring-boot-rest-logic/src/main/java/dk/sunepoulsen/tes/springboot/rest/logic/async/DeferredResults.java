package dk.sunepoulsen.tes.springboot.rest.logic.async;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiInternalServerException;
import dk.sunepoulsen.tes.utils.Waits;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Duration;
import java.util.concurrent.Future;
import java.util.function.Function;

public class DeferredResults {
    public static <T> DeferredResult<T> of(Future<T> future ) {
        return of( Single.fromFuture( future ) );
    }

    public static <T> DeferredResult<T> of(Future<T> future, Function<Throwable, ApiException> errorMapper ) {
        return of( Single.fromFuture( future ), errorMapper );
    }

    public static <T> DeferredResult<T> of(T value ) {
        return of( Single.just( value ) );
    }

    public static <T> DeferredResult<T> of( Single<T> single ) {
        return of( single, ApiInternalServerException::new );
    }

    public static <T> DeferredResult<T> of(Single<T> single, Function<Throwable, ApiException> errorMapper ) {
        DeferredResult<T> deferredResult = new DeferredResult<>();

        single.subscribe( deferredResult::setResult, throwable ->
            errorHandler(deferredResult, throwable, errorMapper)
        );

        return deferredResult;
    }

    public static <T> DeferredResult<T> of( Observable<T> observable ) {
        return of( observable, ApiInternalServerException::new );
    }

    public static <T> DeferredResult<T> of( Observable<T> observable, Function<Throwable, ApiException> errorMapper ) {
        DeferredResult<T> deferredResult = new DeferredResult<>();

        observable.subscribe( deferredResult::setResult, throwable ->
            errorHandler(deferredResult, throwable, errorMapper)
        );

        return deferredResult;
    }

    private static <T> void errorHandler(DeferredResult<T> deferredResult, Throwable throwable, Function<Throwable, ApiException> errorMapper) {
        try {
            if( throwable instanceof ApiException ) {
                deferredResult.setErrorResult( throwable );
                return;
            }

            if( throwable instanceof UnsupportedOperationException ) {
                deferredResult.setErrorResult( throwable );
                return;
            }

            ApiException apiThrowable = errorMapper.apply( throwable );
            if( apiThrowable != null ) {
                deferredResult.setErrorResult( apiThrowable );
                return;
            }

            deferredResult.setErrorResult( new ApiInternalServerException( throwable ) );
        }
        catch( Throwable ex ) {
            deferredResult.setErrorResult( new ApiInternalServerException( ex ) );
        }
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