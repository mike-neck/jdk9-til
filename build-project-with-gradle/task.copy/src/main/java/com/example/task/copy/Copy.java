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
package com.example.task.copy;

import com.example.User;
import com.example.action.Action;
import com.example.action.Result;
import com.example.task.Task;
import com.example.task.event.Event;
import com.example.task.event.WrappedEvent;

public class Copy implements Action {

    private final User source;
    private final User destination;
    private final Task task;

    public Copy(final User source, final Event event, final User destination) {
        this.source = source;
        this.destination = destination;
        final WrappedEvent wrappedEvent = new WrappedEvent(event);
        this.task = new Task(source, Event.of(event.name(), () -> wrappedEvent.action("copy")), destination);
    }

    @Override
    public Result action() {
        return task.action();
    }

    @Override
    public String toString() {
        return "Copy task from: " + source.name() + " to: " + destination.name() + ".";
    }
}
