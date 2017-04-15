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
package com.example.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import java.util.stream.Stream;

class FlowJava9 {

    @Test
    void example() {
        try(final SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {

            final FirstSubscriber<String> subscriber = new FirstSubscriber<>();
            publisher.subscribe(subscriber);

            final RandomTimeConsumer<String> consumer = new RandomTimeConsumer<>(publisher::submit);

            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                final String name = Thread.currentThread().getName();
                System.out.println("Thread[" + name + "] - before call");
                Stream.of("foo", "bar", "baz", "qux").forEach(consumer);
                System.out.println("Thread[" + name + "] - after call");
            }).thenAccept(n -> System.out.println("finish calling"));
            final String name = Thread.currentThread().getName();
            while (!future.isDone()) {
                try {
                    Thread.sleep(200L);
                    System.out.println("Thread[" + name + "] - waiting");
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    public static class RandomTimeConsumer<T> implements Consumer<T> {

        private final Random random;
        private final Consumer<T> consumer;

        RandomTimeConsumer(Consumer<T> consumer) {
            this.consumer = consumer;
            this.random = new Random();
        }

        @Override
        public void accept(T t) {
            try {
                final long time = random.nextInt(20) * 100L;
                Thread.sleep(time);
                consumer.accept(t);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class FirstSubscriber<T> implements Flow.Subscriber<T> {


        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1L);
        }

        @Override
        public void onNext(T item) {
            final String name = Thread.currentThread().getName();
            System.out.println(String.format("Thread[%s] - item[%s]", name, item.toString()));
            subscription.request(1L);
        }

        @Override
        public void onError(Throwable throwable) {
            final String name = Thread.currentThread().getName();
            System.out.println(
                    String.format("Thread[%s] - error[%s]: %s", name, throwable.getMessage(), throwable.getClass()
                            .getSimpleName()));
        }

        @Override
        public void onComplete() {
            System.out.println("Thread[" + Thread.currentThread().getName() + "] - finish");
        }
    }
}
