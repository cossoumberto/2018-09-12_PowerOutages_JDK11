package it.polito.tdp.poweroutages.model;

public class NercWeight implements Comparable<NercWeight>{

	private Nerc nerc;
	private Integer weight;
	
	public NercWeight(Nerc nerc, Integer weight) {
		super();
		this.nerc = nerc;
		this.weight = weight;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return nerc + " " + weight;
	}

	@Override
	public int compareTo(NercWeight o) {
		return o.weight-this.weight;
	}
	
	
}
