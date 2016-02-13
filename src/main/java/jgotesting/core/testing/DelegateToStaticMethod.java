package jgotesting.core.testing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class DelegateToStaticMethod implements InvocationHandler {
    private final Map<MethodSignature, Method> staticMethods;

    static class MethodSignature {
        private final String name;
        private final Class<?>[] parameterTypes;

        public MethodSignature(Method method) {
            this.name = method.getName();
            this.parameterTypes = method.getParameterTypes();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodSignature that = (MethodSignature) o;

            if (!name.equals(that.name)) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
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
        Map<MethodSignature, Method> result = new HashMap<MethodSignature, Method>();

        for (Method method : target.getDeclaredMethods()) {
            // method has name, modifiers, parameters
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                MethodSignature signature = new MethodSignature(method);
                result.put(signature, method);
            }
        }
        return result;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method staticMethod = staticMethods.get(new MethodSignature(method));
        try {
            return staticMethod.invoke(null, args);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof AssertionError) {
                Testing.failWithError(e.getTargetException());
                return null;
            } else {
               throw e.getTargetException();
            }
        }
    }
}
