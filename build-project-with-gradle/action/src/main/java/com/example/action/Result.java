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
package com.example.action;

import com.example.Event;

import java.util.List;

public interface Result {

    boolean isSuccess();

    String getState();

    List<Event> getEvents();

    default boolean isFailure() {
        return !isSuccess();
    }

    static Result success(final String addition, final Event... events) {
        return new Success(addition, List.of(events));
    }

    Result FAILURE = new Result() {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getState() {
            return "FAILURE";
        }

        @Override
        public List<Event> getEvents() {
            return List.of();
        }
    };
}

class Success implements Result {

    private final String addition;
    private final List<Event> events;

    Success(final String addition, final List<Event> events) {
        this.addition = addition == null? "": addition;
        this.events = events;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String getState() {
        return "SUCCESS[" + addition + "]";
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return getState();
    }
}
