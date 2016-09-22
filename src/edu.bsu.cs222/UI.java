package edu.bsu.cs222;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class UI extends Application {

	private String titleToQuery = "";
	private Query query;
    private TextField wikiTextEntry;
    private TextArea outputTextArea;
    private Button submit, userButton, revButton;
    private Text resultText, redirectText, actionRedirect, sceneTitle;
    private Label label;
    private ScrollPane revisionWindow;
	private StackPane userWindow;
    private ObservableList<Revision> data = FXCollections.observableArrayList();
    private ListView listview;
    private RevisionList revisions;
    private Revision selectedItem;
    private VBox outputBox;
    private Formatter formatter;

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
        userButton = new Button("Show user");
        revButton = new Button("Show revisions");
        redirectText = new Text();
        actionRedirect = new Text();
        //userWindow = new ScrollPane();
        userWindow = new StackPane();
        sceneTitle = new Text("Search Wikipedia For...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        resultText = new Text();
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        label = new Label("Hi");
        revisionWindow = new ScrollPane();
        revisionWindow.setContent(outputTextArea);
        revisionWindow.setVisible(false);


        listview = new ListView<Revision>(data);

        userWindow.getChildren().add(listview);
        userWindow.setVisible(false);

        outputBox = new VBox();
        outputBox.getChildren().addAll(userWindow);


        grid.add(sceneTitle, 0, 0);
        grid.add(wikiTextEntry, 0, 1, 4, 1);
        grid.add(submit, 4, 1, 2, 1);
        grid.add(redirectText, 0, 3);
        grid.add(actionRedirect, 2, 3);
        grid.add(outputBox, 0, 4, 6, 2);
        grid.add(label, 0, 7, 6, 1);
        grid.add(userButton, 2, 8);
        grid.add(revButton, 4, 8);
        grid.add(resultText, 0, 8);

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

        listview.setOnMousePressed(e -> changeFocus(e));

	}

    public void changeFocus(MouseEvent e) {
        selectedItem = (Revision) listview.getSelectionModel().getSelectedItem();
        label.setText(new Formatter().formatRevision(selectedItem));
    }

    public void buttonClick(ActionEvent e) {
	    formatter = new Formatter();
        titleToQuery = wikiTextEntry.getText();
        query = new Query.QueryBuilder(titleToQuery).revQuery().build();
        if(e.getSource()==submit) {
            getRevisionData(query.revisionList());
            formatter.formatQuery(query);

            clearData();

            resultText.setFill(Color.FIREBRICK);
            resultText.setText(formatter.getQueryResult());
            redirectText.setText(formatter.getQueryRedirect());
            userWindow.setVisible(true);

        }
        if(e.getSource()==userButton){
            clearData();
            Query requestedUser = new Query.QueryBuilder(selectedItem.getAuthor()).userQuery().build();
            revisions = requestedUser.revisionList();
            formatter.formatQuery(requestedUser);

            data.setAll(revisions.getArray());
            listview.setItems(data);
            userWindow.setVisible(true);
        } /*
        if(e.getSource()==revButton){
            userWindow.setVisible(false);
            revisionWindow.setVisible(true);
        	resultText.setFill(Color.FIREBRICK);
      		resultText.setText("Most recent revisions");
        } */

        listview.setCellFactory(new Callback<ListView<Revision>,
                    ListCell<Revision>>() {
                @Override
                public ListCell<Revision> call(ListView<Revision> listview) {
                    return new TextCell();
                }
            }
        );

    }


    private void getRevisionData(RevisionList revisions) {
        data.addAll(revisions.getArray());
        listview.setItems(data);
    }

    public void clearData() {
        wikiTextEntry.setText("");
        redirectText.setText("");
        outputTextArea.setText("");
    }

    static class TextCell extends ListCell<Revision> {
        @Override
        public void updateItem(Revision item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText("Revised by: \t" + item.getAuthor() + " at " + item.getReadableTS());
            }
        }
    }

	public void setWidths() {
		userWindow.setPrefWidth(800);
	}
}