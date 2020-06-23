package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercWeight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML 
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	model.createGraph();
    	cmbBoxNerc.getItems().addAll(model.getGraph().vertexSet());
    	txtResult.setText("GRAFO CREATO\n#VERTICI: " + model.getGraph().vertexSet().size() + " #ARCHI: " + model.getGraph().edgeSet().size());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	Integer K = null;
    	try {
    		K = Integer.parseInt(txtK.getText());
    	} catch (NumberFormatException e){
    		e.printStackTrace();
    		txtResult.setText("Inserimento non valido");
    	}
    	if(model.getGraph()==null) {
    		txtResult.setText("Crea il grafo");
    	} else {
    		Integer catastrofi = model.simula(K);
    		txtResult.setText("Si sono verificate " + catastrofi + " catastrofi\n");
    		for(Nerc n : model.getGraph().vertexSet())
    			txtResult.appendText(n.getValue() + " " + n.getBonus() + "\n");
    	}
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	if(model.getGraph()==null) {
    		txtResult.setText("Crea il grafo");
    	}
    	else {
	    	Nerc nerc = cmbBoxNerc.getValue();
	    	if(nerc==null) {
	    		txtResult.setText("Seleziona un nerc");
	    	} else {
	    		txtResult.clear();
	    		if(model.getViciniWeight(nerc).size()==0)
	    			txtResult.setText("Nerc non connesso");
	    		for(NercWeight nw : model.getViciniWeight(nerc))
	    			txtResult.appendText(nw.toString() + "\n");
	    	}
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
