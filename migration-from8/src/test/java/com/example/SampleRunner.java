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
import com.example.repo.CompanyQuery;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SampleRunner {

    private Injector injector;

    @After
    public void tearDown() {
        injector.getInstance(PersistService.class).stop();
    }

    @Before
    public void setup() throws Exception {
        injector = Guice.createInjector(new Module());
        injector.getInstance(PersistService.class).start();

        final Flyway flyway = new Flyway();
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/migration_from8?useUnicode=true&characterEncoding=utf8");
        dataSource.setUser("from8");
        dataSource.setPassword("from8");

        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    @Test
    public void runCompanyQuery() {
        final CompanyQuery query = injector.getInstance(CompanyQuery.class);
        final Optional<CompanyEntity> company = query.findById(1L);
        assertThat(company.isPresent(), is(true));
        company.ifPresent(c -> assertThat(c.getName(), is("サンプル工業")));
    }
}
