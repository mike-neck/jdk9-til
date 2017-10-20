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
package com.example.entity.custom;

import com.example.entity.TodoHistory;
import com.example.entity.TodoState;
import com.example.value.*;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.Tuples;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class TodoItem {

    public final TodoId todoId;
    public final TodoTitle title;
    public final TodoDescription description;
    public final LocalDateTime createdAt;
    public final UserId reporter;
    public final List<TodoHistory> changes;

    public TodoItem(final TodoId todoId, final TodoTitle title, final TodoDescription description,
            final LocalDateTime createdAt, final UserId reporter,
            final List<TodoHistory> changes) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.reporter = reporter;
        this.changes = changes;
    }

    TodoItem(final BasicTodoProperties basicProperties, final List<TodoHistory> changes) {
        this.todoId = basicProperties.todoId;
        this.title = basicProperties.title;
        this.description = basicProperties.description;
        this.createdAt = basicProperties.createdAt;
        this.reporter = basicProperties.reporter;
        this.changes = changes;
    }

    public static List<TodoItem> from(final Collection<Raw> rawItems) {
        return Lists.immutable.ofAll(rawItems)
                .asLazy()
                .collect(r -> Tuples.pair(r.basicProperties(), r.toHistory()))
                .groupBy(Pair::getOne)
                .collectValues(Pair::getTwo)
                .keyMultiValuePairsView()
                .collect(p -> new TodoItem(p.getOne(), p.getTwo().toList()))
                .toList();
    }

    @Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
    public static class Raw {

        final TodoId todoId;

        final TodoTitle title;

        final TodoDescription description;

        final LocalDateTime createdAt;

        final UserId reporter;

        final TodoHistoryId historyId;

        final TodoState todoState;

        final UserId operator;

        final LocalDateTime updatedAt;

        public Raw(final TodoId todoId, final TodoTitle title, final TodoDescription description,
                final LocalDateTime createdAt, final UserId reporter, final TodoHistoryId historyId,
                final TodoState todoState,
                final UserId operator, final LocalDateTime updatedAt) {
            this.todoId = todoId;
            this.title = title;
            this.description = description;
            this.createdAt = createdAt;
            this.reporter = reporter;
            this.historyId = historyId;
            this.todoState = todoState;
            this.operator = operator;
            this.updatedAt = updatedAt;
        }

        TodoHistory toHistory() {
            return new TodoHistory(historyId, todoId, operator, todoState, updatedAt);
        }

        BasicTodoProperties basicProperties() {
            return new BasicTodoProperties(todoId, title, description, createdAt, reporter);
        }
    }

    private static class BasicTodoProperties {

        final TodoId todoId;
        final TodoTitle title;
        final TodoDescription description;
        final LocalDateTime createdAt;
        final UserId reporter;

        private BasicTodoProperties(final TodoId todoId, final TodoTitle title,
                final TodoDescription description, final LocalDateTime createdAt,
                final UserId reporter) {
            this.todoId = todoId;
            this.title = title;
            this.description = description;
            this.createdAt = createdAt;
            this.reporter = reporter;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof BasicTodoProperties)) return false;

            final BasicTodoProperties that = (BasicTodoProperties) o;

            if (!todoId.equals(that.todoId)) return false;
            if (!title.equals(that.title)) return false;
            if (!description.equals(that.description)) return false;
            if (!createdAt.equals(that.createdAt)) return false;
            return reporter.equals(that.reporter);
        }

        @Override
        public int hashCode() {
            int result = todoId.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + description.hashCode();
            result = 31 * result + createdAt.hashCode();
            result = 31 * result + reporter.hashCode();
            return result;
        }
    }
}
