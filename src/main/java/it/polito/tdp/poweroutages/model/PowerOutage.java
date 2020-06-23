package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	private Integer id;
	private Nerc nerc;
	private LocalDateTime start;
	private LocalDateTime stop;
	private Nerc donatore;
	
	public PowerOutage(Integer id, Nerc nerc, LocalDateTime start, LocalDateTime stop) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.start = start;
		this.stop = stop;
		this.donatore = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public void setStop(LocalDateTime stop) {
		this.stop = stop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage [id=" + id + ", nercId=" + nerc.getId()+ "]";
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}

}
