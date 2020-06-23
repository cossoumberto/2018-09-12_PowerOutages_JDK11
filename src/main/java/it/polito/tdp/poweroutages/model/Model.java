package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {

	private PowerOutagesDAO dao;
	private Map<Integer, Nerc> idMapNerc;
	private Graph <Nerc, DefaultWeightedEdge> graph;
	private Simulator s;
	
	public Model () {
		dao = new PowerOutagesDAO();
		idMapNerc = new HashMap<>();
		graph = null;
		s = null;
	}
	
	public void createGraph() {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllNercs(idMapNerc);
		Graphs.addAllVertices(graph, idMapNerc.values());
		for(Coppia c : dao.getCoppie(idMapNerc))
			Graphs.addEdge(graph, c.getN1(), c.getN2(), c.getWeight());
	}
	
	public Graph <Nerc, DefaultWeightedEdge> getGraph(){
		return graph;
	}
	
	public List<NercWeight> getViciniWeight(Nerc nerc){
		List<NercWeight> list = new ArrayList<>();
		for(DefaultWeightedEdge e : graph.edgesOf(nerc))
			list.add(new NercWeight(Graphs.getOppositeVertex(graph, e, nerc),(int)graph.getEdgeWeight(e)));
		Collections.sort(list);
		return list;
	}
	
	public List<PowerOutage> listAllPO(){
		return dao.listAllPO(idMapNerc);
	}
	
	public Integer simula(Integer k) {
		s = new Simulator();
		for(Nerc n : graph.vertexSet())
			n.setBonus(0);
		s.init(this, k);
		s.simula();
		return s.getCatastrofi();
	}
}
