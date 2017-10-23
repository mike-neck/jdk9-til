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
package com.example.task.echo;

import com.example.User;
import com.example.action.Action;
import com.example.action.Result;
import com.example.task.Task;
import com.example.task.event.Event;
import com.example.task.event.WrappedEvent;

public class Echo implements Action {

    private final User user;
    private final Event event;
    private final Task task;

    public Echo(final User user, final Event event) {
        this.user = user;
        this.event = event;
        final WrappedEvent wrappedEvent = new WrappedEvent(event);
        task = new Task(user, wrappedEvent, user);
    }

    @Override
    public Result action() {
        return task.action();
    }
}
