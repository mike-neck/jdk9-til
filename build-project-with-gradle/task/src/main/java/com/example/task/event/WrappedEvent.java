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
package com.example.task.event;

import com.example.Event;
import com.example.User;

import java.util.List;

public class WrappedEvent implements Event {

    private final Event event;

    public WrappedEvent(final Event event) {
        this.event = event;
    }

    @Override
    public String name() {
        return event.name();
    }

    @Override
    public List<String> message() {
        return Event.messageSupplier(this, "wrapped event").get();
    }

    public String action(final String taskName) {
        return taskName + this.message();
    }

    @Override
    public User getSource() {
        return event.getSource();
    }

    @Override
    public User getDestination() {
        return event.getDestination();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WrappedEvent{");
        sb.append("event=").append(event);
        sb.append('}');
        return sb.toString();
    }
}
