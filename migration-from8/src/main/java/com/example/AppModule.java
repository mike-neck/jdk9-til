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
import com.google.inject.Provider;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;

import javax.sql.DataSource;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dialect.class).toInstance(new MysqlDialect());
        bind(DataSource.class).toProvider(DataSourceProvider.class);
        bind(AppConfig.class).asEagerSingleton();
    }

    private static class DataSourceProvider implements Provider<DataSource> {
        @Override
        public DataSource get() {
            final MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setDatabaseName("migration_from8");
            dataSource.setPort(3306);
            dataSource.setUser("from8");
            dataSource.setPassword("from8");
            dataSource.setServerName("localhost");
            dataSource.setURL("jdbc:mysql://localhost:3306/migration_from8");
            return dataSource;
        }
    }
}
