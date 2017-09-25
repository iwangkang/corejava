package me.wangkang.aop;

import junit.framework.TestCase;

public class ProxyHandlerTest extends TestCase {
	
	public void testSayHello(){
		ProxyHandler proxy = new ProxyHandler(new HelloImpl());
		IHello hello = (IHello) proxy.proxyInstance();
		hello.sayHello();
	}
}
