package com.coreleo.datastructure.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.coreleo.SimpleException;
import com.coreleo.util.CollectionUtil;

/**
 * 
 * @author Leon Samaroo
 * 
 *         Defines a Grid that holds a list of values against each row-column combination. Getting a value from this Grid will
 *         return a List, holding all the values put to that row-column.
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MultiGrid extends SimpleGrid
{

	private static final long serialVersionUID = 1L;

	public MultiGrid()
	{
		super();
	}

	public Object getFirstValue(int rowIndex, int columnIndex)
	{
		return CollectionUtil.get((List) super.get(rowIndex, columnIndex), 0);
	}

	public Object getFirstValue(Object rowId, Object columnId)
	{
		return CollectionUtil.get((List) super.get(rowId, columnId), 0);
	}

	/**
	 * 
	 * Clears the values at the given row and column
	 * 
	 * @return a reference to this Grid
	 */
	public Grid clearValues(Object rowId, Object columnId)
	{
		CollectionUtil.clear((List) get(rowId, columnId));
		return this;
	}

	/**
	 * 
	 * Clears the values at the given row and column index
	 * 
	 * @return a reference to this Grid
	 */
	public Grid clearValues(int rowIndex, int columnIndex)
	{
		CollectionUtil.clear((List) get(rowIndex, columnIndex));
		return this;
	}

	public boolean remove(int rowIndex, int columnIndex, Object item)
	{
		return CollectionUtil.remove((List) get(rowIndex, columnIndex), item);
	}

	public boolean remove(Object rowId, Object columnId, Object item)
	{
		return CollectionUtil.remove((List) get(rowId, columnId), item);
	}

	public boolean containsValue(Object rowId, Object columnId, Object item)
	{
		return CollectionUtil.contains((List) get(rowId, columnId), item);
	}

	public boolean containsValue(int rowIndex, int columnIndex, Object item)
	{
		return CollectionUtil.contains((List) get(rowIndex, columnIndex), item);
	}

	public int getValuesCount(int rowIndex, int columnIndex)
	{
		return CollectionUtil.size((List) get(rowIndex, columnIndex));
	}

	public int getValuesCount(Object rowId, Object columnId)
	{
		return CollectionUtil.size((List) get(rowId, columnId));
	}

	// -------------------------------------------------------------------------------------------
	// Override of class SimpleGrid
	// -------------------------------------------------------------------------------------------

	/**
	 * @return a java.util.List containing the values located at the specified row and column index, or null if the Grid contains
	 *         no value at this row-column index combination.
	 * 
	 */
	@Override
	public Object get(int rowIndex, int columnIndex)
	{
		return super.get(rowIndex, columnIndex);
	}

	/**
	 * @return a java.util.List containing the values located at the specified row and column, or null if the Grid contains no
	 *         value at this row-column combination.
	 * 
	 */
	@Override
	public Object get(Object rowId, Object columnId)
	{
		return super.get(rowId, columnId);
	}

	@Override
	public boolean put(int rowIndex, int columnIndex, Object value)
	{
		List list = (List) get(rowIndex, columnIndex);
		if (list == null)
		{
			list = new ArrayList();
		}

		list.add(value);

		return super.put(rowIndex, columnIndex, list);
	}

	@Override
	public boolean containsValue(Object value)
	{
		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			if (list.contains(value))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int getValuesCount()
	{
		int count = 0;

		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			count = count + list.size();
		}

		return count;
	}

	@Override
	public Collection values()
	{
		final List values = new ArrayList();

		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			values.addAll(list);
		}

		return values;
	}

	@Override
	public void sort(int columnIndex)
	{
		throw new SimpleException("Not Supported");
	}

	@Override
	public void sort(int[] columnIndexes)
	{
		throw new SimpleException("Not Supported");
	}

}
