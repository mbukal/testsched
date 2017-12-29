package hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.impl;

import java.util.HashMap;
import java.util.Map;

import hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.TabuList;

public class SimpleTabuList<T> implements TabuList<T> {
	private Map<T, Integer> elementToTimeMap;
	
	public SimpleTabuList() {
		elementToTimeMap = new HashMap<>();
	}

	@Override
	public void add(T element, int duration) {
		if (duration > 0) {
			elementToTimeMap.put(element, duration);
		}
	}

	@Override
	public void update() {
		elementToTimeMap.entrySet().
					removeIf(entry -> entry.getValue().equals(0));
		elementToTimeMap.entrySet().
					forEach(entry -> elementToTimeMap.replace(entry.getKey(), entry.getValue() - 1));	
	}
	
	@Override
	public boolean contains(T element) {
		return elementToTimeMap.containsKey(element);
	}
	
	@Override
	public int size() {
		return elementToTimeMap.size();
	}
	
	@Override
	public int clear() {
		int size = size();
		elementToTimeMap.clear();
		return size;
	}

}
