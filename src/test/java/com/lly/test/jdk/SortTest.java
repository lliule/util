package com.lly.test.jdk;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leliu
 */
public class SortTest {


	@Test
	public void sort(){
		int rank = 2;
		ConcurrentHashMap<String, Float> map = new ConcurrentHashMap<>();
		map.put("a",1.1f);
		map.put("c",2.1f);
		map.put("b",3.0f);
		ArrayList<Map.Entry<String, Float>> list = new ArrayList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {


			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				System.out.println(o1.getValue());
				return rank == 1? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue());
			}
		});
		System.out.println(JSON.toJSONString(list));
		map.clear();
		LinkedHashMap<String, Float> linkedHashMap = new LinkedHashMap<>();
		list.forEach((e)->{
			linkedHashMap.put(e.getKey(), e.getValue());
		});

		System.out.println(JSON.toJSONString(linkedHashMap));

		ArrayList<Float> arrayList = new ArrayList<>(map.values());
		Collections.sort(arrayList, new Comparator<Float>() {
			@Override
			public int compare(Float o1, Float o2) {
				return rank == 1 ? o1.compareTo(o2) : o2.compareTo(o1);
			}
		});
		System.out.println(arrayList);
	}


	@Test
	public void sortFloat(){
		Float a = 1.1f;
		Float b = 2.1f;
		Float c= 3.0f;
		System.out.println(a.compareTo(b));
	}
}


