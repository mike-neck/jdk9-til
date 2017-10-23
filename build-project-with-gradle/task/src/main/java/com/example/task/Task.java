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
package com.example.task;

import com.example.User;
import com.example.action.Action;
import com.example.action.Result;
import com.example.task.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    private final User source;
    private final Event event;
    private final User destination;

    public Task(final User source, final Event event, final User destination) {
        this.source = source;
        this.event = event;
        this.destination = destination;
    }

    @Override
    public Result action() {
        final String msg = "Event {" + event.name() + ", " + event.message() + "} from: " + source.name() + ", to: " + destination.name();
        LOGGER.info(msg);
        return Result.success(msg);
    }
}
