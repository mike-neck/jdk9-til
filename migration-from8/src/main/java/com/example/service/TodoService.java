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
package com.example.service;

import com.example.dao.TodoDao;
import com.example.dao.UserDao;
import com.example.entity.*;
import com.example.entity.custom.TodoItem;
import com.example.transaction.Transactional;
import com.example.util.ServiceResult;
import com.example.value.TodoDescription;
import com.example.value.TodoId;
import com.example.value.TodoTitle;
import com.example.value.UserId;
import org.seasar.doma.jdbc.Result;

import javax.inject.Inject;
import javax.inject.Provider;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TodoService {

    private final TodoDao todoDao;
    private final UserDao userDao;
    private final Provider<LocalDateTime> currentTime;

    @Inject
    public TodoService(final TodoDao todoDao, final UserDao userDao,
            final Provider<LocalDateTime> currentTime) {
        this.todoDao = todoDao;
        this.userDao = userDao;
        this.currentTime = currentTime;
    }

    public Optional<Todo> findById(final TodoId todoId) {
        return todoDao.findById(todoId);
    }

    public List<Todo> findByReporterId(final UserId reporter) {
        return todoDao.findByUserId(reporter);
    }

    @Transactional
    public ServiceResult<TodoItem, Exception> createNewTodo(final UserId userId, final TodoTitle title,
            final TodoDescription description) {
        final Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) {
            return ServiceResult.failure(() -> new NoSuchElementException("user not found. id[" + userId.getValue() + "]"));
        }
        final LocalDateTime now = currentTime.get();
        final Todo preTodo = new Todo(title, description, now);
        final Result<Todo> todoResult = todoDao.create(preTodo);
        if (todoResult.getCount() < 1) {
            return ServiceResult.failure(() -> new IllegalStateException("failed to store todo."));
        }
        final Todo todo = todoResult.getEntity();
        final TodoReporter preReporter = new TodoReporter(userId, todo.todoId);
        final Result<TodoReporter> reporterResult = todoDao.createTodoReporter(preReporter);
        if (reporterResult.getCount() < 1) {
            return ServiceResult.failure(() -> new IllegalStateException("failed to store todo_reporter."));
        }
        final TodoHistory preHistory = new TodoHistory(todo.todoId, userId, TodoState.SUBMITTED, now);
        final Result<TodoHistory> historyResult = todoDao.createTodoHistory(preHistory);
        if (historyResult.getCount() < 1) {
            return ServiceResult.failure(() -> new IllegalStateException("failed to store todo_history."));
        }
        return ServiceResult.success(new TodoItem(todo.todoId, todo.title, todo.description, todo.createdAt,
                reporterResult.getEntity().userId, Collections.singletonList(historyResult.getEntity())));
    }
}
