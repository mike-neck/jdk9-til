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
package com.example.dao;

import com.example.AppConfig;
import com.example.InjectConfig;
import com.example.entity.Todo;
import com.example.entity.TodoHistory;
import com.example.entity.TodoReporter;
import com.example.entity.custom.TodoItem;
import com.example.value.TodoId;
import com.example.value.UserId;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Result;

import java.util.List;
import java.util.Optional;

@Dao(config = AppConfig.class)
@InjectConfig
public interface TodoDao {

    @Select
    Optional<Todo> findById(final TodoId todoId);

    @Select
    List<Todo> findByUserId(final UserId userId);

    @Insert
    Result<Todo> create(final Todo todo);

    @Insert
    Result<TodoReporter> createTodoReporter(final TodoReporter reporter);

    @Insert
    Result<TodoHistory> createTodoHistory(final TodoHistory history);

    @Select
    List<TodoItem.Raw> findTodoItemById(final TodoId todoId);
}
