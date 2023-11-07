package com.coreleo.util.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BatchParameter implements Iterable<Object> {
	private final List<Object> params = new ArrayList<>();

	public boolean isEmpty() {
		return params.isEmpty();
	}

	public boolean add(final Object e) {
		return params.add(e);
	}

	public Object get(final int index) {
		return params.get(index);
	}

	public void add(final int index, final Object element) {
		params.add(index, element);
	}

	public int size() {
		return params.size();
	}

	@Override
	public Iterator<Object> iterator() {
		return params.iterator();
	}

}
