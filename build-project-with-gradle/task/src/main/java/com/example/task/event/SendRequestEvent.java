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

public class SendRequestEvent implements Event {

    private final User source;
    private final String requestObject;
    private final User destination;

    public SendRequestEvent(final User source, final String requestObject, final User destination) {
        this.source = source;
        this.requestObject = requestObject;
        this.destination = destination;
    }

    @Override
    public String name() {
        return "send_request";
    }

    @Override
    public List<String> message() {
        return List.of("{object:" + requestObject + "}");
    }

    @Override
    public User getSource() {
        return source;
    }

    @Override
    public User getDestination() {
        return destination;
    }
}
