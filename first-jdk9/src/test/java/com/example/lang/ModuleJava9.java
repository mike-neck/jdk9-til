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
package com.example.lang;

import org.junit.jupiter.api.Test;

//import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Module;
import java.sql.Driver;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleJava9 {

    @Test
    void name() {
        final Module module = String.class.getModule();
        assertEquals("java.base", module.getName());
    }

    @Test
    void packages() {
        final Module module = int.class.getModule();
        Arrays.stream(module.getPackages())
                .forEach(System.out::println);
    }

    @Test
    void mod() {
        final Module module = Driver.class.getModule();
        System.out.println(module.getName());
//        final Module module1 = XmlElement.class.getModule();
//        System.out.println(module1.getName());
    }
}
