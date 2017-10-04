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
package com.example.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Provider;
import javax.transaction.TransactionManager;

public class TransactionRequired implements MethodInterceptor {

    private final Provider<TransactionManager> provider;

    public TransactionRequired(final Provider<TransactionManager> provider) {
        this.provider = provider;
    }


    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final TransactionManager tm = provider.get();
        tm.begin();
        try {
            final Object result = invocation.proceed();
            tm.commit();
            return result;
        } catch (final Throwable th) {
            tm.rollback();
            throw th;
        }
    }
}
