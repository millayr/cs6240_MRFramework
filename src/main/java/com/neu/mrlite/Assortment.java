package com.neu.mrlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Generic Collection type, that also holds the contract of
 * operations that it can execute in parallel. ParallelOperations
 * needs a callback to execute the task. This callback is provided
 * by POCallback abstract class.
 * map, reduce and combine methods are specific to their mode and
 * purpose of operation.
 * It holds an IOHandle to read inputs and generate output.
 * The collection is an ArrayList of type 'T' internally.
 * Every operation is a deferred execution to run in parallel once
 * the job is ready to execute.
 * 
 * It implements Collection&lt;ANY&gt; interface just to adhere to standards.
 * here ANY type is a strict set of DataTypes including [java autoboxing classes, Pair&lt;ANY,ANY&gt;]
 * @author nikit
 *
 * @param <T>
 */
public class Assortment<T> implements Collection<T>, ParallelOperations<T>{
	private List<T> list;
	private static IOHandle IOHANDLE;
	
	public Assortment() {
		list = new ArrayList<T>();
	} 
	/**
	 * Map Operation
	 * 
	 */
	public <Q> Assortment<Q> map(POCallback<T> callback,
			Class<? extends Q> ret) {
		// TODO Auto-generated method stub
		Assortment<Q> res = new Assortment<Q>();
		for(T item: this){
			callback.process(item);
			if(ret.equals(Pair.class)) {
				res.add(ret.cast(new Pair<Object, Object>(callback.getKey(), 
						callback.getValue())));
			} else {
				res.add(ret.cast(callback.getValue()));
			}
		}
		return res;
	}
	
	/**
	 * Combine Operation
	 */
	public <Q> Assortment<Q> combine(POCallback<T> callback,
			Class<? extends Q> ret) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Reduce Operation 
	 */
	public <P,Q,R> Assortment<Q> reduce(POCallback<Pair<P, List<R>>> callback,
			Class<? extends Q> ret) {
		// TODO Auto-generated method stub
		Assortment<Q> res = new Assortment<Q>();
		Map<P, List<R>> m = new HashMap<P, List<R>>();
		for(T item: this){
			if(item instanceof Pair) {
				Pair i = (Pair) item;
				List<R> ls = m.get(i.key);
				if(ls == null){
					ls = new ArrayList<R>();
					m.put((P)i.key, ls);
				}
				ls.add((R) i.value);
			}
		}
	
		for(P key: m.keySet()) {
			callback.process(new Pair<P, List<R>>(key, m.get(key)));
			if(ret.equals(Pair.class)) {
				 IOHANDLE.write(callback.getKey()+"\t"+callback.getValue());
				 
				 res.add(ret.cast(new Pair<Object, Object>(callback.getKey(), 
						callback.getValue())));
			} else {
				IOHANDLE.write(callback.getValue());
				res.add(ret.cast(callback.getValue()));
			}
		}
		return res;
	}
	
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(T e) {
		// TODO Auto-generated method stub
		return list.add(e);
	}

	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * returns the class type of the given class
	 * to create a resultant Assortment DataSet.
	 * @param responseType
	 * @return
	 */
	public static <Q> Class<? extends Q> dataSet(
			Class<? extends Q> responseType) {
		return responseType;
	}
	
	/**
	 * returns the Pair&lt;K, V&gt; type to create a
	 * resultant Assortment DataTable.
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <P,Q> Class<? extends Pair<P, Q>> dataTable(
			Class<? extends P> key,
			Class<? extends Q> value) {
		return (Class<? extends Pair<P, Q>>) Pair.class;
	}
	
	/**
	 * Reads the input file and performs the split (future scope).
	 * It collects the String Assortment and returns it to caller for
	 * more operations.
	 * @param io
	 * @return Assortment&lt;String&gt;
	 */
	public static Assortment<String> readInputFrom(IOHandle io) {
		String line = "";
		Assortment<String> newAssort = new Assortment<String>();
		IOHANDLE = io;
		try {
			while((line = io.readLine()) != null) {
				newAssort.add(line);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return newAssort;
	}
	
	public String toString() {
		return list.toString();
	}
	
	final static public void close() {
		IOHANDLE.close();
	}
}
