package org.netbeans.gradle.project.others;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.gradle.model.util.CollectionUtils;

public final class PluginClassImplementation {
    private final ClassFinder type;
    private final InvocationHandler invocationHandler;
    private final AtomicReference<Object> instanceRef;

    public PluginClassImplementation(
            ClassFinder type,
            Object delegateInstance) {
        this(type, new SimpleDelegator(delegateInstance));
    }

    public PluginClassImplementation(
            ClassFinder type,
            Object delegateInstance,
            InvocationHandlerFactory exceptionalCases) {
        this(type, new MultiDelegator(exceptionalCases, new SimpleDelegator(delegateInstance)));
    }

    private PluginClassImplementation(
            ClassFinder type,
            InvocationHandler invocationHandler) {

        ExceptionHelper.checkNotNullArgument(type, "type");
        ExceptionHelper.checkNotNullArgument(invocationHandler, "invocationHandler");

        this.type = type;
        this.invocationHandler = invocationHandler;
        this.instanceRef = new AtomicReference<>(null);
    }

    public Object tryGetAsPluginClass() {
        Object result = instanceRef.get();
        if (result == null) {
            instanceRef.compareAndSet(null, tryCreateProxyInstance());
            result = instanceRef.get();
        }
        return result;
    }

    private Object tryCreateProxyInstance() {
        Class<?> pluginClass = type.tryGetClass();
        if (pluginClass == null) {
            return null;
        }

        ClassLoader classLoader = pluginClass.getClassLoader();

        return Proxy.newProxyInstance(classLoader,
                new Class<?>[]{pluginClass},
                invocationHandler);
    }

    private static final class MultiDelegator
    implements
            InvocationHandler {

        private final InvocationHandlerFactory[] factories;

        public MultiDelegator(InvocationHandlerFactory... factories) {
            this.factories = factories.clone();
            CollectionUtils.checkNoNullElements(Arrays.asList(this.factories), "factories");
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (InvocationHandlerFactory factory: factories) {
                InvocationHandler handler = factory.tryGetInvocationHandler(proxy, method, args);
                if (handler != null) {
                    return handler.invoke(proxy, method, args);
                }
            }
            throw new UnsupportedOperationException("No handler for method: " + method);
        }
    }

    private static final class SimpleDelegator
    implements
            InvocationHandler,
            InvocationHandlerFactory {

        private final Object delegate;

        public SimpleDelegator(Object delegate) {
            ExceptionHelper.checkNotNullArgument(delegate, "delegate");
            this.delegate = delegate;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method delegateMethod
                    = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
            return delegateMethod.invoke(delegate, args);
        }

        @Override
        public InvocationHandler tryGetInvocationHandler(Object proxy, Method method, Object[] args) {
            return this;
        }
    }
}
