package pt.ul.fc.di.css.javafxexample.model;

import java.io.File;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

	
	public void saveData(File file) {
		//idk
	}


	private final ObservableList<Jogador> jogadorList = FXCollections
			.observableArrayList(jogador -> new Observable[] { jogador.getNomeProperty(), jogador.getUsernameProperty(), 
				jogador.getPosicaoProperty(), jogador.getEquipasProperty() });



	public void setJogadorList(List<Jogador> jogadorList) {
		this.jogadorList.setAll(jogadorList);
	}

	public ObservableList<Jogador> getJogadorList() {
		return jogadorList;
	}

	private final ObservableList<Jogador> jogadorBackupList = FXCollections
			.observableArrayList(jogador -> new Observable[] { jogador.getNomeProperty(), jogador.getUsernameProperty(), 
				jogador.getPosicaoProperty(), jogador.getEquipasProperty() });

	public void setJogadorBackupList(List<Jogador> jogadorList) {
		this.jogadorBackupList.setAll(jogadorList);
	}

	public ObservableList<Jogador> getJogadorBackupList() {
		return jogadorBackupList;
	}


	private final ObservableList<Arbitro> arbitroList = FXCollections
			.observableArrayList(arbitro -> new Observable[] { arbitro.getNomeProperty(), arbitro.getUsernameProperty(),
				arbitro.getJogosProperty() });

	public void setArbitroList(List<Arbitro> arbitroList) {
		this.arbitroList.setAll(arbitroList);
	}

	public ObservableList<Arbitro> getArbitroList() {
		return arbitroList;
	}

	private final ObservableList<Arbitro> arbitroBackupList = FXCollections
			.observableArrayList(arbitro -> new Observable[] { arbitro.getNomeProperty(), arbitro.getUsernameProperty(),
				arbitro.getJogosProperty() });

	
	public void setArbitroBackupList(List<Arbitro> arbitroList) {
		this.arbitroBackupList.setAll(arbitroList);	
	}

	public ObservableList<Arbitro> getArbitroBackupList() {
		return arbitroBackupList;
	}


	private final ObservableList<Equipa> equipasList = FXCollections
			.observableArrayList(equipa -> new Observable[] { equipa.getNomeProperty(), equipa.getJogadoresProperty(), equipa.getJogosCasaProperty(),
				equipa.getJogosVisitanteProperty()});

	public void setEquipasList(List<Equipa> equipaList) {
		this.equipasList.setAll(equipaList);
	}

	public ObservableList<Equipa> getEquipasList() {
		return equipasList;
	}

	private final ObservableList<Equipa> equipasBackupList = FXCollections
			.observableArrayList(equipa -> new Observable[] { equipa.getNomeProperty(), equipa.getJogadoresProperty(), equipa.getJogosCasaProperty(),
				equipa.getJogosVisitanteProperty()});

	public void setEquipasBackupList(List<Equipa> equipaList) {
		this.equipasBackupList.setAll(equipaList);
	}

	public ObservableList<Equipa> getEquipasBackupList() {
		return equipasBackupList;
	}

	private final ObservableList<Local> localList = FXCollections
			.observableArrayList(local -> new Observable[] { local.getNomeProperty(), local.getMoradaProperty() });


	public void setLocalList(List<Local> localList) {
		this.localList.setAll(localList);
	}

	public ObservableList<Local> getLocalList() {
		return localList;
	}

	private final ObservableList<Plantel> plantelList = FXCollections
			.observableArrayList(plantel -> new Observable[] { plantel.getEquipaProperty(), plantel.getJogadoresProperty() });

	public void setPlantelList(List<Plantel> plantelList) {
		this.plantelList.setAll(plantelList);
	}

	public ObservableList<Plantel> getPlantelList() {
		return plantelList;
	}

	private final ObservableList<Campeonato> campeonatoList = FXCollections
			.observableArrayList(campeonato -> new Observable[] { campeonato.getNomeProperty(), campeonato.getEpocaProperty(),
				campeonato.getEquipasProperty(), campeonato.getJogosProperty(), campeonato.getEstatisticasProperty() });

	public void setCampeonatoList(List<Campeonato> campeonatoList) {
		this.campeonatoList.setAll(campeonatoList);
	}

	public ObservableList<Campeonato> getCampeonatoList() {
		return campeonatoList;
	}

	private final ObservableList<Jogo> jogoList = FXCollections
			.observableArrayList(jogo -> new Observable[] { jogo.getEquipaCasaProperty(), jogo.getEquipaVisitanteProperty(),
				jogo.getGolosCasaProperty(), jogo.getGolosVisitanteProperty(), jogo.getArbitroMainProperty(), jogo.getCampeonatoProperty() });


	public void setJogoList(List<Jogo> jogoList) {
		this.jogoList.setAll(jogoList);
	}

	public ObservableList<Jogo> getJogoList() {
		return jogoList;
	}




	private final ObjectProperty<Jogador> currentUserJogador = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Arbitro> currentUserArbitro = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Jogador> currentSelectedJogador = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Arbitro> currentSelectedArbitro = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Equipa> currentSelectedEquipa = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Local> currentSelectedLocal = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Plantel> currentSelectedPlantel = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Campeonato> currentSelectedCampeonato = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Jogo> currentSelectedJogo = new SimpleObjectProperty<>(null);


	public ObjectProperty<Arbitro> currentUserArbitroProperty() {
		return currentUserArbitro;
	}

	public final Arbitro getCurrentUserArbitro() {
		return currentUserArbitroProperty().get();
	}

	public final void setCurrentUserArbitro(Arbitro arbitro) {
		currentUserArbitroProperty().set(arbitro);
	}

	public ObjectProperty<Jogador> currentUserJogadorProperty() {
		return currentUserJogador;
	}

	public final Jogador getCurrentUserJogador() {
		return currentUserJogadorProperty().get();
	}

	public final void setCurrentUserJogador(Jogador jogador) {
		currentUserJogadorProperty().set(jogador);
	}

	public ObjectProperty<Jogador> currentSelectedJogadorProperty() {
		return currentSelectedJogador;
	}

	public final Jogador getCurrentSelectedJogador() {
		return currentSelectedJogadorProperty().get();
	}

	public final void setCurrentSelectedJogador(Jogador jogador) {
		currentSelectedJogadorProperty().set(jogador);
	}

	public ObjectProperty<Arbitro> currentSelectedArbitroProperty() {
		return currentSelectedArbitro;
	}

	public final Arbitro getCurrentSelectedArbitro() {
		return currentSelectedArbitroProperty().get();
	}

	public final void setCurrentSelectedArbitro(Arbitro arbitro) {
		currentSelectedArbitroProperty().set(arbitro);
	}

	public ObjectProperty<Equipa> currentSelectedEquipaProperty() {
		return currentSelectedEquipa;
	}

	public final Equipa getCurrentSelectedEquipa() {
		return currentSelectedEquipaProperty().get();
	}

	public final void setCurrentSelectedEquipa(Equipa equipa) {
		currentSelectedEquipaProperty().set(equipa);
	}

	public ObjectProperty<Local> currentSelectedLocalProperty() {
		return currentSelectedLocal;
	}
	public final Local getCurrentSelectedLocal() {
		return currentSelectedLocalProperty().get();
	}
	public final void setCurrentSelectedLocal(Local local) {
		currentSelectedLocalProperty().set(local);
	}

	public ObjectProperty<Plantel> currentSelectedPlantelProperty() {
		return currentSelectedPlantel;
	}
	public final Plantel getCurrentSelectedPlantel() {
		return currentSelectedPlantelProperty().get();
	}

	public final void setCurrentSelectedPlantel(Plantel plantel) {
		currentSelectedPlantelProperty().set(plantel);
	}

	public ObjectProperty<Campeonato> currentSelectedCampeonatoProperty() {
		return currentSelectedCampeonato;
	}

	public final Campeonato getCurrentSelectedCampeonato() {
		return currentSelectedCampeonatoProperty().get();
	}

	public final void setCurrentSelectedCampeonato(Campeonato campeonato) {
		currentSelectedCampeonatoProperty().set(campeonato);
	}

	public ObjectProperty<Jogo> currentSelectedJogoProperty() {
		return currentSelectedJogo;
	}

	public final Jogo getCurrentSelectedJogo() {
		return currentSelectedJogoProperty().get();
	}

	public final void setCurrentSelectedJogo(Jogo jogo) {
		currentSelectedJogoProperty().set(jogo);
	}

	

	

	


	


}