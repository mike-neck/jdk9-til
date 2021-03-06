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

import com.example.impl.DefaultGroup;
import com.example.impl.DefaultUser;
import org.eclipse.collections.api.set.ImmutableSet;

public final class DefaultImpls {

    private DefaultImpls() {
        throw new UnsupportedOperationException();
    }

    public static User defaultUser(final String name) {
        return new DefaultUser(name);
    }

    @SafeVarargs
    public static <U extends User> Group<U> defaultGroup(final String name, final U... users) {
        return new DefaultGroup<>(name, users);
    }

    public static <U extends User> Group<U> defaultGroup(final String name, final ImmutableSet<U> users) {
        return new DefaultGroup<>(name, users);
    }
}
