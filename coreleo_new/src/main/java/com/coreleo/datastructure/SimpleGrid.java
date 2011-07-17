package com.coreleo.datastructure;

import java.io.Serializable;
import java.util.*;
import com.coreleo.util.CollectionUtil;
import com.coreleo.util.ComparatorUtil;
import com.coreleo.util.NumberUtil;

public class SimpleGrid implements Grid, Serializable {
	private static final long serialVersionUID = 1L;

	protected final static String ROW_HEADER_KEY_PREFIX = "R";
	protected final static String COLUMN_HEADER_KEY_PREFIX = "C";

	protected List rowIds;
	protected List columnIds;
	protected Map map;
	protected Map comparators;

	public SimpleGrid() {
		rowIds = new ArrayList();
		columnIds = new ArrayList();
		map = new HashMap();
		comparators = new HashMap();
	}

	/**
	 * 
	 * This constructor will create a Grid with the specified number of rows and
	 * columns. The names of the rows and column will be defaulted to
	 * SimpleGrid.DEFAULT_ROW_NAME + rowIndex and SimpleGrid.DEFAULT_COLUMN_NAME
	 * + columnIndex respectively.
	 * 
	 */
	public SimpleGrid(int rows, int columns) {
		this();

		for (int i = 0; i < rows; i++) {
			rowIds.add(ROW_HEADER_KEY_PREFIX + i);
		}

		for (int i = 0; i < columns; i++) {
			columnIds.add(COLUMN_HEADER_KEY_PREFIX + i);
		}
	}

