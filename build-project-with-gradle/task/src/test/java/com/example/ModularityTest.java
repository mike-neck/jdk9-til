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

// 参照できない
//import org.eclipse.collections.api.set.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModularityTest {

    @Test
    void canReachUser() {
        final User hideyoshi = DefaultImpls.defaultUser("Hideyoshi");
        assertEquals("Hideyoshi", hideyoshi.name());
    }

    @Test
    void canReachByReflection() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Class<?> klass = ModularityTest.class.getClassLoader().loadClass("com.example.impl.DefaultUser");
        @SuppressWarnings("unchecked")
        final Constructor<User> constructor = (Constructor<User>) klass.getConstructor(String.class);
        final User hideyoshi = constructor.newInstance("Hideyoshi");
        assertEquals("Hideyoshi", hideyoshi.name());
    }

    @Test
    void canReachDirectly() {
        final User user = new com.example.impl.DefaultUser("Hideyoshi");
        assertEquals("Hideyoshi", user.name());
    }

    // 参照できない
//    @Test
//    void canReachEclipseCollection() {
//        final User hideyoshi = DefaultImpls.defaultUser("Hideyoshi");
//        final User dt = DefaultImpls.defaultUser("DT");
//        final ImmutableSet<User> users = Sets.immutable.of(hideyoshi, dt);
//        final Group<User> group = DefaultImpls.defaultGroup("Goran", users);
//
//        assertEquals("Goran", group.name());
//    }
}
