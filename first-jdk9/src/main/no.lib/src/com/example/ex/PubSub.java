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
package com.example.ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class PubSub {

    public static void main(String[] args) throws IOException {
        final Random random = new Random();
        final ExecutorService exec = Executors.newFixedThreadPool(2);

        prepareTestDataFile(random);

        
    }

    private static void prepareTestDataFile(Random random) throws IOException {
        final Path tmpFile = Files.createTempFile("tmp", ".txt");
        System.out.println(tmpFile);
        try (final BufferedWriter w = Files.newBufferedWriter(tmpFile, StandardCharsets.UTF_8)) {
            IntStream.range(0, 300)
                    .mapToObj(i -> randomLog(random))
                    .forEach(consumer(w::write));
        }
    }

    interface ExConsumer<T> {
        void apply(T t) throws Exception;
    }

    private static <T>Consumer<T> consumer(final ExConsumer<T> c) {
        return t -> {
            try {
                c.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static String randomLog(Random random) {
        return String.format("[%d] - [%s] - [%s]\n", random.nextInt(3) + 1, Sex.random(random),
                Prefecture.random(random));
    }

    enum Prefecture {
        埼玉, 千葉, 東京, 神奈川;
        static Prefecture random(Random r) {
            final int i = r.nextInt(4);
            return values()[i];
        }
    }

    enum Sex {
        女, 男;
        static Sex random(Random r) {
            final int i = r.nextInt(2);
            return values()[i];
        }
    }
}

class Record {
    private final int entrance;
    private final PubSub.Sex sex;
    private final PubSub.Prefecture prefecture;

    Record(int entrance, PubSub.Sex sex, PubSub.Prefecture prefecture) {
        this.entrance = entrance;
        this.sex = sex;
        this.prefecture = prefecture;
    }

    public int getEntrance() {
        return entrance;
    }

    public PubSub.Sex getSex() {
        return sex;
    }

    public PubSub.Prefecture getPrefecture() {
        return prefecture;
    }
}

class ReadingPublisher implements Flow.Publisher<String> {

    private final ExecutorService exec;
    private final Path file;
    private final AtomicInteger callCount = new AtomicInteger(0);

    ReadingPublisher(ExecutorService exec, Path file) {
        this.exec = exec;
        this.file = file;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        if (callCount.getAndIncrement() != 0) {
            subscriber.onError(new IllegalStateException());
        }
        try {
            final BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            subscriber.onSubscribe(new Subscription(exec, subscriber, br));
        } catch (IOException e) {
            subscriber.onError(e);
        }
    }
}

class Subscription implements Flow.Subscription {

    private final ExecutorService exec;
    private final Flow.Subscriber<? super String> sb;
    private final BufferedReader br;

    Subscription(ExecutorService exec, Flow.Subscriber<? super String> sb,
            BufferedReader br) {
        this.exec = exec;
        this.sb = sb;
        this.br = br;
    }

    @Override
    public void request(final long req) {
        exec.submit(() -> {
            try {
                for (long time = req; time > 0 && br.ready(); time--) {
                    final String line = br.readLine();
                    sb.onNext(line);
                }
            } catch (IOException e) {
                sb.onError(e);
            }
        });
    }

    @Override
    public void cancel() {
        exec.submit(() -> {
            try {
                br.close();
                sb.onComplete();
            } catch (IOException e) {
                sb.onError(e);
            }
        });
    }
}
