package com.lagou.edu.annotation;

import com.lagou.edu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionEnabledAnnotationProxyManager
{
    private TransactionManager transactionManager;

    public TransactionEnabledAnnotationProxyManager(TransactionManager transactionManager)
    {

        this.transactionManager = transactionManager;
    }

    public Object proxyFor(Object object)
    {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new AnnotationTransactionInvocationHandler(object, transactionManager));
    }
}

class AnnotationTransactionInvocationHandler implements InvocationHandler
{
    private Object proxied;
    private TransactionManager transactionManager;

    AnnotationTransactionInvocationHandler(Object object, TransactionManager transactionManager)
    {
        this.proxied = object;
        this.transactionManager = transactionManager;
    }

    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable
    {
        Method originalMethod = proxied.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (!originalMethod.isAnnotationPresent(MyTransactional.class))
        {
            return method.invoke(proxied, objects);
        }

        transactionManager.beginTransaction();
        Object result = null;
        try
        {
            result = method.invoke(proxied, objects);
            transactionManager.commit();
        } catch (Exception e)
        {
            transactionManager.rollback();
        }
        return result;
    }
}