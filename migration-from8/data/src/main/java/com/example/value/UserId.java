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
package com.example.value;

import org.seasar.doma.Domain;

import java.util.Objects;

@Domain(valueType = Long.class)
public class UserId implements Comparable<UserId> {

    private final Long value;

    public UserId(final Long value) {
        this.value = Objects.requireNonNull(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public int compareTo(final UserId o) {
        final UserId another = Objects.requireNonNull(o);
        return this.value.compareTo(another.value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;

        final UserId userId = (UserId) o;

        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
