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

import java.util.Set;

public class FirstJava9Set {

    public static void main(String[] args) {
        final Set<String> empty = Set.of();
        System.out.println(empty);
        final Set<String> one = Set.of("foo");
        System.out.println(one);
        final Set<Integer> integers = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        System.out.println(integers.size());
    }
}
