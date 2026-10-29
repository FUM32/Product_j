import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.Scanner;
import java.io.*;

public class Kadai04_1 extends Application
{
	private TextField[][] tf = new TextField[9][9];
	private static int[][] board = new int[9][9];
	
	public static void main(String[] args)
	{
		
		String fname = "input.txt";
		
		if(args.length>0) fname = args[0];
		
		try{
			Scanner sc = new Scanner(new File(fname));
			for(int i=0; i<9; i++){
				for(int j=0; j<9; j++){
					board[i][j] = sc.nextInt();
					if (board[i][j]<0 || 9 < board[i][j]) board[i][j] = 0;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		launch(args);
		
	}
	
	public void start(Stage stage)throws Exception
	{
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if (board[i][j] == 0){
					tf[i][j] = new TextField();
				}else{
					tf[i][j] = new TextField(String.valueOf(board[i][j]));
					tf[i][j].setEditable(false);
					tf[i][j].setBackground(new Background
					  (new BackgroundFill(Color.GRAY, null, null)));
				}
				tf[i][j].setMaxWidth(40);
				tf[i][j].setFont(Font.font("MonoSpace", 20));
			}
		}
		
		GridPane gp = new GridPane();
		gp.setHgap(5);
		gp.setVgap(5);
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				gp.add(tf[i][j],j,i);
			}
		}
		gp.setAlignment(Pos.CENTER);
		
		Scene sc = new Scene(gp,500,500);
		stage.setScene(sc);
		stage.setTitle("3x3");
		stage.show();
	}
}