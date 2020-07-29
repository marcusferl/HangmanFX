package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.javafx.application.LauncherImpl;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	// Scenebuilder
	@FXML
	Text text;
	@FXML
	ImageView hangmanImage;
	@FXML
	TextField textField;
	@FXML
	Text info;
	@FXML
	Label zuErratendesWort;
	@FXML
    private Label progress;
    
    public static Label label;
    
    
    @FXML
    private ProgressBar progressBar;
    
    public static ProgressBar statProgressBar;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
    }
    
    public void initialize(URL url, ResourceBundle rb) {
       label = progress ;
       statProgressBar = progressBar;
    }    
    private static final int COUNT_LIMIT = 10;
    
    
	String wort;
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

	// Button New Game
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
		text.setText(textfieldString);
		counter = 1;
		hangmanImage.setImage(null);
		info.setText("");
	}

	// Check the letter input from Textfield
	public boolean letterCheck(char[] wortToArr, char[] displayWord) {

		for (int i = 0; i < wort.length(); i++) {
			if (wortToArr[i] == textField.getText().charAt(0)) {
				displayWord[i] = wortToArr[i];
				return true;
			}

		}
		return false;
	}

	// Button to confirm\check typed Letter
	public void checkLetter() {
		info.setText("");
		if (letterCheck(wortToArr, displayWord) == true) {
			rightletterString = new String(displayWord);
			text.setText(rightletterString);
			info.setText("Richtig");

		} else {
			hangmanImage.setImage(new Image("/images/" + counter + ".png"));
			info.setText("Falsch");
			counter++;
		}
		textField.clear();
	}

	// Returns a Word from txt file
	public String newWord() throws FileNotFoundException {
		Random random = new Random();
		String line;
		ArrayList<String> temp = new ArrayList<String>();
		InputStream in = getClass().getResourceAsStream("/wort.txt");
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		try {
			while ((line = bf.readLine()) != null) {
				temp.add(line);
			}
		} catch (IOException e) {

			e.getMessage();
		}

		return temp.get(random.nextInt(temp.size())).toString();
	}
	@Override
	  public void init() throws Exception {       
	      
	      // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
	      for (int i = 1; i <= COUNT_LIMIT; i++) {
	          double progress = (double) i/10;
	          System.out.println("progress: " +  progress);            
	          LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
	          Thread.sleep(250);
	      }
	      
	  }
	
	      
	  
	
	      
	  

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, HangmanPreload.class, args);
	}

}
