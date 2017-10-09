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
package com.example.entity;

import org.eclipse.collections.impl.factory.Lists;
import org.seasar.doma.Domain;

import java.util.NoSuchElementException;

@Domain(valueType = Integer.class, factoryMethod = "fromInt")
public enum TodoState {

    SUBMITTED(10),
    IN_PROGRESS(20),
    FIXED(100),
    VERIFIED(150),
    REOPEN(50);

    public final int value;

    TodoState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TodoState fromInt(final Integer value) {
        return Lists.immutable.with(values())
                .detectOptional(s -> s.value == value)
                .orElseThrow(() -> new NoSuchElementException("Invalid state value[" + value + "]."));
    }
}
