package com.xiaoyan.eurekaclient.dynamicquery;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 动态jpql/nativesql查询的实现类
 *  因为jpa对于动态mysql支持比较不良.
 *
 */
@Repository
public class DynamicQueryImpl implements DynamicQuery {

	Logger logger = LoggerFactory.getLogger(DynamicQueryImpl.class);
	//这是一种接口,用来连接内存和数据库.在框架中的EntityManager不需要用Factory进行创建,而只需要使用@PersistenceContext这个注解进行引入.
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void save(Object entity) {
		// 持久化方法传入的entity一开始并不存在于数据库中,所以使用persist方法让其进入持久化状态,即进入数据库中
		em.persist(entity);
	}

	@Override
	public void update(Object entity) {
		em.merge(entity);
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object entityid) {
		delete(entityClass, new Object[] { entityid });
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		for (Object id : entityids) {
			em.remove(em.getReference(entityClass, id));
		}
	}
	// 使用原生sql进行处理,因为jpa不支持动态sql,所以只能使用EntityManager手写一个
	private Query createNativeQuery(String sql, Object... params) {
		// 这个方法会返回一个	Query 类,这个类可以用来执行其已经获得的sql语句
		Query q = em.createNativeQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i + 1, params[i]); // 与Hiberante不同,jpa
													// query从位置1开始
			}
		}
		return q;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> nativeQueryList(String nativeSql, Object... params) {
		Query q = createNativeQuery(nativeSql, params);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.TO_LIST);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> nativeQueryListModel(Class<T> resultClass,
			String nativeSql, Object... params) {
		Query q = createNativeQuery(nativeSql, params);;
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(resultClass));
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> nativeQueryListMap(String nativeSql, Object... params) {
		Query q = createNativeQuery(nativeSql, params);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.getResultList();
	}
	
	@Override
	public Object nativeQueryObject(String nativeSql, Object... params) {
		return createNativeQuery(nativeSql, params).getSingleResult();
	}
	@Override
	public int nativeExecuteUpdate(String nativeSql, Object... params) {
		return createNativeQuery(nativeSql, params).executeUpdate();
	}

	@Override
	public Object[] nativeQueryArray(String nativeSql, Object... params) {
		return (Object[]) createNativeQuery(nativeSql, params).getSingleResult();
	}

}
