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

public interface Result {

    boolean isSuccess();

    String getState();

    default boolean isFailure() {
        return !isSuccess();
    }

    static Result success(final String addition) {
        return new Success(addition);
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
    };
}

class Success implements Result {

    private final String addition;

    Success(final String addition) {
        this.addition = addition == null? "": addition;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String getState() {
        return "SUCCESS[" + addition + "]";
    }

    @Override
    public String toString() {
        return getState();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Success)) return false;

        final Success success = (Success) o;

        return addition.equals(success.addition);
    }

    @Override
    public int hashCode() {
        return addition.hashCode();
    }
}
