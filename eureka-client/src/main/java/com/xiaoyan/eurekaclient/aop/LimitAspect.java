package com.xiaoyan.eurekaclient.aop;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.xiaoyan.eurekaclient.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Aspect
@Configuration
public class LimitAspect {

	private static Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁

	//根据IP分不同的令牌桶, 每天自动清理缓存
// cachebuilder是一种google的缓存
	private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(1, TimeUnit.DAYS)
			.build(new CacheLoader<String, RateLimiter>() {
				@Override
				//RateLimiter是一种令牌桶，其目的是为了保证在如下情况下，资源利用的最大化
				//服务器每秒处理的能力只有1000（这是一个宏观的平均值，实际上可以接受上下波动）.之中4秒分别来了1200，1300，800，700
				//如果采用漏桶算法，那么前两秒都只会处理1000，那么就造成了资源的浪费。（因为实际上1200，1300数据库是处理的了的）
				//RateLimiter就是改进方案之一，令牌桶，令牌桶用恒定的方式产生令牌，比如这个桶设定每秒产出1000令牌，第一秒消耗800则第二秒桶内就有1200令牌
				public RateLimiter load(String key) {
					// 新的IP初始化 每秒只发出5个令牌
					return RateLimiter.create(5);
				}
			});
	
	//Service层切点  限流
	//带servicelimt这个注解的，都会被匹配上around这个advice
	@Pointcut("@annotation(com.xiaoyan.eurekaclient.aop.ServiceLimit)")
	public void ServiceAspect() {
		
	}
	
    @Around("ServiceAspect()")
	//ProceedingJoinPoint是环绕方法的独有类，用来执行join point 即目标方法
    public  Object around(ProceedingJoinPoint joinPoint) {
		//用反射
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		//用反射去获取servicelimit 即注解。
		ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
		//根据注解的type确定操作方式
		ServiceLimit.LimitType limitType = limitAnnotation.limitType();
		//获得注解中配置的key
		String key = limitAnnotation.key();
		Object obj = null;
		try {
			if(limitType.equals(ServiceLimit.LimitType.IP)){
				key = IPUtils.getIpAddr();
			}
			//这里之所以没有put是因为在cachebuilder中使用了CacheLoader的缘故，它将为所有没有key值的value提供一个默认的内容。
			//所以这里不加put就ok
			RateLimiter rateLimiter = caches.get(key);
			//拿取令牌，如果拿不到，就不执行目标方法
			Boolean flag = rateLimiter.tryAcquire();
			if(flag){
				try {
					lock.lock();
					obj = joinPoint.proceed();
				}catch (Throwable e){
					System.out.println("err is comming !!!");
					System.out.println(e.toString());
				}finally {
					lock.unlock();
				}
			}else{
				throw new Exception("小同志，你访问的太频繁了");
			}
		} catch (Throwable e) {
			System.out.println("小同志，你访问的太频繁了");
			System.out.println(e.toString());
		}
		return obj;
    } 
}
