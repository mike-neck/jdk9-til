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

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("migration"));
        bind(JPAQueryFactory.class).toProvider(QueryFactoryProvider.class);
    }

    public static class QueryFactoryProvider implements Provider<JPAQueryFactory> {

        private final EntityManager em;

        @Inject
        public QueryFactoryProvider(final EntityManager em) {
            this.em = em;
        }

        @Override
        public JPAQueryFactory get() {
            return new JPAQueryFactory(() -> em);
        }
    }
}
