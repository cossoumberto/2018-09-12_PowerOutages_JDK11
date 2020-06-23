package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {

	public enum EventType{
		START, STOP
	}
	
	private LocalDateTime date;
	private EventType type;
	private PowerOutage po;
	//private Nerc donatore; 
	
	public Event(LocalDateTime date, EventType type, PowerOutage po/*, Nerc donatore*/) {
		this.date = date;
		this.type = type;
		this.po = po;
		//this.donatore = donatore;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public EventType getType() {
		return type;
	}

	public PowerOutage getPo() {
		return po;
	}
/*
	public Nerc getDonatore() {
		return donatore;
	}

	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}*/

	@Override
	public String toString() {
		return "Event [date=" + date + ", type=" + type + ", nerc=" + po.getNerc() + /*", donatore=" + donatore.toString() +*/ "]";
	}

	@Override
	public int compareTo(Event o) {
		return date.compareTo(o.date);
	}
	
	
}
