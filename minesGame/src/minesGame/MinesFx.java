package minesGame;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MinesFx extends Application{
	Mines mines;
	openArea[][] squares;
	Stage stage;
	GridPane pane;
	TextField heightTxt;
	TextField widthTxt;
	TextField minesNumTxt;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(makeGrid());
		this.stage=primaryStage;
		stage.setTitle("The Amazing Mines Sweeper");
		stage.setMinHeight(300);
		stage.setMinWidth(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private GridPane makeGrid()
	{
		GridPane pane1 = new GridPane();
		GridPane pane2 = new GridPane();
		GridPane pane3 = new GridPane();
		
		pane1.setPadding(new Insets(10));
		pane1.setHgap(10);
		pane1.setVgap(10);
		heightTxt = new TextField("10");
		widthTxt = new TextField("10");
		minesNumTxt = new TextField("10");
		heightTxt.setMaxWidth(40);
		widthTxt.setMaxWidth(40);
		minesNumTxt.setMaxWidth(40);
		heightTxt.setStyle("-fx-Background-color: lightblue");
		widthTxt.setStyle("-fx-Background-color: lightblue");
		minesNumTxt.setStyle("-fx-Background-color: lightblue");
		Label heightLbl = new Label("Height");
		Label widthLbl = new Label("Width");
		Label munesNumLbl = new Label("Mines");
		Button resetBtn = new Button("RESET");
		resetBtn.setStyle("-fx-Background-color: lightblue");
		heightLbl.setTextFill(Color.WHITE);
		munesNumLbl.setTextFill(Color.WHITE);
		widthLbl.setTextFill(Color.WHITE);
		resetBtn.setOnMouseClicked(new resetButton());
		resetBtn.setMinSize(50, 50);
		
		pane1.add(resetBtn,0,0);
		pane1.add(heightLbl, 0, 1);
		pane1.add(heightTxt, 1, 1);
		pane1.add(widthLbl, 0, 2);
		pane1.add(widthTxt, 1, 2);
		pane1.add(munesNumLbl, 0, 3);
		pane1.add(minesNumTxt, 1, 3);
		
		mines = new Mines(Integer.valueOf(heightTxt.getText()),Integer.valueOf(widthTxt.getText()),Integer.valueOf(minesNumTxt.getText()));
		squares=new openArea[Integer.valueOf(heightTxt.getText())][Integer.valueOf(widthTxt.getText())];
		for(int i=0;i<mines.getHeight();i++)
		{
			for(int j=0;j<mines.getWidth();j++)
			{
				pane2.add(makePlace(mines.get(i, j), i, j),i,j);
			}
		}
		pane3.add(pane2, 1, 0);
		pane3.add(pane1, 0, 0);
		this.pane=pane1;
		pane3.setPadding(new Insets(10));
		pane3.setStyle("-fx-Background-color: gray");
		return pane3;
	}
	
	private class openArea implements EventHandler<MouseEvent>
	{
		int i;
		int j;
		Button btn;
		
		public openArea(int i, int j, Button btn)
		{
			this.i=i;
			this.j=j;
			this.btn=btn;
		}
		@Override
		public void handle(MouseEvent e)
		{
			if(!((MouseEvent)e).getButton().equals(MouseButton.SECONDARY))
			{
				if(!mines.open(i, j))
				{
					Image img = new Image(getClass().getResource("lost.jpg").toExternalForm());
					ImageView imgView = new ImageView(img);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("");
					alert.setHeaderText(null);
					alert.getDialogPane().setPrefSize(350, 300);
					alert.setGraphic(imgView);
					alert.show();
					lostGame(true);
				}
				lostGame(false);
			}
			else
			{
				mines.toggleFlag(j, j);
				btn.setText(mines.get(i, j));
			}
			
		}
		
		public void lost(boolean fail)
		{
			if(fail)
			{
				mines.setShowAll(true);
			}
			btn.setText(mines.get(i, j));
		}
		
		
		public void lostGame(boolean fail)
		{
			for(int i=0;i<mines.getHeight();i++)
			{
				for(int j=0;j<mines.getWidth();j++)
				{
					squares[i][j].lost(fail);
				}
			}
			if(mines.winner())
			{
				Image img = new Image(getClass().getResource("won.jpg").toExternalForm());
				ImageView imgView = new ImageView(img);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("");
				alert.setHeaderText("");
				alert.getDialogPane().setPrefSize(350, 300);
				alert.setGraphic(imgView);
				alert.show();
			}
		}
		
	}
	

	
	private class resetButton implements EventHandler <MouseEvent>
	{

		@Override
		public void handle(MouseEvent event) 
		{
			mines = new Mines(Integer.valueOf(heightTxt.getText()),Integer.valueOf(widthTxt.getText()),Integer.valueOf(minesNumTxt.getText()));
			resetpane();
			
		}
		
	}
	
	private Button makePlace(String s,int i,int j)
	{
		Button b= new Button(s);
		b.setMinSize(25, 25);
		b.setStyle("-fx-Background-color: lightblue");
		b.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))));
		openArea open = new openArea(i,j,b);
		b.setOnMouseClicked(open);
		squares[i][j]=open;
		return b;
	}
	
	private void resetpane()
	{
		stage.setScene(new Scene(resetGrid()));
	}
	
	private GridPane resetGrid()
	{
		GridPane pane1 = new GridPane();
		GridPane pane2 = new GridPane();
		pane2.setPadding(new Insets(10));
		pane1.setPadding(new Insets(10));
		mines = new Mines(Integer.valueOf(heightTxt.getText()),Integer.valueOf(widthTxt.getText()),Integer.valueOf(minesNumTxt.getText()));
		squares = new openArea[Integer.valueOf(heightTxt.getText())][Integer.valueOf(widthTxt.getText())];
		for(int i=0;i<mines.getHeight();i++)
		{
			for(int j=0;j<mines.getWidth();j++)
			{
				pane1.add(makePlace(mines.get(i, j), i, j),i,j);
			}
		}
		pane2.add(pane1, 1, 0);
		pane2.add(this.pane, 0, 0);
		pane2.setStyle("-fx-Background-color: gray");
		return pane2;
	}
	public static void main(String[] args)
	{
		launch(args);
	}

}

