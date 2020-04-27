/*
 * (BSD License: https://opensource.org/licenses/BSD-3-Clause)
 *
 * Copyright (c) 2016-2017, Dan North and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the
 * following conditions are met:
 *
 * * Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 * * Neither the name of JGoTesting nor the names of
 *   its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jgotesting.internal;

import org.jgotesting.Testing;
import org.jgotesting.events.Failure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * invokes static method with the same name and parameters as a method
 * invoked on a dynamic proxy.
 *
 * <p>The method intercepts an {@link AssertionError} and
 * posts a {@link Failure JGoTesting error}
 * instead of unrolling the stack. This allows multiple assertions
 * within the same test.</p>
 */
public class DelegateToStaticMethod implements InvocationHandler {
    private final Map<MethodSignature, Method> staticMethods;

    /** used to look up method by name and parameters */
    protected static class MethodSignature {
        private final String name;
        private final Class<?>[] parameterTypes;

        private MethodSignature(String name, Class<?>[] parameterTypes) {
            this.name = name;
            this.parameterTypes = parameterTypes;
        }

        public MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        public MethodSignature withName(String newName) {
            return new MethodSignature(newName, parameterTypes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodSignature that = (MethodSignature) o;

            if (!name.equals(that.name)) return false;
            // FIXME: Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(parameterTypes, that.parameterTypes);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + Arrays.hashCode(parameterTypes);
            return result;
        }
    }

    public DelegateToStaticMethod(Class target) {
        staticMethods = buildPublicStaticMethodMap(target);
    }

    private Map<MethodSignature, Method> buildPublicStaticMethodMap(Class<?> target) {
        Map<MethodSignature, Method> result = new HashMap<>();

        for (Method method : target.getDeclaredMethods()) {
            // method has name, modifiers, parameters
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                MethodSignature signature = buildMethodSignature(method);
                result.put(signature, method);
            }
        }
        return result;
    }

    /**
     * override this to map e.g. <code>checkBlah</code> to <code>assertBlah</code>
     */
    protected MethodSignature buildMethodSignature(Method method) {
        return new MethodSignature(method);
    }

    /**
     * Invoke a static method and capture its error if it fails.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodSignature key = buildMethodSignature(method);
        Method staticMethod = staticMethods.get(key);

        try {
            return staticMethod.invoke(null, args);
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof AssertionError) {
                Testing._addFailure(cause);
                return null;
            } else {
               throw cause;
            }
        }
    }
}
