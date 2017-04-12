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
package com.example;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirstJava9SetTest {

    @Test
    void setOfで作られたSetに対する操作は例外() {
        final Set<String> foo = Set.of("foo");
        assertAll(
                () -> assertThrows(UnsupportedOperationException.class, () -> foo.add("bar")),
                () -> assertThrows(UnsupportedOperationException.class, foo::clear),
                () -> assertThrows(UnsupportedOperationException.class, () -> foo.addAll(Set.of("bar"))),
                () -> assertThrows(UnsupportedOperationException.class, () -> foo.remove("foo")),
                () -> assertThrows(UnsupportedOperationException.class, () -> foo.removeAll(Set.of("bar"))),
                () -> assertThrows(UnsupportedOperationException.class, () -> foo.removeIf(s -> s.length() > 1))
        );
    }
}
