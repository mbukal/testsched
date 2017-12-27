package hr.unizg.fer.hmo.ts.optimization.ga.util;

public class ParentPair<T> {
	private T parent1;
	private T parent2;
	
	public ParentPair(T parent1, T parent2) {
		this.parent1 = parent1;
		this.parent2 = parent2;
	}
	
	public static <T> ParentPair<T> of(T parent1, T parent2) {
		return new ParentPair<>(parent1, parent2);
	}

	public T getParent1() {
		return parent1;
	}

	public T getParent2() {
		return parent2;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("parent1: ");
		sb.append(parent1);
		
		sb.append("\n");
		
		sb.append("parent2: ");
		sb.append(parent2);
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent1 == null) ? 0 : parent1.hashCode());
		result = prime * result + ((parent2 == null) ? 0 : parent2.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParentPair<T> other = (ParentPair<T>) obj;
		if (parent1 == null) {
			if (other.parent1 != null)
				return false;
		} else if (!parent1.equals(other.parent1))
			return false;
		if (parent2 == null) {
			if (other.parent2 != null)
				return false;
		} else if (!parent2.equals(other.parent2))
			return false;
		return true;
	}
	
	
	
}
