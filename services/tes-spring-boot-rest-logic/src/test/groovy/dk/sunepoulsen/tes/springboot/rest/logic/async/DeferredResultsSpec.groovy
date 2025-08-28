package dk.sunepoulsen.tes.springboot.rest.logic.async

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiBadRequestException
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiInternalServerException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.springframework.web.context.request.async.DeferredResult
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Duration
import java.util.concurrent.CompletableFuture

class DeferredResultsSpec extends Specification {

    private Duration duration;
    private Duration sleepDuration;

    void setup() {
        this.duration = Duration.ofSeconds(1)
        this.sleepDuration = Duration.ofMillis(20)
    }

    @Unroll
    void "Tests DeferredResults with a single values: #_testcase"() {
        when:
            DeferredResult<Integer> result = DeferredResults.of(_value)
            DeferredResults.wait(result, this.duration, this.sleepDuration)

        then:
            result.result == _expected

        where:
            _testcase             | _value                                | _expected
            'Primitive value'     | 27                                    | 27
            'Single of value'     | Single.just(45)                       | 45
            'Observable of value' | Observable.just(47)                   | 47
            'Future of value'     | CompletableFuture.completedFuture(55) | 55
    }

    @Unroll
    void "Tests DeferredResults with exception values: #_testcase"() {
        when:
            DeferredResult<Integer> deferredResult = DeferredResults.of(_value)
            DeferredResults.wait(deferredResult, this.duration, this.sleepDuration)
            throw deferredResult.getResult() as Throwable

        then:
            thrown(_exception)

        where:
            _testcase                                       | _value                                                         | _exception
            'Single with ApiException'                      | Single.error(new ApiBadRequestException('message'))            | ApiBadRequestException
            'Single with UnsupportedOperationException'     | Single.error(new UnsupportedOperationException('message'))     | UnsupportedOperationException
            'Single with unknown exception'                 | Single.error(new IllegalAccessError('message'))                | ApiInternalServerException
            'Observable with ApiException'                  | Observable.error(new ApiBadRequestException('message'))        | ApiBadRequestException
            'Observable with UnsupportedOperationException' | Observable.error(new UnsupportedOperationException('message')) | UnsupportedOperationException
            'Observable with unknown exception'             | Observable.error(new IllegalAccessError('message'))            | ApiInternalServerException
    }

    void "Tests DeferredResults with Single exception and exception mapping to ApiException"() {
        when:
            DeferredResult<Integer> deferredResult = DeferredResults.of(Single.error(new IllegalAccessError('message'))) {
                new ApiBadRequestException('message')
            }
            DeferredResults.wait(deferredResult, this.duration, this.sleepDuration)
            throw deferredResult.getResult() as Throwable

        then:
            thrown(ApiBadRequestException)
    }

    void "Tests DeferredResults with Single exception and exception mapping to null"() {
        when:
            DeferredResult<Integer> deferredResult = DeferredResults.of(Single.error(new IllegalAccessError('message'))) {
                null
            }
            DeferredResults.wait(deferredResult, this.duration, this.sleepDuration)
            throw deferredResult.getResult() as Throwable

        then:
            thrown(ApiInternalServerException)
    }

    void "Tests DeferredResults with Single exception and exception mapping throws exception"() {
        when:
            DeferredResult<Integer> deferredResult = DeferredResults.of(Single.error(new IllegalAccessError('message'))) {
                throw new NullPointerException('Null pointer')
            }
            DeferredResults.wait(deferredResult, this.duration, this.sleepDuration)
            throw deferredResult.getResult() as Throwable

        then:
            thrown(ApiInternalServerException)
    }

}
