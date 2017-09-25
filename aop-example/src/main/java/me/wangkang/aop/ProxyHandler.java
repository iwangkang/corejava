package me.wangkang.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {

	private Object target;

	public ProxyHandler(Object target) {
		this.target = target;
	}

	public Object proxyInstance() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("aspect before ......");
		Object result = method.invoke(this.target, args);
		System.out.println("aspect after ......");
		return result;
	}

}
