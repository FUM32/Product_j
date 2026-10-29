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

public class Kadai04_2 extends Application
{
	private TextField[][] tf = new TextField[9][9];
	private static int[][] board = new int[9][9];

	private Button bt = new Button("リセット");
	
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
		

		GridPane main_gp = new GridPane();
		main_gp.setHgap(12);
		main_gp.setVgap(12);
		main_gp.setAlignment(Pos.CENTER);


		for(int k=0; k<3; k++){
			for(int l=0; l<3; l++){
				GridPane sub_gp1 = new GridPane();
				sub_gp1.setHgap(4);
				sub_gp1.setVgap(4);
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						sub_gp1.add(tf[k*3+i][l*3+j],j,i);
					}
				}
				main_gp.add(sub_gp1,l,k);
			}
		}
	
		GridPane sub_gp2 = new GridPane();
		sub_gp2.add(bt,0,0);
		sub_gp2.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setCenter(main_gp);
		bp.setBottom(sub_gp2);

		Scene sc = new Scene(bp,500,500);
		stage.setScene(sc);
		stage.setTitle("数独");
		stage.show();
	}
}