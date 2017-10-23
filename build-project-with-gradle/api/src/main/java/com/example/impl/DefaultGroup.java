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
package com.example.impl;

import com.example.Group;
import com.example.User;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.util.Collection;
import java.util.Iterator;

/**
 * default implementation of {@link com.example.Group}
 */
public class DefaultGroup<U extends User> implements Group<U> {

    private final String name;
    private final ImmutableSet<U> users;

    private DefaultGroup(final String name, final ImmutableSet<U> users) {
        this.name = name;
        this.users = users;
    }

    @SafeVarargs
    public DefaultGroup(final String name, final U... users) {
        this(name, Sets.immutable.of(users));
    }

    @Override
    public int size() {
        return users.size();
    }

    @Override
    public boolean isEmpty() {
        return users.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return users.contains(o);
    }

    @Override
    public Iterator<U> iterator() {
        return users.iterator();
    }

    @Override
    public Object[] toArray() {
        return users.toArray(new Object[0]);
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return users.toArray(a);
    }

    @Override
    public boolean add(final U u) {
        throw new UnsupportedOperationException("add not supported");
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException("remove not supported");
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return users.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends U> c) {
        throw new UnsupportedOperationException("add not supported");
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException("retain not supported");
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException("remove not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear not supported");
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Group {" + name + " with size " + size() + "}";
    }
}
