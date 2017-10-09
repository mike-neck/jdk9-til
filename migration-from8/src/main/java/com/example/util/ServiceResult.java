/*
 * Copyright 2017 Shinya Mochida
 * 
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ServiceResult<T, E extends Exception> {

    T getOrThrow() throws E;

    <R> ServiceResult<R, E> map(final Function<? super T, ? extends R> function);

    static <T, E extends Exception> ServiceResult<T, E> fromOptional(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") final Optional<T> optional,
            final Supplier<? extends E> exception) {
        return optional.<ServiceResult<T, E>>map(ServiceResult::success)
                .orElse(failure(exception));
    }

    static <T, E extends Exception> ServiceResult<T, E> success(final T value) {
        return new Success<>(value);
    }

    static <T, E extends Exception> ServiceResult<T, E> failure(final Supplier<? extends E> exception) {
        return new Failure<>(exception);
    }

    class Success<T, E extends Exception> implements ServiceResult<T, E> {

        private final T value;

        Success(final T value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public T getOrThrow() throws E {
            return value;
        }

        @Override
        public <R> ServiceResult<R, E> map(final Function<? super T, ? extends R> function) {
            return new Success<>(function.apply(value));
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Success)) return false;

            final Success<?, ?> success = (Success<?, ?>) o;

            return value.equals(success.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Result = Success{");
            sb.append("value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }

    class Failure<T, E extends Exception> implements ServiceResult<T, E> {

        private final Supplier<? extends E> exception;

        Failure(final Supplier<? extends E> exception) {
            this.exception = exception;
        }

        @Override
        public T getOrThrow() throws E {
            throw exception.get();
        }

        @Override
        public <R> ServiceResult<R, E> map(final Function<? super T, ? extends R> function) {
            return new Failure<>(exception);
        }
    }

}
