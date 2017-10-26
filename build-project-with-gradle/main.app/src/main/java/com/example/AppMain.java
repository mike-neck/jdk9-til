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

import com.example.action.Action;
import com.example.action.Result;

import java.util.List;
import java.util.ServiceLoader;

public class AppMain {

    public static void main(String[] args) {
        final ServiceLoader<Action> serviceLoader = ServiceLoader.load(Action.class);
        final User source = DefaultImpls.defaultUser("Hideyoshi");
        final Group<User> destination = DefaultImpls.defaultGroup("Earth", DefaultImpls.defaultUser("Chache"),
                DefaultImpls.defaultUser("DT"),
                DefaultImpls.defaultUser("Mo"), DefaultImpls.defaultUser("Han"));
        final Event event = Event.of("original", () -> List.of("hello"))
                .from(source)
                .to(destination);

        serviceLoader.stream()
                .map(ServiceLoader.Provider::get)
                .map(a -> a.action(event))
                .filter(Result::isSuccess)
                .flatMap(r -> r.getEvents().stream())
                .map(Object::toString)
                .forEach(System.out::println);
    }
}
