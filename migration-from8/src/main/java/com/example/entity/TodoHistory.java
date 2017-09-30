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

import com.example.value.TodoId;
import com.example.value.UserId;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
public class TodoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(table = "generated_ids"
            , pkColumnName = "pk_name"
            , valueColumnName = "generated_id"
            , pkColumnValue = "todo_history_id")
    @Column(name = "id")
    public final Long todoHistoryId;

    public final TodoId todoId;

    public final UserId operator;

    public final TodoState todoState;

    public TodoHistory(final TodoId todoId, final UserId operator, final TodoState todoState) {
        this.todoHistoryId = null;
        this.todoId = todoId;
        this.operator = operator;
        this.todoState = todoState;
    }

    public TodoHistory(final Long todoHistoryId, final TodoId todoId, final UserId operator,
            final TodoState todoState) {
        this.todoHistoryId = todoHistoryId;
        this.todoId = todoId;
        this.operator = operator;
        this.todoState = todoState;
    }
}
