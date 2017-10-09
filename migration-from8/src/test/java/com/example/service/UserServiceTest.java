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
import com.example.entity.User;
import com.example.value.UserId;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

public class UserServiceTest {

    @ClassRule
    public static final DatabaseResource.ExecuteOnce executeOnce = new DatabaseResource.ExecuteOnce();

    @Rule
    public final DatabaseResource database = new DatabaseResource(executeOnce);

    @Test
    public void createNewUser() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("杉田玄白");
        assertThat(user.name, is("杉田玄白"));
        assertThat(user.hireDate, is(now()));
    }

    @Test
    public void findUserThenFound() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("源義経");

        final Optional<User> found = service.findUserById(user.userId);

        assertThat(found.isPresent(), is(true));
        found.ifPresent(f -> {
            assertThat(f.userId, is(user.userId));
            assertThat(f.name, is(user.name));
            assertThat(f.hireDate, is(user.hireDate));
        });
    }

    @Test
    public void findUserThenNotFound() {
        final UserService service = database.service(UserService.class);
        service.hireNewUser("源義経");

        final Optional<User> found = service.findUserById(new UserId(new Random().nextLong()));

        assertThat(found, is(Optional.empty()));
    }

    @Test
    public void updateUserWithAnotherName() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("源義経");

        assumeThat(user.userId, is(notNullValue()));

        final Optional<User> result = service.updateUser(user.userId, "源頼朝", user.hireDate);
        assertThat(result, is(not(Optional.empty())));
        result.ifPresent(u -> {
            assertThat(u.userId, is(user.userId));
            assertThat(u.name, is("源頼朝"));
            assertThat(u.hireDate, is(user.hireDate));
        });
    }

    @Test
    public void updateUserWithHireDate() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("源義経");

        assumeThat(user.userId, is(notNullValue()));

        final Optional<User> result = service.updateUser(user.userId, "源義経", user.hireDate.plus(1L, ChronoUnit.DAYS));
        assertThat(result, is(not(Optional.empty())));
        result.ifPresent(u -> {
            assertThat(u.userId, is(user.userId));
            assertThat(u.name, is(user.name));
            assertThat(u.hireDate.minus(1L, ChronoUnit.DAYS), is(user.hireDate));
        });
    }

    @Test
    public void updateUserWithSameProperties() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("源義経");

        assumeThat(user.userId, is(notNullValue()));

        final Optional<User> result = service.updateUser(user.userId, user.name, user.hireDate);
        assertThat(result, is(Optional.empty()));
    }

    @Test(expected = NoSuchElementException.class)
    public void updateUserWithUnknownUserId() {
        final UserService service = database.service(UserService.class);
        final User user = service.hireNewUser("源義経");

        assumeThat(user.userId, is(notNullValue()));

        service.updateUser(new UserId(2000L), "北条政子", LocalDate.now().plus(20L, ChronoUnit.DAYS));
    }
}
