package com.coreleo.datastructure.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coreleo.util.CollectionUtil;
import com.coreleo.util.ComparatorUtil;
import com.coreleo.util.NumberUtil;

public class SimpleGrid<R, C, V> implements Grid<R, C, V>, Serializable {
	private static final long serialVersionUID = 1L;

	protected final static String ROW_HEADER_KEY_PREFIX = "R";
	protected final static String COLUMN_HEADER_KEY_PREFIX = "C";

	protected List<R> rowIds;
	protected List<C> columnIds;
	protected Map<String, V> map;
	protected Map<Integer, Comparator<V>> comparators;

	public SimpleGrid() {
		rowIds = new ArrayList<R>();
		columnIds = new ArrayList<C>();
		map = new HashMap<String, V>();
		comparators = new HashMap<Integer, Comparator<V>>();
	}

	private String getKey(final R rowId, final C columnId) {
		final StringBuffer buff = new StringBuffer();
		buff.append(ROW_HEADER_KEY_PREFIX);
		buff.append(rowId);
		buff.append("+");
		buff.append(COLUMN_HEADER_KEY_PREFIX);
		buff.append(columnId);
		return buff.toString();
	}

	@Override
	public String toString() {
		final StringBuffer buff = new StringBuffer();
		for (int i = 0; i < rowIds.size(); i++) {
			for (int j = 0; j < columnIds.size(); j++) {
				buff.append(get(i, j));
				buff.append(" ");
			}
			buff.append("\n");
		}

		return buff.toString();
	}

	@Override
	public Comparator<V> getComparator(final int column) {
		return comparators.get(NumberUtil.toIntegerObject(column));
	}

	@Override
	public void setComparator(final int column, final Comparator<V> comparator) {
		comparators.put(NumberUtil.toIntegerObject(column), comparator);
	}

	@Override
	public boolean addColumn(final int index, final C columnId) {
		if (!columnIds.contains(columnId)) {
			columnIds.add(index, columnId);
			return true;
		}

		return false;
	}

	@Override
	public boolean addColumn(final C columnId) {
		if (!columnIds.contains(columnId)) {
			columnIds.add(columnId);
			return true;
		}

		return false;
	}

	@Override
	public boolean addRow(final int index, final R rowId) {
		if (!rowIds.contains(rowId)) {
			rowIds.add(index, rowId);
			return true;
		}

		return false;
	}

	@Override
	public boolean addRow(final R rowId) {
		if (!rowIds.contains(rowId)) {
			rowIds.add(rowId);
			return true;
		}

		return false;
	}

