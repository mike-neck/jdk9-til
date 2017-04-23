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
package com.example.http;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientJava9 {

    private static final ExecutorService exec = Executors.newFixedThreadPool(1);

    private static final String yourApiKey = "";

    @Test
    void getCommand() throws UnsupportedEncodingException {
        final HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .executor(exec)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        final String sentence = URLEncoder.encode("諸行無常の響きあり", "UTF-8");
        final HttpRequest request = HttpRequest.newBuilder(
                URI.create(
                        "http://jlp.yahooapis.jp/FuriganaService/V1/furigana?appid=" +
                                yourApiKey +
                                "&sentence=" + sentence))
                .GET()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandler.asString(StandardCharsets.UTF_8))
                .thenAccept(System.out::println)
                .join();
    }
}
