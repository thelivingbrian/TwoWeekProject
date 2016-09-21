package edu.bsu.cs222;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI extends Application {

	private String titleToQuery = "";
	private Query pageRequest;
    private TextField wikiTextEntry;
    private TextArea outputTextArea;
    private Button submit, userButton, revButton;
    private Text notificationText, resultOfQuery, actionRedirect, sceneTitle;
    private ScrollPane dataWindow;
	
	public static void main(String[] args) {
        launch(args);
    }
		
   	@Override
	public void start(Stage primaryStage) {              		
   		primaryStage.setTitle("Wikipedia User Search");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        wikiTextEntry = new TextField();
        submit = new Button("Submit");
        userButton = new Button("Show users");
        revButton = new Button("Show revisions");
        resultOfQuery = new Text();
        actionRedirect = new Text();
        dataWindow = new ScrollPane();
        sceneTitle = new Text("Search Wikipedia For...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        notificationText = new Text();
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        dataWindow.setContent(outputTextArea);
        dataWindow.setFitToWidth(true);
        
        grid.add(sceneTitle, 0, 0);
        grid.add(wikiTextEntry, 0, 1, 4, 1);
        grid.add(submit, 4, 1, 2, 1);
        grid.add(resultOfQuery, 0, 3);
        grid.add(actionRedirect, 2, 3);
        grid.add(dataWindow, 0, 4, 6, 3);
        grid.add(userButton, 2, 7);
        grid.add(revButton, 4, 7);
        grid.add(notificationText, 0, 7);

        setWidths();
        connectCode();
    
	    Scene scene = new Scene(grid, 750, 575);
	    primaryStage.setScene(scene);
	    primaryStage.show();
    }

	public void connectCode() {
		userButton.setOnAction(e -> buttonClick(e));
		revButton.setOnAction(e -> buttonClick(e));
		submit.setOnAction(e -> buttonClick(e));		
	}
	
	public void buttonClick(ActionEvent e) {
	    Formatter formatter;
	    if(e.getSource()==submit){
        	titleToQuery = wikiTextEntry.getText();
            wikiTextEntry.setText("");
            resultOfQuery.setText("");
            outputTextArea.setText("");

            if ( titleToQuery.equals("") ) {
          		notificationText.setFill(Color.FIREBRICK);
	      		notificationText.setText("INVALID INPUT");
          	} else {
                pageRequest = new Query(titleToQuery);
                formatter = new Formatter(pageRequest);
	        	
	      		notificationText.setFill(Color.FIREBRICK);
                notificationText.setText("Query sent");
	      		resultOfQuery.setText(formatter.getFormattedResult());
	      		outputTextArea.setText(formatter.getFormattedData());
          	}
        }
        if(e.getSource()==userButton){
        	formatter = new Formatter(pageRequest);
        	notificationText.setFill(Color.FIREBRICK);
      		notificationText.setText("Most active contributors");
      		outputTextArea.setText(formatter.makeUserAnalysis());
        }
        if(e.getSource()==revButton){
        	formatter = new Formatter(pageRequest);
        	notificationText.setFill(Color.FIREBRICK);
      		notificationText.setText("Most recent revisions");
      		outputTextArea.setText(formatter.getFormattedData());
        }
    }

	public void setWidths() {
		dataWindow.setPrefWidth(700);
	}
}