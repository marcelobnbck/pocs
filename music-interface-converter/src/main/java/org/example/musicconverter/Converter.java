package org.example.musicconverter;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Converter<T, R> {
    R convert(T input);

    default <V> Converter<T, V> andThen(Converter<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.convert(this.convert(t));
    }

    default <S> Converter<S, R> compose(Converter<? super S, ? extends T> before) {
        Objects.requireNonNull(before);
        return (S s) -> this.convert(before.convert(s));
    }

    static <A, B> Converter<A, B> of(Function<? super A, ? extends B> f) {
        Objects.requireNonNull(f);
        return f::apply;
    }
}
