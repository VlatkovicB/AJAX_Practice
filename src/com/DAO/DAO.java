package com.DAO;

import java.util.List;

public interface DAO<T> {

	// get all
	List<T> getAll();

	// create
	boolean insert(T t);

	// read
	T get(int id);

	// update
	boolean update(T t);

	// delete
	boolean delete(T t);
}
