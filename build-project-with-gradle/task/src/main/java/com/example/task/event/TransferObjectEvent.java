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

public class TransferObjectEvent implements Event {

    private final User source;
    private final String objectName;
    private final User destination;

    public TransferObjectEvent(final User source, final String objectName, final User destination) {
        this.source = source;
        this.objectName = objectName;
        this.destination = destination;
    }

    @Override
    public String name() {
        return "transfer_object";
    }

    @Override
    public List<String> message() {
        return List.of("{object:" + objectName + "}");
    }

    @Override
    public User getSource() {
        return null;
    }

    @Override
    public User getDestination() {
        return null;
    }
}
