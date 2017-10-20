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

import com.example.value.TodoHistoryId;
import com.example.value.TodoId;
import com.example.value.UserId;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
public class TodoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(table = "generated_ids"
            , pkColumnName = "pk_name"
            , valueColumnName = "generated_id"
            , pkColumnValue = "todo_history_id")
    @Column(name = "id")
    public final TodoHistoryId todoHistoryId;

    public final TodoId todoId;

    public final UserId operator;

    public final TodoState state;

    public final LocalDateTime createdAt;

    public TodoHistory(final TodoId todoId, final UserId operator, final TodoState state,
            final LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.todoHistoryId = null;
        this.todoId = todoId;
        this.operator = operator;
        this.state = state;
    }

    public TodoHistory(final TodoHistoryId todoHistoryId, final TodoId todoId, final UserId operator,
            final TodoState state, final LocalDateTime createdAt) {
        this.todoHistoryId = todoHistoryId;
        this.todoId = todoId;
        this.operator = operator;
        this.state = state;
        this.createdAt = createdAt;
    }
}
