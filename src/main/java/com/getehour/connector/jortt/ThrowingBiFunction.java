package com.getehour.connector.jortt;

@FunctionalInterface
public interface ThrowingBiFunction<T, U, R> {
    R apply(T t, U u) throws ApiException;
}