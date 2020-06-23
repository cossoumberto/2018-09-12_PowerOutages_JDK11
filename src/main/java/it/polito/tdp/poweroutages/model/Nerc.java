package it.polito.tdp.poweroutages.model;

public class Nerc {
	private int id;
	private String value;
	private boolean staDonando;
	private Integer bonus;
	/*private List<LocalDateTime> dateDonazioni;
	private List<Nerc> nercDonazioni;*/

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
		this.staDonando = false;
		this.bonus = 0;
		/*this.dateDonazioni = new ArrayList<>();
		this.nercDonazioni = new ArrayList<>();*/
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	public boolean staDonando() {
		return staDonando;
	}

	public void setStaDonando(boolean staDonando) {
		this.staDonando = staDonando;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	
/*
	public List<LocalDateTime> getDateDonazioni() {
		return dateDonazioni;
	}

	public List<Nerc> getNercDonazioni() {
		return nercDonazioni;
	}
	
	public void addDonazione(LocalDateTime data, Nerc nerc) {
		dateDonazioni.add(data);
		nercDonazioni.add(nerc);
	}*/
}
