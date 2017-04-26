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

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientTest {

    public static void main(String[] args) {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final HttpClient client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .executor(executor)
                .followRedirects(HttpClient.Redirect.SAME_PROTOCOL)
                .build();
        final HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.google.com/teapot"))
                .GET()
                .build();
        client.sendAsync(request,
                HttpResponse.BodyHandler.asString(StandardCharsets.UTF_8))
                .thenApply(HttpResponse::body)
                .thenApply(s -> String.format("[%s] - %s", Thread.currentThread().getName(), s))
                .thenAccept(System.out::println)
                .thenAccept(v -> executor.shutdown());
    }
}
