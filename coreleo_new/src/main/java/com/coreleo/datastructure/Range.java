package com.coreleo.datastructure;


@SuppressWarnings("rawtypes")
public class Range< T extends Comparable> implements Comparable {
	private final T start;
	private final T end;
	
	
	public Range(T start, T end) {
		super();
		this.start = start;
		this.end = end;
	}


	public T getStart() {
		return start;
	}


	public T getEnd() {
		return end;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean contains( T value ){
		return
			start.compareTo( value ) <= 0 &&
			end.compareTo( value ) >= 0;
	}



	@Override
	public boolean equals(Object obj) {
	
		return compareTo(obj) == 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Object obj) {
		Range other = (Range) obj;
		int compare = start.compareTo( other.getStart() );
		if( compare != 0 ){
			return compare;
		}
		else {
			return end.compareTo( other.getEnd() );
		}
	}
	

}
