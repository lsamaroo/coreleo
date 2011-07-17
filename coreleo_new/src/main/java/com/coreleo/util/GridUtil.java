package com.coreleo.util;

import java.util.ArrayList;
import java.util.List;

import com.coreleo.datastructure.Grid;

/**
 * @deprecated
 * 
 * Doesn't handle MultiGrids
 */
public class GridUtil {

	private GridUtil() {
		super();
	}

	public static final Grid fill( Grid grid, Object object ){
		for( int i=0; i < grid.getRowCount(); i++ ){
			for( int j=0; j < grid.getColumnCount(); j++ ){
				grid.put(i, j, object);
			}
		}		
		
		return grid;
	}
	
	
	
	public static final Object[] toArrayOfArrays( Grid grid ){
		Object[][] x = new Object[ grid.getRowCount() ][ grid.getColumnCount() ];
		for( int i=0; i < grid.getRowCount(); i++ ){
			for( int j=0; j < grid.getColumnCount(); j++ ){
				x[i][j] = grid.get(i, j);
			}
		}				
		return x;
	}
	
	
	public static final List toListOfLists( Grid grid ){
		List list = new ArrayList( grid.getRowCount() );
		
		for( int i=0; i < grid.getRowCount(); i++ ){
			List row = new ArrayList(grid.getColumnCount());
			for( int j=0; j < grid.getColumnCount(); j++ ){
				row.add( grid.get(i, j) );
			}
			list.add(row);
		}				
		return list;
	}
}
