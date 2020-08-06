package application;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HangmanPreload extends Preloader {

	private Stage preloaderStage;
	private Scene scene;

	
	@Override
	public void init() throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("Preloader.fxml"));
		scene = new Scene(root1);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		preloaderStage.setScene(scene);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.show();
		
		
	}
	@Override
	public void handleApplicationNotification(Preloader.PreloaderNotification info) {
		if(info instanceof ProgressNotification) {
			StartScreenController.statProgressBar.setProgress(((ProgressNotification) info).getProgress() );
			}
	}
	@Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
      
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }

}
}
