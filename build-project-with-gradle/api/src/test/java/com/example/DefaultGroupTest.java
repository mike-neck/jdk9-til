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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultGroupTest {

    @Test
    void emptyGroup() {
        final Group<User> empty = DefaultImpls.defaultGroup("empty");
        assertAll(
                () -> assertEquals(0, empty.size()),
                () -> assertEquals("empty", empty.name()),
                () -> assertTrue(empty.isEmpty())
        );
    }

    @Test
    void containsUsers() {
        final User hideyoshi = DefaultImpls.defaultUser("Hideyoshi");
        final User dt = DefaultImpls.defaultUser("DT");
        final User mo = DefaultImpls.defaultUser("Mo");
        final User maru = DefaultImpls.defaultUser("Maru");
        final User ivan = DefaultImpls.defaultUser("Ivan");

        final Group<User> earthTeam = DefaultImpls.defaultGroup("Earth", hideyoshi, dt, mo, maru, ivan);

        assertAll(
                () -> assertEquals("Earth", earthTeam.name()),
                () -> assertEquals(5, earthTeam.size())
        );
    }
}
