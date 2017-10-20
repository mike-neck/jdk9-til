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

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;

import javax.inject.Inject;
import javax.sql.DataSource;

public class AppConfig implements Config {

    @Inject
    private DataSource dataSource;

    @Inject
    private Dialect dialect;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }
}
