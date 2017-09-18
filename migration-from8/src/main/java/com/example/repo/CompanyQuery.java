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

import com.example.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CompanyQuery {

    private static final QCompanyEntity COMPANY_AS_C = QCompanyEntity.companyEntity; 

    private static final QCompanyEntity C = QCompanyEntity.companyEntity;

    private static final QDepartmentEntity DEPARTMENT_AS_D = QDepartmentEntity.departmentEntity;

    private static final QDepartmentEntity D = QDepartmentEntity.departmentEntity;

    private static final QAffiliationEntity AFFILIATION_AS_A = QAffiliationEntity.affiliationEntity;

    private static final QAffiliationEntity A = QAffiliationEntity.affiliationEntity;

    private final JPAQueryFactory queryFactory;

    @Inject
    public CompanyQuery(final JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Optional<CompanyEntity> findById(final long companyId) {
        final CompanyEntity entity = queryFactory.selectFrom(COMPANY_AS_C)
                .where(C.id.eq(companyId))
                .fetchOne();
        return Optional.ofNullable(entity);
    }

    public List<CompanyEntity> findByEmployeeId(final long employeeId) {
        return queryFactory.select(COMPANY_AS_C)
                .from(C)
                .join(C.departments, DEPARTMENT_AS_D)
                .join(AFFILIATION_AS_A)
                .on(A.id.departmentId.eq(D.id))
                .where(A.id.employeeId.eq(employeeId))
                .fetch();
    }

    public List<CompanyEntity> findAll() {
        return queryFactory.selectFrom(COMPANY_AS_C)
                .fetch();
    }
}