	private Object getKey(Object rowId, Object columnId) {
		 StringBuffer buff = new StringBuffer();
		 buff.append(ROW_HEADER_KEY_PREFIX);
		 buff.append(rowId);
		 buff.append("+");
		 buff.append(COLUMN_HEADER_KEY_PREFIX);
		 buff.append(columnId);
		 return buff.toString();
	}
	
	
	 

	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < rowIds.size(); i++) {
			for (int j = 0; j < columnIds.size(); j++) {
				buff.append(get(i, j));
				buff.append(" ");
			}
			buff.append("\n");
		}

		return buff.toString();
	}
	

	public Comparator getComparator(int column) {
		return (Comparator) comparators.get( NumberUtil.toIntegerObject(column));
	}


	public void setComparator(int column, Comparator comparator) {
		comparators.put(NumberUtil.toIntegerObject(column), comparator);
	}

	
	public boolean addColumn(int index, Object columnId) {
		if (!columnIds.contains(columnId)) {
			columnIds.add(index, columnId);
			return true;
		}

		return false;
	}

	public boolean addColumn(Object columnId) {
		if (!columnIds.contains(columnId)) {
			columnIds.add(columnId);
			return true;
		}

		return false;
	}

	public boolean addRow(int index, Object rowId) {
		if (!rowIds.contains(rowId)) {
			rowIds.add(index, rowId);
			return true;
		}

		return false;
	}

	public boolean addRow(Object rowId) {
		if (!rowIds.contains(rowId)) {
			rowIds.add(rowId);
			return true;
		}

		return false;
	}


	public Object get(int rowIndex, int columnIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return get(rowIds.get(rowIndex), columnIds.get(columnIndex));
		}
		return null;
	}


	public Object get(Object rowId, Object columnId) {
		return map.get(getKey(rowId, columnId));
	}

	public int getColumnCount() {
		return columnIds.size();
	}

	public Object getColumnId(int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return columnIds.get(columnIndex);
		}

		return null;
	}

	public int getRowCount() {
		return rowIds.size();
	}

	public Object getRowId(int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			return rowIds.get(rowIndex);
		}

		return null;
	}

	public boolean put(int rowIndex, int columnIndex, Object value) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			put(rowIds.get(rowIndex), columnIds.get(columnIndex), value );
			return true;
		}
		return false;
	}

	public boolean put(Object rowId, Object columnId, Object value) {
		if (CollectionUtil.contains(rowIds, rowId) && CollectionUtil.contains(columnIds, columnId)) {
			map.put(getKey(rowId, columnId), value);
			return true;
		}

		return false;
	}

	
	public Object remove(int rowIndex, int columnIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex) && CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return remove(rowIds.get(rowIndex), columnIds.get(columnIndex) );
		}
		return null;
	}

	public Object remove(Object rowId, Object columnId) {
		if (CollectionUtil.contains(rowIds, rowId) && CollectionUtil.contains(columnIds, columnId)) {
			return map.remove(getKey(rowId, columnId));
		}

		return null;
	}

	public boolean removeRow(int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			return removeRow( rowIds.get(rowIndex) );
		}
		
		return false;
	}

	public boolean removeRow(Object rowId) {
		if (CollectionUtil.contains(rowIds, rowId)) {
			for (int i = 0; i < getColumnCount(); i++) {
				remove(rowId, columnIds.get(i));
			}
			rowIds.remove(rowId);
			return true;
		}
		return false;
	}

	
	public boolean removeColumn(int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			return removeColumn( columnIds.get(columnIndex) );
		}
		
		return false;
	}

	public boolean removeColumn(Object columnId) {
		if (CollectionUtil.contains(columnIds, columnId)) {
			for (int i = 0; i < getRowCount(); i++) {
				remove( rowIds.get(i), columnId );
			}
			columnIds.remove(columnId);
			return true;
		}
		return false;
	}

	public Grid clear() {
		rowIds.clear();
		columnIds.clear();
		map.clear();
		return this;
	}

	public Grid clearValues() {
		map.clear();
		return this;
	}

	public boolean containsColumn(Object columnId) {
		return columnIds.contains(columnId);
	}

	public boolean containsRow(Object rowId) {
		return rowIds.contains(rowId);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public int getValuesCount() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}

	public int getColumnIndex(Object columnId) {
		return columnIds.indexOf(columnId);
	}

	public int getRowIndex(Object rowId) {
		return rowIds.indexOf(rowId);
	}

	public boolean isRowEmpty(int rowIndex) {
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			Object rowId = rowIds.get(rowIndex);
			for (int columnIndex = 0; columnIndex < columnIds.size(); columnIndex++) {
				if (get( rowId, columnIds.get(columnIndex) ) != null) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isRowEmpty(Object rowId) {
		return isRowEmpty(getRowIndex(rowId));
	}

	public boolean isColumnEmpty(int columnIndex) {
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			Object columnId = columnIds.get(columnIndex);
			for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
				if ( get(rowIds.get(rowIndex), columnId) != null) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isColumnEmpty(Object columnId) {
		return isColumnEmpty(getColumnIndex(columnId));
	}

	public List getRowValues(int rowIndex, boolean includeNullValues) {
		List list = new ArrayList();
		if (CollectionUtil.isInbounds(rowIds, rowIndex)) {
			Object rowId = rowIds.get(rowIndex);
			for (int columnIndex = 0; columnIndex < columnIds.size(); columnIndex++) {
				Object value = get(rowId, columnIds.get(columnIndex));
				if (value != null || includeNullValues) {
					list.add(value);
				}
			}
		}
		return list;
	}

	public List getRowValues(Object rowId, boolean includeNullValues) {
		return getRowValues(getRowIndex(rowId), includeNullValues);
	}

	public List getColumnValues(int columnIndex, boolean includeNullValues) {
		List list = new ArrayList();
		if (CollectionUtil.isInbounds(columnIds, columnIndex)) {
			Object columnId = columnIds.get(columnIndex);
			for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
				Object value = get(rowIds.get(rowIndex), columnId);
				if (value != null || includeNullValues) {
					list.add(value);
				}
			}
		}

		return list;
	}

	public List getColumnValues(Object columnId, boolean includeNullValues) {
		return getColumnValues(getColumnIndex(columnId), includeNullValues);
	}

	public List getColumnIds() {
		return Collections.unmodifiableList(columnIds);
	}

	public List getRowIds() {
		return Collections.unmodifiableList(rowIds);
	}


	public void sort(int columnIndex) {
		sort( new int[]{columnIndex});
	}
	
	
	public void sort(int[] columnIndexes ) {
		List list = new ArrayList(rowIds.size());
		for (int rowIndex = 0; rowIndex < rowIds.size(); rowIndex++) {
				list.add(new SortableEntry(rowIndex, columnIndexes));
		}

		CollectionUtil.sort(list);
		List sortedRowIds = new ArrayList(rowIds.size());
		for (int i = 0; i < list.size(); i++) {
			sortedRowIds.add(((SortableEntry) list.get(i)).getRowId());
		}
		this.rowIds = sortedRowIds;
	}
	
	/**
	 * Convenience method, sets the comparator for the given column before calling sort.
	 */
	public void sort(int columnIndex, Comparator comparator) {
		setComparator(columnIndex, comparator);
		sort(columnIndex);
	}
	
	/**
	 * Convenience method, sets the comparator for the given columns before calling sort.
	 */
	public void sort(int[] columnIndexes, Comparator comparator ) {
		for (int i = 0; i < columnIndexes.length; i++) {
			setComparator(columnIndexes[i], comparator);
		}
		sort(columnIndexes);
	}
	
	

	private class SortableEntry implements Comparable {
		private int rowIndex;
		private int[] columnIndexesToSortBy;
		private Object rowId;
		
		
		public SortableEntry(int rowIndex, int[] columnIndexesToSortBy) {
			super();
			this.rowIndex = rowIndex;
			this.columnIndexesToSortBy = columnIndexesToSortBy;
			this.rowId = SimpleGrid.this.getRowId(rowIndex);
		}
		

		
		public Object getRowId() {
			return rowId;
		}
		

		public int getRowIndex() {
			return rowIndex;
		}



		public int compareTo(Object obj) {
			SortableEntry another = (SortableEntry) obj;
			if (another == null) {
				return -1;
			}

			int result = -1;
			for( int i=0; i < columnIndexesToSortBy.length; i++ ){
				int columnIndex = columnIndexesToSortBy[i];
				Comparator comparator = SimpleGrid.this.getComparator( columnIndex );
				if( comparator == null ){
					comparator = ComparatorUtil.NATURAL_COMPARATOR;
				}
				if( CollectionUtil.isInbounds( SimpleGrid.this.columnIds, columnIndex)){
					Object value1 = SimpleGrid.this.get(rowIndex, columnIndex);
					Object value2 = SimpleGrid.this.get(another.getRowIndex(), columnIndex);
					
					int compare = comparator.compare(value1, value2);
					if( compare != 0 ){
						return compare;
					}
				}
			}
			
			return 0;
		}

	}

}
