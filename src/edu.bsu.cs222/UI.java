package edu.bsu.cs222;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;

public class UI extends Application {


	private Query query;
    private TextField wikiTextEntry;
    private Button submitButton, switchButton;
    private Text statusText, redirectText, sceneTitle;
    private Label label;
	private StackPane userWindow;
    private ObservableList<Revision> data = FXCollections.observableArrayList();
    private ListView listview;
    private Revision selectedItem;
    private VBox outputBox;
    private Formatter formatter;
    private String previousTitle;

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
        submitButton = new Button("Search");

        switchButton = new Button("Show user");
        switchButton.setDisable(true);


        redirectText = new Text();
        userWindow = new StackPane();
        sceneTitle = new Text("Search Wikipedia For...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        statusText = new Text();
        label = new Label("");

        listview = new ListView<Revision>(data);
        userWindow.getChildren().add(listview);
        userWindow.setVisible(false);

        outputBox = new VBox();
        outputBox.getChildren().addAll(userWindow);


        grid.add(sceneTitle, 0, 7);
        grid.add(wikiTextEntry, 0, 8, 6, 1);
        grid.add(submitButton, 6, 8, 2, 1);
        grid.add(redirectText, 0, 0);
        grid.add(outputBox, 0, 3, 8, 4);
        grid.add(label, 0, 1, 8, 2);
        grid.add(switchButton, 8, 8);
        grid.add(statusText, 5, 7);

        setWidths();
        connectCode();
    
	    Scene scene = new Scene(grid, 750, 575);
	    primaryStage.setScene(scene);
	    primaryStage.show();
    }

    private void newVBox() {
        outputBox = new VBox();
    }

    public void connectCode() {
		switchButton.setOnAction(e -> buttonClick(e));
		submitButton.setOnAction(e -> buttonClick(e));
        listview.setOnMousePressed(e -> changeFocus(e));
        wikiTextEntry.setOnKeyReleased(e -> enableSearch(e));
	}

    private void enableSearch(KeyEvent e) {
        submitButton.setDisable(false);
        //submitButton.requestFocus();
    }

    public void changeFocus(MouseEvent e) {
        selectedItem = (Revision) listview.getSelectionModel().getSelectedItem();
        label.setText(new Formatter().formatRevision(selectedItem));
        switchButton.setDisable(false);
    }

    public void buttonClick(ActionEvent e) {
	    formatter = new Formatter();
        if(e.getSource()== submitButton) {
            String titleToQuery = wikiTextEntry.getText();
            previousTitle = titleToQuery;
            Query query = new Query.QueryBuilder(titleToQuery).revQuery().build();
            formatter.formatQuery(query);
            clearData();
            setStatusText(formatter.getQueryResult());
            setRedirectText(formatter.getQueryRedirect());
            setRevisionData(query);
            submitButton.setDisable(true);
            switchButton.setVisible(true);
            userWindow.setVisible(true);
        }
        if(e.getSource()== switchButton){
            switchToUser();
            switchButton.setDisable(true);
        }

        listview.setCellFactory(new Callback<ListView<Revision>,
                    ListCell<Revision>>() {
                @Override
                public ListCell<Revision> call(ListView<Revision> listview) {
                    return new TextCell();
                }
            }
        );

    }


    private void switchToUser() {
        Query requestedUser = new Query.QueryBuilder(selectedItem.getAuthor()).userQuery().build();
        formatter.formatQuery(requestedUser);
        setRedirectText("Showing revisions made by:  " + selectedItem.getAuthor() + ".");
        setRevisionData(requestedUser);
    }

    private void switchToTitle() {
        //formatter = new Formatter();
        Query requestedPage = new Query.QueryBuilder(selectedItem.getTitle()).revQuery().build();
        formatter.formatQuery(requestedPage);
        setStatusText(formatter.getQueryResult());
        setRedirectText(formatter.getQueryRedirect());
        setRevisionData(requestedPage);
    }


    public void clearData() {
        wikiTextEntry.setText("");
        redirectText.setText("");
        label.setText("");
        newVBox();
    }
    private void setRevisionData(Query query) {
        RevisionList revisions = query.revisionList();
        data.setAll(revisions.getArray());
        listview.setItems(data);
    }

    public void setStatusText(String status) {
        statusText.setFill(Color.FIREBRICK);
        statusText.setText(status);
    }

    public void setRedirectText(String redirectText) {
        this.redirectText.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        this.redirectText.setText( redirectText );
    }

    private static class TextCell extends ListCell<Revision> {
        @Override
        public void updateItem(Revision item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText("Revision by: \t" + item.getAuthor() + " at " + item.getReadableTS());
            }
        }
    }

	public void setWidths() {
		userWindow.setPrefWidth(800);
	}
}