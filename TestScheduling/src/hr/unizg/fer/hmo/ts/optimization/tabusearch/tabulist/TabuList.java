package hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist;

public interface TabuList<T> {
	public void add(T element, int duration);
	public void update();
	public boolean contains(T element);
	public int size();
	public int clear();
}