	@Override
	public V get(final int rowIndex, final int columnIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return get(rowIds.get(rowIndex), columnIds.get(columnIndex));
		}
		return null;
	}

	@Override
	public V get(final R rowId, final C columnId) {
		return map.get(getKey(rowId, columnId));
	}

	@Override
	public int getColumnCount() {
		return columnIds.size();
	}

	@Override
	public C getColumnId(final int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return columnIds.get(columnIndex);
		}

		return null;
	}

	@Override
	public int getRowCount() {
		return rowIds.size();
	}

	@Override
	public R getRowId(final int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			return rowIds.get(rowIndex);
		}

		return null;
	}

	@Override
	public boolean put(final int rowIndex, final int columnIndex, final V value) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			put(rowIds.get(rowIndex), columnIds.get(columnIndex), value);
			return true;
		}
		return false;
	}

	@Override
	public boolean put(final R rowId, final C columnId, final V value) {
		if (CollectionUtil.contains(rowIds, rowId) && CollectionUtil.contains(columnIds, columnId)) {
			map.put(getKey(rowId, columnId), value);
			return true;
		}

		return false;
	}

	@Override
	public V remove(final int rowIndex, final int columnIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return remove(rowIds.get(rowIndex), columnIds.get(columnIndex));
		}
		return null;
	}

	@Override
	public V remove(final R rowId, final C columnId) {
		if (CollectionUtil.contains(rowIds, rowId) && CollectionUtil.contains(columnIds, columnId)) {
			return map.remove(getKey(rowId, columnId));
		}

		return null;
	}

	@Override
	public boolean removeRow(final int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			return removeRow(rowIds.get(rowIndex));
		}

		return false;
	}

	@Override
	public boolean removeRow(final R rowId) {
		if (CollectionUtil.contains(rowIds, rowId)) {
			for (int i = 0; i < getColumnCount(); i++) {
				remove(rowId, columnIds.get(i));
			}
			rowIds.remove(rowId);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeColumn(final int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return removeColumn(columnIds.get(columnIndex));
		}

		return false;
	}

	@Override
	public boolean removeColumn(final C columnId) {
		if (CollectionUtil.contains(columnIds, columnId)) {
			for (int i = 0; i < getRowCount(); i++) {
				remove(rowIds.get(i), columnId);
			}
			columnIds.remove(columnId);
			return true;
		}
		return false;
	}

	@Override
	public Grid<R, C, V> clear() {
		rowIds.clear();
		columnIds.clear();
		map.clear();
		return this;
	}

	@Override
	public Grid<R, C, V> clearValues() {
		map.clear();
		return this;
	}

	@Override
	public boolean containsColumn(final C columnId) {
		return columnIds.contains(columnId);
	}

	@Override
	public boolean containsRow(final R rowId) {
		return rowIds.contains(rowId);
	}

	@Override
	public boolean containsValue(final V value) {
		return map.containsValue(value);
	}

	@Override
	public int getValuesCount() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public int getColumnIndex(final C columnId) {
		return columnIds.indexOf(columnId);
	}

	@Override
	public int getRowIndex(final R rowId) {
		return rowIds.indexOf(rowId);
	}

	@Override
	public boolean isRowEmpty(final int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			final R rowId = rowIds.get(rowIndex);
			for (int columnIndex = 0; columnIndex < columnIds.size(); columnIndex++) {
				if (get(rowId, columnIds.get(columnIndex)) != null) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isRowEmpty(final R rowId) {
		return isRowEmpty(getRowIndex(rowId));
	}

	@Override
	public boolean isColumnEmpty(final int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			final C columnId = columnIds.get(columnIndex);
			for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
				if (get(rowIds.get(rowIndex), columnId) != null) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isColumnEmpty(final C columnId) {
		return isColumnEmpty(getColumnIndex(columnId));
	}

	@Override
	public List<V> getRowValues(final int rowIndex, final boolean includeNullValues) {
		final List<V> list = new ArrayList<V>();
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			final R rowId = rowIds.get(rowIndex);
			for (int columnIndex = 0; columnIndex < columnIds.size(); columnIndex++) {
				final V value = get(rowId, columnIds.get(columnIndex));
				if (value != null || includeNullValues) {
					list.add(value);
				}
			}
		}
		return list;
	}

	@Override
	public List<V> getRowValues(final R rowId, final boolean includeNullValues) {
		return getRowValues(getRowIndex(rowId), includeNullValues);
	}

	@Override
	public List<V> getColumnValues(final int columnIndex, final boolean includeNullValues) {
		final List<V> list = new ArrayList<V>();
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			final C columnId = columnIds.get(columnIndex);
			for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
				final V value = get(rowIds.get(rowIndex), columnId);
				if (value != null || includeNullValues) {
					list.add(value);
				}
			}
		}

		return list;
	}

	@Override
	public List<V> getColumnValues(final C columnId, final boolean includeNullValues) {
		return getColumnValues(getColumnIndex(columnId), includeNullValues);
	}

	@Override
	public List<C> getColumnIds() {
		return Collections.unmodifiableList(columnIds);
	}

	@Override
	public List<R> getRowIds() {
		return Collections.unmodifiableList(rowIds);
	}

	@Override
	public void sort(final int columnIndex) {
		sort(new int[] { columnIndex });
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sort(final int[] columnIndexes) {
		final List<SortableEntry> list = new ArrayList<SortableEntry>(rowIds.size());
		for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
			list.add(new SortableEntry(rowIndex, columnIndexes));
		}

		CollectionUtil.sort((Collection) list);
		final List<R> sortedRowIds = new ArrayList<R>(rowIds.size());
		for (int i = 0; i < list.size(); i++) {
			sortedRowIds.add(list.get(i).getRowId());
		}
		this.rowIds = sortedRowIds;
	}

	/**
	 * Convenience method, sets the comparator for the given column before
	 * calling sort.
	 */
	public void sort(final int columnIndex, final Comparator<V> comparator) {
		setComparator(columnIndex, comparator);
		sort(columnIndex);
	}

	/**
	 * Convenience method, sets the comparator for the given columns before
	 * calling sort.
	 */
	public void sort(final int[] columnIndexes, final Comparator<V> comparator) {
		for (final int columnIndexe : columnIndexes) {
			setComparator(columnIndexe, comparator);
		}
		sort(columnIndexes);
	}

	private class SortableEntry implements Comparable<SortableEntry> {
		private final int rowIndex;
		private final int[] columnIndexesToSortBy;
		private final R rowId;

		public SortableEntry(final int rowIndex, final int[] columnIndexesToSortBy) {
			super();
			this.rowIndex = rowIndex;
			this.columnIndexesToSortBy = columnIndexesToSortBy;
			this.rowId = SimpleGrid.this.getRowId(rowIndex);
		}

		public R getRowId() {
			return rowId;
		}

		public int getRowIndex() {
			return rowIndex;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(final SortableEntry another) {
			if (another == null) {
				return -1;
			}

			for (final int columnIndex : columnIndexesToSortBy) {
				Comparator<V> comparator = SimpleGrid.this.getComparator(columnIndex);
				if (comparator == null) {
					comparator = ComparatorUtil.NATURAL_COMPARATOR;
				}
				if (CollectionUtil.isInbounds(SimpleGrid.this.columnIds, columnIndex)) {
					final V value1 = SimpleGrid.this.get(rowIndex, columnIndex);
					final V value2 = SimpleGrid.this.get(another.getRowIndex(), columnIndex);

					final int compare = comparator.compare(value1, value2);
					if (compare != 0) {
						return compare;
					}
				}
			}

			return 0;
		}

	}

}
