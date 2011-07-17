/**
 * 
 */
package com.coreleo.util;

import java.util.*;

import com.coreleo.util.closure.Block;

/**
 * @author Leon Samaroo
 * 
 */
public final class CollectionUtil {

    private CollectionUtil() {
        super();
    }
    
    
    
    /**
     * Splits a list into sublists of length <code>subListLength</code>
     * @ return a List containing the sublists.  The sublists are views of the original list.
     */
    public static <T> List<List<T>> split(List<T> list, final int subListLength) {
    	return split(list, subListLength, true );
    }

    
    /**
     * Splits a list into sublists of length <code>subListLength</code>
     * 
     * @param subListAsView - true if the sublists should remain as views of the original list, false to create copies.
     * @ return a List containing the sublists. 
     */
    public static <T> List<List<T>> split(List<T> list, final int subListLength, boolean subListAsView) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += subListLength) {
        	if( subListAsView ){
        		parts.add( list.subList(i, Math.min(N, i + subListLength)) );
        	}
        	else{
        		parts.add( new ArrayList<T>(list.subList(i, Math.min(N, i + subListLength))) );
        	}
        }
        return parts;
    }

    
    
    public static final boolean contains( List x, Object value){
    	if( x != null ){
    		return x.contains(value);
    	}
    	
    	return false;
    }

    
    
    public static final boolean remove( List x, Object value){
    	if( x != null ){
    		return x.remove(value);
    	}
    	
    	return false;
    }

    
    public static final void clear( List x){
    	if( x != null ){
    		x.clear();
    	}
    }
    
    
    public static final void forEach( List x, Block b ){
    	for( int i = 0; i < x.size(); i++ ){
    		b.invoke(x.get(i));
    	}
    }
    
    
    public static final void forEach( Collection x, Block b ){
    	for( Iterator i = x.iterator(); i.hasNext(); ){
    		b.invoke( i.next() );
    	}
    }
    
    
    
    
	public static final Object get( List x, int index ){
		if (x == null) {
			return null;
		}

		if (index < 0 || index >= x.size()) {
			return null;
		}

		return x.get(index);		
	}
	

    public static final int size( Collection x ){
    	if( x == null ){
    		return 0;
    	}
    	
    	return x.size();
    }
    
    
    
    public static final boolean isInbounds( Collection x, Object ix ){
    	if( x == null ){
    		return false;
    	}
    	
    	int index = NumberUtil.toInteger(ix,-1);
    	
    	
    	if( !(index < 0 || index >= x.size() ) ){
    		return true;
    	}
    	
    	return false;
    }
    
    
    public static final boolean isInbounds( Collection x, int index ){
    	if( x == null ){
    		return false;
    	}
    	
    	if( !(index < 0 || index >= x.size() ) ){
    		return true;
    	}
    	
    	return false;
    }



    public static final boolean isEmpty(Object x) {
        if (x == null) {
            return true;
        }

        if (x instanceof Collection) {
            return ((Collection) x).size() <= 0;
        }

        return true;
    }
    
    
    public static final boolean isNotEmpty(Object x) {
        return ! isEmpty( x );
    }



    public static final List commaDelimitedStringToList(String x) {
        return delimitedStringToList(x, ",");
    }


    public static final List delimitedStringToList(String x, String delimiter) {
        if (x == null) {
            return null;
        }

        if (delimiter == null) {
            List list = new ArrayList();
            list.add(x);
            return list;
        }

        List list = new ArrayList();
        // TODO replace tokenizer with regex or split
        for (StringTokenizer i = new StringTokenizer(x, delimiter); i.hasMoreTokens();) {
            String nextToken = i.nextToken();
            if (StringUtil.isNotEmpty(nextToken)) {
                list.add(StringUtil.trim(nextToken));
            }
        }
        return list;
    }


    public static final String toCommaDelimitedString(Collection x) {
        return toDelimitedString(x, ",");
    }


    public static final String toDelimitedString(Collection x, String delimiter) {
        if (x == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        int count = 0;

        for (Iterator i = x.iterator(); i.hasNext();) {
            sb.append(i.next());
            if (count < x.size() - 1) {
                sb.append(delimiter);
            }
            count++;
        }

        return sb.toString();
    }


    public static final String toCommaDelimitedString(List x) {
        return toDelimitedString(x, ",");
    }


    public static final String toDelimitedString(List x, String delimiter) {
        if (x == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
       
        for ( int i = 0; i < x.size(); i++ ) {
            sb.append( x.get(i) );
            if (i < x.size() - 1) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }
    
    
    public static final String toDelimitedString(List x, String delimiter, Block alter) {
        if (x == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
       
        for ( int i = 0; i < x.size(); i++ ) {
            sb.append( alter.invoke(x.get(i)) );
            if (i < x.size() - 1) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    
    public static final boolean compare(Collection x1, Collection x2) {
        boolean isEqual = false;
        ArrayList list1 = null;
        ArrayList list2 = null;
        // converting the collections into ArrayList inorder to apply sort(),
        // binary search and
        // since it implemnts randomAccess interface ,binary search takes
        // O(log(n))time
        list1 = (x1 == null) ? new ArrayList() : new ArrayList(x1);
        list2 = (x2 == null) ? new ArrayList() : new ArrayList(x2);
        Collections.sort(list2);// list2 has to be sorted before applying
        // binarySearch()
        if (list1.size() == list2.size()) {
            if (list1.size() == 0)
                isEqual = true;
            else {
                for (int i = 0; i < list1.size(); i++) {
                    if (!(isEqual = Collections.binarySearch(list2, list1.get(i)) >= 0))
                        break;
                }
            }
        }
        return isEqual;
    }


    public static final String[] toStringArray(Collection x) {
        String[] strings = new String[x.size()];
        int count = 0;

        for (Iterator i = x.iterator(); i.hasNext();) {
            strings[count++] = String.valueOf(i.next());
        }

        return strings;
    }
    
    
    public static final String[] toStringArray(List x) {
        String[] strings = new String[x.size()];
        
        for (int i = 0; i < x.size(); i++ ) {
            strings[i] = String.valueOf(x.get(i));
        }

        return strings;
    }


    public static final List toList(Collection c) {
        if (c == null) {
            return null;
        }

        if (c instanceof List) {
            return (List) c;
        }

        return new ArrayList(c);
    }


    public static final List toList(Object[] x) {
        if (x == null) {
            return null;
        }

        return Arrays.asList(x);
    }


    public static final List toIntegerObjectList(Object[] x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        List list = new ArrayList();

        for (int i = 0; i < x.length; i++) {
            list.add(NumberUtil.toIntegerObject(x[i]));
        }

        return list;
    }


    public static final List delimitedStringToIntegerObjectList(String x, String delimiter) {
        if (x == null || delimiter == null) {
            return null;
        }

        List list = new ArrayList();
        // TODO replace tokenizer with regex or split
        Enumeration enumerator = new StringTokenizer(x, delimiter);
        while (enumerator.hasMoreElements()) {
            list.add(NumberUtil.toIntegerObject(enumerator.nextElement()));
        }

        return list;
    }


    public static final List commaDelimitedStringToIntegerObjectList(String x) {
        return delimitedStringToIntegerObjectList(x, ",");
    }


    public static final List merge(Collection x1, Collection x2) {
        if (x1 == null && x2 == null) {
            return null;
        }

        ArrayList list = new ArrayList();

        if (x1 != null) {
            list.addAll(x1);
        }

        if (x2 != null) {
            list.addAll(x2);
        }

        return list;
    }

    
    public static final Collection sort( Collection x){
    	if( x == null ){
    		return null;
    	}

    	if( x instanceof List ){
    		Collections.sort((List) x);
    		return x;
    	}
    	else {
    		List list = new ArrayList(x);
    		Collections.sort( list  );
    		return list;
    	}
    }
    
    
    
    public static final Collection sort( Collection x, Comparator c){
    	if( x == null ){
    		return null;
    	}
    	
    	if( c == null ){
    		return x;
    	}
    	
    	if( x instanceof List ){
    		Collections.sort((List) x, c);
    		return x;
    	}
    	else {
    		List list = new ArrayList(x);
    		Collections.sort( list, c );
    		return list;
    	}
    }
    
    
    public static final List sort( List list){
    	if( list == null ){
    		return null;
    	}

    	Collections.sort(list);
    	return list;
    }
    
    
    public static final List sort( List list, Comparator c){
    	if( list == null ){
    		return null;
    	}
    	
    	if( c == null ){
    		return list;
    	}
    	
    	Collections.sort(list, c);
    	return list;
    }
}
