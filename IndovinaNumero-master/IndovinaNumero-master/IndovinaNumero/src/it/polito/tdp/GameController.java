package it.polito.tdp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class GameController {

	private int margine = 10;

	private int numeroInserito;
	private int numeroSegreto;
	private int difficolta;
	private int totTentativi;
	private int numeroTentativo;
	private boolean inGame = false;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Integer> cmbDifficolta;

	@FXML
	private Button btnGioca;

	@FXML
	private TextField txtNumeroInserito;

	@FXML
	private Button btnProva;

	@FXML
	private Label txtResult;

	@FXML
	private Label txtInfo;

	@FXML
	private ProgressBar pgrBar;

	@FXML
	void doGioca(ActionEvent event) {

		if (inGame) {

			inGame = false;
			btnGioca.setText("Gioca!");

			// Rendi non visibile l'interfaccia
			makeGUIVisible(false);
			cmbDifficolta.setDisable(false);

		} else {
			inGame = true;
			btnGioca.setText("Abbandona!");

			// Rendi visibile l'interfaccia
			makeGUIVisible(true);
			cmbDifficolta.setDisable(true);
			txtNumeroInserito.setDisable(false);
			btnProva.setDisable(false);
			txtNumeroInserito.setText("");
			txtResult.setText("Inserisci numero");

		}

		if (cmbDifficolta.getValue() == null) {
			return;
		}

		difficolta = cmbDifficolta.getValue();
		numeroSegreto = (int) (Math.random() * difficolta) + 1;
		totTentativi = (int) (Math.log(difficolta) / Math.log(2.0)) + (int) (difficolta * 0.1);

		numeroTentativo = 0;
		txtInfo.setText(String.format("Tentativo: %d/%d", numeroTentativo, totTentativi));
		pgrBar.setProgress(0.0);
		// pgrBar.setProgress(numeroTentativo/totTentativi);
	}

	private void makeGUIVisible(boolean visible) {
		txtNumeroInserito.setVisible(visible);
		btnProva.setVisible(visible);
		txtResult.setVisible(visible);
		txtInfo.setVisible(visible);
		pgrBar.setVisible(visible);
	}

	@FXML
	void doProva(ActionEvent event) {

		try {
			numeroInserito = Integer.parseInt(txtNumeroInserito.getText());
		} catch (NumberFormatException e) {
			txtResult.setText("Inserisci un numero");
			return;
		}

		numeroTentativo++;
		// String a = "Tentativo: " + numeroTentativo + "/" + totTentativi;
		txtInfo.setText(String.format("Tentativo: %d/%d", numeroTentativo, totTentativi));
		pgrBar.setProgress((double) numeroTentativo / totTentativi);

		if (numeroTentativo > totTentativi) {
			// Ho perso
			txtResult.setText("Hai perso! Era " + numeroSegreto);
			txtNumeroInserito.setDisable(true);
			btnProva.setDisable(true);
			return;
		}
		if (numeroInserito == numeroSegreto) {
			// Ho vinto
			txtResult.setText("Hai vinto!");
			txtNumeroInserito.setDisable(true);
			btnProva.setDisable(true);
			return;
		}
		if (numeroInserito < numeroSegreto) {
			// Troppo piccolo;
			txtResult.setText("Troppo piccolo");
			return;
		}
		if (numeroInserito > numeroSegreto) {
			// Troppo grande;
			txtResult.setText("Troppo grande");
			return;
		}
	}

	@FXML
	void initialize() {
		assert cmbDifficolta != null : "fx:id=\"cmbDifficolta\" was not injected: check your FXML file 'Game.fxml'.";
		assert btnGioca != null : "fx:id=\"btnGioca\" was not injected: check your FXML file 'Game.fxml'.";
		assert txtNumeroInserito != null : "fx:id=\"txtNumeroInserito\" was not injected: check your FXML file 'Game.fxml'.";
		assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Game.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Game.fxml'.";
		assert txtInfo != null : "fx:id=\"txtInfo\" was not injected: check your FXML file 'Game.fxml'.";
		assert pgrBar != null : "fx:id=\"pgrBar\" was not injected: check your FXML file 'Game.fxml'.";

		cmbDifficolta.getItems().addAll(10, 100, 1000);
		if (cmbDifficolta.getItems().size() > 0)
			cmbDifficolta.setValue(cmbDifficolta.getItems().get(0));
	}
}
