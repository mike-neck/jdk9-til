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

import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.transaction.Transactional;
import com.example.value.UserId;
import org.seasar.doma.jdbc.Result;

import javax.inject.Inject;
import javax.inject.Provider;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService {

    private final UserDao userDao;
    private final Provider<LocalDate> nowProvider;

    @Inject
    public UserService(final UserDao userDao, final Provider<LocalDate> nowProvider) {
        this.userDao = userDao;
        this.nowProvider = nowProvider;
    }

    @SuppressWarnings("WeakerAccess")
    @Transactional
    public User hireNewUser(final String name) {
        final User user = new User(name, nowProvider.get());
        final Result<User> result = userDao.insert(user);
        if (result.getCount() != 1) {
            throw new IllegalStateException("creating new user is failed.");
        }
        return result.getEntity();
    }

    public Optional<User> findUserById(final UserId userId) {
        return userDao.findById(userId);
    }

    @Transactional
    public Optional<User> updateUser(final UserId userId,
            final String name, final LocalDate hireDate) {
        return com.example.util.Result.fromOptional(findUserById(userId),
                () -> new NoSuchElementException("user not found. id: " + userId.getValue()))
                .map(u -> u.equalsProperties(new User(userId, name, hireDate)) ?
                        Optional.<User>empty() : Optional.of(u))
                .map(o -> o.map(u -> u.updateName(name).updateHireDate(hireDate)))
                .map(o -> o.map(userDao::update))
                .map(o -> o.map(Result::getEntity))
                .getOrThrow();
    }
}
