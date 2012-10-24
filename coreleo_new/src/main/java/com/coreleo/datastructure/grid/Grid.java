package com.coreleo.datastructure.grid;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Leon Samaroo
 * 
 *         A data structure which maps a value to a row/column. Rows and columns can be referenced by either their index or by
 *         associating them with an id and using that id as a reference.
 * 
 * @param <V>
 *            - the type of value stored in this grid
 */
public interface Grid<V>
{

	/**
	 * 
	 * @return true if the value was added to the grid, false otherwise. e.g. may return false could be if the row or column
	 *         specified does not exist in this grid.
	 */
	public boolean put(int rowIndex, int columnIndex, V value);

	/**
	 * 
	 * @return previous value associated with specified row-column, or null if there was no mapping.
	 */
	public V remove(Object rowId, Object columnId);

	/**
	 * 
	 * @return previous value associated with specified row-column index, or null if there was no mapping.
	 */
	public V remove(int rowIndex, int columnIndex);

	public boolean removeRow(int rowIndex);

	public boolean removeRow(Object rowId);

	public boolean removeColumn(int columnIndex);

	public boolean removeColumn(Object columnId);

	/**
	 * 
	 * @return true if the value was added to the grid, false otherwise. e.g. may return false could be if the row or column
	 *         specified does not exist in this grid.
	 */
	public boolean put(Object rowId, Object columnId, V value);

	/**
	 * @return the value located at the specified row and column, or null if the Grid contains no value at this row-column
	 *         combination.
	 * 
	 */
	public V get(Object rowId, Object columnId);

	/**
	 * @return the value located at the specified row and column index, or null if the Grid contains no value at this row-column
	 *         index combination.
	 * 
	 */
	public V get(int rowIndex, int columnIndex);

	public int getRowCount();

	public int getColumnCount();

	/**
	 * Adds a new column at the given index to the grid and associates it with the given column id. If this grid already contains
	 * this object as a column, then the column is not added.
	 * 
	 * @return true if the column was added, false otherwise.
	 * 
	 */
	public boolean addColumn(int index, Object columnId);

	/**
	 * Adds a new column to the grid and associates it with the given column id. If this grid already contains this object as a
	 * column, then the column is not added.
	 * 
	 * @return true if the column was added, false otherwise.
	 * 
	 */
	public boolean addColumn(Object columnId);

	/**
	 * Adds a new row to the grid at the given index and associates it with the given row id. If this grid already contains this
	 * row, then no row is added.
	 * 
	 * @return true if the row was added, false otherwise.
	 * 
	 */
	public boolean addRow(int index, Object rowId);

	/**
	 * Adds a new row to the grid and associates it with the given row id. If this grid already contains this row, then no row is
	 * added.
	 * 
	 * @return true if the row was added, false otherwise.
	 * 
	 */
	public boolean addRow(Object rowId);

	/**
	 * 
	 * @return the id associated with the column at the given index.
	 */
	public Object getColumnId(int columnIndex);

	/**
	 * 
	 * @return the id associated with the row at the given index.
	 */
	public Object getRowId(int rowIndex);

	public boolean containsRow(Object rowId);

	public boolean containsColumn(Object columnId);

	public boolean containsValue(V value);

	/**
	 * Removes the values stored in this grid. This method only removes the values stored in the Grid, not the rows or columns.
	 * After calling this method, getValuesCount should return 0.
	 * 
	 * 
	 * To remove the rows and columns as well as the values stored in this Grid use the method clear() instead.
	 * 
	 * @return a reference to this Grid
	 */
	public Grid<V> clearValues();

	/**
	 * Removes the values, rows and columns stored in this grid. After a call to the clear method, getRowCount, getColumnCount and
	 * getValuesCount should all return 0.
	 * 
	 * @return a reference to this Grid
	 * 
	 * 
	 */
	public Grid<V> clear();

	/**
	 * 
	 * @return the values stored in this Grid. There is no guarantee of the order in which the values are returned.
	 */
	public Collection<V> values();

	/**
	 * 
	 * @return a count of all the values added to this Grid
	 */
	public int getValuesCount();

	/**
	 * 
	 * @return the index of the row associated with the given row id.
	 */
	public int getRowIndex(Object rowId);

	/**
	 * 
	 * @return the index of the column associated with the given column id.
	 */
	public int getColumnIndex(Object columnId);

	public boolean isRowEmpty(int rowIndex);

	public boolean isRowEmpty(Object rowId);

	public boolean isColumnEmpty(int columnIndex);

	public boolean isColumnEmpty(Object columnId);

	public List<V> getRowValues(int rowIndex, boolean includeNullValues);

	public List<V> getRowValues(Object rowId, boolean includeNullValues);

	public List<V> getColumnValues(int columnIndex, boolean includeNullValues);

	public List<V> getColumnValues(Object columnId, boolean includeNullValues);

	public List<Object> getColumnIds();

	public List<Object> getRowIds();

	/**
	 * 
	 * Sort the data by the given columns. It uses the set Comparator for the given columns. If none was set, the default
	 * "Natural order" comparator is used.
	 */
	public void sort(int[] columnIndexes);

	/**
	 * 
	 * Sort the data by the given columns. It uses the set Comparator for the given columns. If none was set, the default
	 * "Natural order" comparator is used.
	 */
	public void sort(int columnIndex);

	public void setComparator(int column, Comparator<?> comparator);

	public Comparator<?> getComparator(int column);
}
