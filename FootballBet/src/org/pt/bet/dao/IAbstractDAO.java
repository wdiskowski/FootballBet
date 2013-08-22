package org.pt.bet.dao;

import java.io.Serializable;
import java.util.List;

public interface IAbstractDAO {

	public abstract Serializable save(Object transientInstance);

	public abstract void saveOrUpdate(Object transientInstance);

	public abstract void delete(Object persistentInstance);

	public abstract <T> List<T> findByExample(T instance);

	public abstract <T> T merge(T detachedInstance);

	public abstract void attachDirty(Object instance);

	public abstract void attachClean(Object instance);

}