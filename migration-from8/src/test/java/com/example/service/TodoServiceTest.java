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

import com.example.DatabaseResource;
import com.example.entity.TodoHistory;
import com.example.entity.TodoState;
import com.example.entity.User;
import com.example.entity.custom.TodoItem;
import com.example.util.ServiceResult;
import com.example.value.TodoDescription;
import com.example.value.TodoTitle;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class TodoServiceTest {

    @ClassRule
    public static final DatabaseResource.ExecuteOnce executeOnce = new DatabaseResource.ExecuteOnce();

    @Rule
    public final DatabaseResource database = new DatabaseResource(executeOnce);
    private User user;

    @Before
    public void setupUser() {
        final UserService userService = database.service(UserService.class);
        user = userService.hireNewUser("土方巽");
    }

    @Test
    public void createNewTodoStores3Tables() {
        final TodoService service = database.service(TodoService.class);
        final ServiceResult<TodoItem, Exception> result = service.createNewTodo(user.userId,
                new TodoTitle("暗黒舞踏派解散公演の日程を決める"), new TodoDescription(""));
        try {
            final TodoItem item = result.getOrThrow();
            assertThat(item, is(notNullValue()));
            assertThat(item.reporter, is(user.userId));
            assertThat(item.changes.size(), is(1));
            final TodoHistory history = item.changes.get(0);
            assertThat(history.operator, is(user.userId));
            assertThat(history.state, is(TodoState.SUBMITTED));
        } catch (Exception e) {
            fail();
        }
    }
}
