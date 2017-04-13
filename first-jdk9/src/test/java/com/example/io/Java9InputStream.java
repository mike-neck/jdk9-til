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
package com.example.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Java9InputStream {

    @Test
    void transferTo() throws IOException {
        String result = transferString();
        assertEquals("foo-bar-baz-qux", result);
    }

    private String transferString() throws IOException {
        try (
                final InputStream ins = new ByteArrayInputStream("foo-bar-baz-qux".getBytes(StandardCharsets.UTF_8));
                final ByteArrayOutputStream ous = new ByteArrayOutputStream(20)) {
            final long byteLength = ins.transferTo(ous);
            System.out.println(byteLength);
            return ous.toString("UTF-8");
        }
    }
}