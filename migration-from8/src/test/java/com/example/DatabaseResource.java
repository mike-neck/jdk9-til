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
package com.example;

import com.example.dao.TodoDao;
import com.example.dao.TodoDaoImpl;
import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.rules.ExternalResource;

public class DatabaseResource extends ExternalResource {

    private final Injector injector;

    public DatabaseResource() {
        injector = Guice.createInjector(new AppModule(), new BindingDao());
    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    protected void before() throws Throwable {
        final InitializeDao script = injector.getInstance(InitializeDao.class);
        script.initializeDataForTest();
    }

    public static class BindingDao extends AbstractModule {
        @Override
        protected void configure() {
            bind(TodoDao.class).to(TodoDaoImpl.class);
            bind(UserDao.class).to(UserDaoImpl.class);
            bind(InitializeDao.class).to(InitializeDaoImpl.class);
        }
    }

    public UserDao userDao() {
        return injector.getInstance(UserDao.class);
    }
}
