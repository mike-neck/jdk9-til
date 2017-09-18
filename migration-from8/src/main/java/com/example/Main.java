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

import com.example.entity.CompanyEntity;
import com.example.entity.EmployeeEntity;
import com.example.repo.CompanyQuery;
import com.example.repo.EmployeeQuery;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

import javax.inject.Inject;

public class Main implements AutoCloseable, Runnable {

    private final Injector injector;
    private final AutoCloseable delegate;

    Main(final Injector injector, final AutoCloseable delegate) {
        this.injector = injector;
        this.delegate = delegate;
    }

    @Override
    public void run() {
//        final CompanyQuery companyQuery = injector.getInstance(CompanyQuery.class);
//        companyQuery.findAll().stream()
//                .map(CompanyEntity::getName)
//                .forEach(System.out::println);

        final EmployeeQuery employeeQuery = injector.getInstance(EmployeeQuery.class);
        employeeQuery.findByCompanyId(1L)
                .stream()
                .map(EmployeeEntity::getName)
                .forEach(System.out::println);
    }

    public static void main(final String... args) throws Exception {
        final Injector injector = Guice.createInjector(new Module());
        try (final Main app = injector.getInstance(PersistInitializer.class).startApplication()) {
            app.run();
        }
    }

    @Override
    public void close() throws Exception {
        delegate.close();
    }

    public static class PersistInitializer {

        private final PersistService service;
        private final Injector injector;

        @Inject
        public PersistInitializer(final PersistService service, final Injector injector) {
            this.service = service;
            this.injector = injector;
        }

        Main startApplication() throws Exception {
            service.start();
            return new Main(injector, service::stop);
        }
    }
}
