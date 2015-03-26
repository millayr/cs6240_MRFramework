package com.neu.mrlite.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.neu.mrlite.*;

import static com.neu.mrlite.Assortment.*;

public class TestInterface {
	public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {
		
		IOHandle io = new IOHandle(args[0], args[1]);
		
		Assortment<String> col1 = readInputFrom(io);
		
		System.out.println("Reader:\t"+col1);
		Assortment<Pair<String, Integer>> col2 = col1.map(
			new POCallback<String>() {
				public void process(String line) {
					String[] parts = line.split("\\t");
					emit(parts[0], Integer.parseInt(parts[1]));
				}
			}, 
			dataTable(String.class, Integer.class));
		
		System.out.println("Mapper:\t"+col2);
		
		Assortment<Pair<String, Integer>> col3 = col2.reduce(
				new POCallback<Pair<String, List<Integer>>>() {
					public void process(Pair<String, List<Integer>> data) {
						int sum = 0;
						for(Integer x: data.value) {
							sum += x;
						}
						
						emit(data.key, sum);
					}
				}, 
				dataTable(String.class, Integer.class));
		
		close();
		
		System.out.println("Reducer:"+col3);
	}
}
