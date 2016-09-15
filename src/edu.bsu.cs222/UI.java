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

	String titleToQuery = "";
	Page requestedPage = new Page();
	TextField wikiTextEntry;
	TextArea textArea;
    Button submit, userButton, revButton;
    Text actiontarget, actionTitle, actionRedirect, sceneTitle;
    ScrollPane dataWindow;
	
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
        actionTitle = new Text();
        actionRedirect = new Text();
        dataWindow = new ScrollPane();
        sceneTitle = new Text("Search Wikipedia For...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        actiontarget = new Text();
        textArea = new TextArea();
        dataWindow.setContent(textArea);
        dataWindow.setFitToWidth(true);
        
        grid.add(sceneTitle, 0, 0);
        grid.add(wikiTextEntry, 0, 1, 4, 1);
        grid.add(submit, 4, 1, 2, 1);
        grid.add(actionTitle, 0, 3);
        grid.add(actionRedirect, 2, 3);
        grid.add(dataWindow, 0, 4, 6, 3);
        grid.add(userButton, 2, 7);
        grid.add(revButton, 4, 7);
        grid.add(actiontarget, 0, 7);

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
	
	public void buttonClick(ActionEvent e)
    {
	    
        Formatter formatter;
        
	    if(e.getSource()==submit){
        	titleToQuery = wikiTextEntry.getText();
          	if (titleToQuery.equals("")) {
          		actiontarget.setFill(Color.FIREBRICK);
	      		actiontarget.setText("INVALID INPUT");
          	}else{
	        	wikiTextEntry.setText("");
	        	
	          	requestedPage.query(titleToQuery);
	      		formatter = new Formatter(requestedPage);
	        	
	      		actiontarget.setFill(Color.FIREBRICK);
	      		actiontarget.setText("Query sent");
	      		actionTitle.setText(formatter.makeTitle());
	      		textArea.setText(formatter.makeData());
          	}
        }
        if(e.getSource()==userButton){
        	formatter = new Formatter(requestedPage);
        	actiontarget.setFill(Color.FIREBRICK);
      		actiontarget.setText("Most active contributors");
      		textArea.setText(formatter.makeUserAnalysis());
        }
        if(e.getSource()==revButton){
        	formatter = new Formatter(requestedPage);
        	actiontarget.setFill(Color.FIREBRICK);
      		actiontarget.setText("Most recent revisions");
      		textArea.setText(formatter.makeData());
        }
    }

	public void setWidths() {
		dataWindow.setPrefWidth(700);
	}
}