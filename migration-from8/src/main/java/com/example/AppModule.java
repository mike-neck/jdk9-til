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

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.example.transaction.TransactionRequired;
import com.example.transaction.Transactional;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import com.mysql.cj.jdbc.Driver;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.time.LocalDate;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dialect.class).toInstance(new MysqlDialect());
        final DataSource dataSource = dataSource();
        bind(DataSource.class).toInstance(dataSource);
        bind(Config.class).to(AppConfig.class);
        bind(UserTransaction.class).toProvider(UserTransactionProvider.class);
        bind(TransactionManager.class).toProvider(UserTransactionManagerProvider.class).asEagerSingleton();
        bind(AppConfig.class).asEagerSingleton();
        bind(LocalDate.class).toProvider(LocalDate::now);

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class),
                new TransactionRequired(getProvider(TransactionManager.class)));
    }

    private static DataSource dataSource() {
        final AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setDriverClassName(Driver.class.getCanonicalName());
        dataSource.setUser("from8");
        dataSource.setPassword("from8");
        dataSource.setUrl("jdbc:mysql://localhost:3306/migration_from8");
        dataSource.setPoolSize(20);
        dataSource.setBorrowConnectionTimeout(60);
        dataSource.setUniqueResourceName("migration_from8");
        return dataSource;
    }

    private static class UserTransactionProvider implements Provider<UserTransaction> {

        @Override
        public UserTransaction get() {
            try {
                final UserTransactionImp userTransaction = new UserTransactionImp();
                userTransaction.setTransactionTimeout(300);
                return userTransaction;
            } catch (SystemException e) {
                throw new IllegalStateException("cannot create UserTransaction.", e);
            }
        }
    }

    private static class UserTransactionManagerProvider implements Provider<TransactionManager> {

        @Override
        public TransactionManager get() {
            try {
                final UserTransactionManager transactionManager = new UserTransactionManager();
                transactionManager.setForceShutdown(true);
                transactionManager.init();
                return transactionManager;
            } catch (SystemException e) {
                throw new IllegalStateException("cannot create UserTransactionManager.", e);
            }

        }
    }
}
