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

import com.example.DatabaseResource;
import com.example.entity.User;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.seasar.doma.jdbc.Result;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDaoTest {

    @ClassRule
    public static final DatabaseResource.ExecuteOnce executeOnce = new DatabaseResource.ExecuteOnce();

    @Rule
    public DatabaseResource database = new DatabaseResource(executeOnce);

    @Test
    public void insert() {
        final UserDao userDao = database.userDao();
        final User previous = new User("テストユーザー", LocalDate.of(2017, 4, 1));
        final Result<User> result = userDao.insert(previous);

        final User user = result.getEntity();
        assertThat(user.name, is("テストユーザー"));
        assertThat(result.getCount(), is(1));
        assertThat(user.hireDate, is(LocalDate.of(2017, 4, 1)));
    }
}
