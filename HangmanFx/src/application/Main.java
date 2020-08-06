package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.javafx.application.LauncherImpl;
import com.sun.javafx.scene.EnteredExitedHandler;
import com.sun.media.jfxmedia.events.PlayerEvent;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class Main extends Application {

	// Scenebuilder
	@FXML
	Text hangmanWord;
	@FXML
	ImageView hangmanImage;
	@FXML
	TextField letter;
	@FXML
	Text info;
	@FXML
	Label title;
	@FXML
	RadioButton easy;
	@FXML
	RadioButton medium;
	@FXML
	RadioButton hard;

	// G A M E

	private static final int COUNT_LIMIT = 10;

	private String wort;
	String rightletterString;

	char[] wortToArr;
	char[] displayWord;

	int counter = 1;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Ui.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("****HANGMAN****");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// New Game
	public void newGame() {
		try {
			wort = newWord();
			wortToArr = wort.toCharArray();
			displayWord = new char[wortToArr.length];
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		for (int i = 0; i < wort.length(); i++) {
			displayWord[i] = '-';
		}
		String textfieldString = new String(displayWord);
		hangmanWord.setText(textfieldString);
		counter = 1;
		hangmanImage.setImage(null);
		info.setText("");
	}

	// Check the letter from Textfields
	public boolean arrayMatch(char[] wortToArr, char[] displayWord) {
		boolean result = false;
		if(!letter.getText().isEmpty()) {
		for (int i = 0; i < wortToArr.length; i++) {
			if(wortToArr[i] == letter.getText().toLowerCase().charAt(0)
						|| wortToArr[i] == letter.getText().toUpperCase().charAt(0)) {
					displayWord[i] = wortToArr[i];
					result = true;
					}
				}

		}
		return result;
	}

	// Confirm\check typed Letter
	public void verifyTypedLetter() {
		info.setText("");

		if (arrayMatch(wortToArr, displayWord)) {
			rightletterString = new String(displayWord);
			hangmanWord.setText(rightletterString);
			info.setText("Richtig");
			
			AudioClip correctLetter = new AudioClip(
					this.getClass().getResource("/sounds/correct.mp3").toExternalForm());
			correctLetter.play();

		} else {
			hangmanImage.setImage(new Image("/images/" + counter + ".png"));
			info.setText("Falsch");
			
			if (counter < 15) {
				AudioClip wrongLetter = new AudioClip(
						this.getClass().getResource("/sounds/wrong.mp3").toExternalForm());
				wrongLetter.play();
			}
			counter++;
		}
		letter.clear();

		if (counter == 16) {
			hangmanWord.setText(wort);
			AudioClip wrongLetter = new AudioClip(this.getClass().getResource("/sounds/gameover.mp3").toExternalForm());
			wrongLetter.play();
		}
	}

	// Returns a Word from txt file
	public String newWord() throws FileNotFoundException {

		String line;
		ArrayList<String> temp = new ArrayList<String>();
		Random random = new Random();

		letter.setDisable(false);

		BufferedReader bf = new BufferedReader(new InputStreamReader(difficult()));
		try {
			while ((line = bf.readLine()) != null) {
				temp.add(line);
			}
		} catch (IOException e) {

			e.getMessage();
		}

		return temp.get(random.nextInt(temp.size())).toString();
	}

	// Returns the difficulty of Wordlist
	public InputStream difficult() {
		String easy = "/wordlists/wordeasy.txt";
		String medium = "/wordlists/wordmedium.txt";
		String hard = "/wordlists/wordhard.txt";

		InputStream difficult = null;

		if (this.easy.isSelected()) {
			difficult = getClass().getResourceAsStream(easy);

		}
		if (this.medium.isSelected()) {
			difficult = getClass().getResourceAsStream(medium);

		}
		if (this.hard.isSelected()) {
			difficult = getClass().getResourceAsStream(hard);

		} else {
			difficult = getClass().getResourceAsStream(easy);

		}
		return difficult;
	}
// Exit Game
	public void exit() {
		System.exit(0);
	}

	// Preloader
	@Override
	public void init() throws Exception {

		// Perform some heavy lifting (i.e. database start, check for application
		// updates, etc. )
		for (int i = 1; i <= COUNT_LIMIT; i++) {
			double progress = (double) i / 10;
			System.out.println("progress: " + progress);
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
			Thread.sleep(350);
		}

	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, HangmanPreload.class, args);
	}

}
