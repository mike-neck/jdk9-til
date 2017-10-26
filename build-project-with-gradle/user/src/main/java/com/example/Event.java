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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Event {

    static Supplier<List<String>> messageSupplier(final Event event, final String additionalMessage) {
        return () -> {
            final List<String> builder = new ArrayList<>();
            builder.addAll(event.message());
            builder.add(additionalMessage);
            return Collections.unmodifiableList(builder);
        };
    }

    String name();

    List<String> message();

    User getSource();

    User getDestination();

    default Event update(final String additionalMessage) {
        return of(name(), messageSupplier(this, additionalMessage))
                .from(getSource())
                .to(getDestination());
    }

    static SourceUser of(final String name, final Supplier<List<String>> messageSupplier) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(messageSupplier);

        return s -> d -> new Event() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public List<String> message() {
                return messageSupplier.get();
            }

            @Override
            public User getSource() {
                return s;
            }

            @Override
            public User getDestination() {
                return d;
            }

            @Override
            public String toString() {
                return "Event[" + name + "]{source: " + s.name() + ", destination: " + d.name() + ", message: [" +
                        messageSupplier.get().stream().collect(Collectors.joining(", ")) + "]}";
            }
        };
    }

    interface SourceUser {
        DestinationUser from(final User source);
    }

    interface DestinationUser {
        Event to(final User destination);
    }
}
