package ru.frtk.das.microtypes;

import java.util.function.Function;
import java.util.function.Supplier;

public class Result<R> {
    private final boolean isError;
    private final R result;
    private final Exception exception;

    public static <R> Result<R> resultOf(Supplier<R> function) {
        try {
            return new Result<>(function.get());
        } catch (Exception e) {
            return new Result<>(e);
        }
    }

    private Result(R result) {
        this.isError = false;
        this.result = result;
        this.exception = null;
    }

    private Result(Exception exception) {
        this.isError = true;
        this.result = null;
        this.exception = exception;
    }

    public <NR> Result<NR> map(Function<? super R, ? extends NR> mapper) {
        if(!isError) {
            return new Result<>(mapper.apply(result));
        } else {
            return new Result<>(this.exception);
        }
    }

    public R error(Function<? super Exception, ? extends R> catcher) {
        if(isError) {
            return catcher.apply(exception);
        } else {
            return result;
        }
    }

    public R orThrow() {
        throw new IllegalStateException(exception);
    }
}
