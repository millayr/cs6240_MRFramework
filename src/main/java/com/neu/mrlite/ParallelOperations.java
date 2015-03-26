package com.neu.mrlite;

import java.util.List;
/**
 * The contract for API users to access the public API's
 * It contains the detailed structure of an operation execution.
 * Each operation returns an Assortment which can perform more
 * operations to transform the data and arrive at the final result.
 * 
 * Eg. Assortment A = readInputFrom(io);
 * Assortment B = A.map(callback);
 * Assortment C = B.reduce(callback);
 * 
 * @author nikit
 *
 * @param <T>
 */
public interface ParallelOperations<T> {
	/**
	 * Map operation that executes a callback process to perform a user defined
	 * map on the scoped collection of data. The resultant data is emitted to
	 * the callback method emit and it will be collected here for further
	 * processing.
	 * 
	 * @param callback POCallback
	 * @param ret Class 'Q'
	 * @return Assortment&ltQ&gt;
	 */
	public <Q> Assortment<Q> map(POCallback<T> callback, Class<? extends Q> ret);
	/**
	 * Combine operation to accumulate the results from map and relaying
	 * it back to the reducer. It follows the same line of Hadoop implementation
	 * which makes the combiner take the same input as reducer and produce the
	 * output like a map.
	 * 
	 * @param callback POCallback
	 * @param ret Class 'Q'
	 * @return Assortment&ltQ&gt;
	 */
	public <Q> Assortment<Q> combine(POCallback<T> callback, Class<? extends Q> ret);
	/**
	 * Reduce operation to agglomerate the final results form the intermediate map
	 * and combine operations. The contract to reducer is slightly different from map
	 * since it accepts the Assortment table which holds the keys and their respective
	 * list of values to be reduced. Output will be emitted in the similar fashion as
	 * map but in this case it gets written to the FS and also in another Assortment of 
	 * required Type that needs to be returned.
	 * 
	 * @param callback POCallback
	 * @param ret Class 'Q'
	 * @return Assortment&ltQ&gt;
	 */
	public <P,Q,R> Assortment<Q> reduce(POCallback<Pair<P, List<R>>> callback, Class<? extends Q> ret);
}
