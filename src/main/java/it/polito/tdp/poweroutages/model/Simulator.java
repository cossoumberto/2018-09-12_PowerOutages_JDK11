package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.poweroutages.model.Event.EventType;

public class Simulator {

	//PARAMETRI DI SIMULAZIONE
	private Model model;
	private List<PowerOutage> listPO;
	private Integer K; 
	private Graph<Nerc, DefaultWeightedEdge> graph;
	
	//OUTPUT DA CALCOLARE
	private Integer catastrofi;
	
	//STATO DEL SISTEMA
	private List<PowerOutage> donazioni;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	public void init(Model model, Integer K) {
		this.model = model;
		this.K = K;
		catastrofi=0;
		this.graph = model.getGraph();
		listPO = model.listAllPO();
		donazioni = new ArrayList<>();
		queue = new PriorityQueue<>();
		for(PowerOutage po : listPO) {
			Event e1 = new Event(po.getStart(), EventType.START, po);
			Event e2 = new Event(po.getStop(), EventType.STOP, po);
			queue.add(e1);
			queue.add(e2);
		}
	}
	
	public void simula() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		
		case START:
			Nerc guasto = e.getPo().getNerc();
			Nerc donatore = null;
			List<Nerc> possibiliDonatori = new ArrayList<>();
			if(Graphs.neighborListOf(graph, guasto).size()>0 && donazioni.size()>0)
				for(PowerOutage po : donazioni) 
					if(guasto.equals(po.getDonatore()) && e.getDate().minus(K, ChronoUnit.MONTHS).isBefore(po.getStop()) 
							&& po.getNerc().staDonando()==false) 
						possibiliDonatori.add(po.getNerc());
			if(Graphs.neighborListOf(graph, guasto).size()>0 && possibiliDonatori.size()==0)
				for(Nerc n : Graphs.neighborListOf(graph, guasto))
					if(n.staDonando()==false)
						possibiliDonatori.add(n);
			if(possibiliDonatori.size()>0)
				donatore = scegliDonatorePerPeso(guasto, possibiliDonatori);
			if(donatore!=null) {
				donatore.setStaDonando(true);
				e.getPo().setDonatore(donatore);
				donazioni.add(e.getPo());
			} else {
				catastrofi++;
			}
			break;
		
		case STOP:
			if(e.getPo().getDonatore()!=null) {
				e.getPo().getDonatore().setStaDonando(false);
				Integer bonus = e.getPo().getDonatore().getBonus() 
						+ (int) e.getPo().getStart().until(e.getPo().getStop(), ChronoUnit.DAYS);
				e.getPo().getDonatore().setBonus(bonus);
			}
			break;
		}
		System.out.println(donazioni);
		System.out.println(catastrofi);
	}

	private Nerc scegliDonatorePerPeso(Nerc guasto, List<Nerc> possibiliDonatori) {
		Nerc donatore = null;
		Integer pesoMinore = -1;
		for(Nerc n : possibiliDonatori)
			if(graph.getEdgeWeight(graph.getEdge(guasto, n))<pesoMinore || pesoMinore==-1) {
				donatore = n;
				pesoMinore = (int)graph.getEdgeWeight(graph.getEdge(guasto, n));
			}
		return donatore;
	}
	
	public Integer getCatastrofi() {
		return catastrofi;
	}
}
