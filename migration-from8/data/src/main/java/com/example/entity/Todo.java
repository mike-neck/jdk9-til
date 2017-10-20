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
package com.example.entity;

import com.example.value.TodoDescription;
import com.example.value.TodoId;
import com.example.value.TodoTitle;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(table = "generated_ids"
            , pkColumnName = "pk_name"
            , valueColumnName = "generated_id"
            , pkColumnValue = "todo_id")
    @Column(name = "id")
    public final TodoId todoId;

    public final TodoTitle title;

    public final TodoDescription description;

    public final LocalDateTime createdAt;

    public Todo(final TodoTitle title, final TodoDescription description, final LocalDateTime createdAt) {
        this.todoId = null;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Todo(final TodoId todoId, final TodoTitle title, final TodoDescription description,
            final LocalDateTime createdAt) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }
}
