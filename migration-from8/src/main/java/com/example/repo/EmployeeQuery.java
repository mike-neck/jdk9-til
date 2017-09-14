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
package com.example.repo;

import com.example.entity.EmployeeEntity;
import com.example.entity.QAffiliationEntity;
import com.example.entity.QEmployeeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EmployeeQuery {

    private static final QEmployeeEntity EMPLOYEE_AS_E = QEmployeeEntity.employeeEntity;

    private static final QEmployeeEntity E = QEmployeeEntity.employeeEntity;

    private static final QAffiliationEntity AFFILIATION_AS_A = QAffiliationEntity.affiliationEntity;

    private static final QAffiliationEntity A = QAffiliationEntity.affiliationEntity;

    private final JPAQueryFactory queryFactory;

    @Inject
    public EmployeeQuery(final JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Optional<EmployeeEntity> findById(final long employeeId) {
        return Optional.ofNullable(queryFactory.selectFrom(EMPLOYEE_AS_E)
                .where(E.id.eq(employeeId))
                .fetchOne());
    }

    public List<EmployeeEntity> findByDepartmentId(final long departmentId) {
        return queryFactory.select(EMPLOYEE_AS_E)
                .from(E)
                .join(AFFILIATION_AS_A)
                .on(A.id.employeeId.eq(E.id))
                .where(A.id.departmentId.eq(departmentId))
                .fetch();
    }
}
