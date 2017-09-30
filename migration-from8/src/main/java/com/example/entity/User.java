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
package com.example.entity;

import com.example.value.UserId;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDate;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(table = "generated_ids"
            , pkColumnName = "pk_name"
            , valueColumnName = "generated_id"
            , pkColumnValue = "user_id")
    @Column(name = "id")
    public final UserId userId;

    public final String name;

    public final LocalDate hireDate;

    public User(final String name, final LocalDate hireDate) {
        this.userId = null;
        this.name = name;
        this.hireDate = hireDate;
    }

    public User(final UserId userId, final String name, final LocalDate hireDate) {
        this.userId = userId;
        this.name = name;
        this.hireDate = hireDate;
    }
}
