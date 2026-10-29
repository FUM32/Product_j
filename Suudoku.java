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
import javafx.event.*;

public class Kadai04_3 extends Application
{
	private TextField[][] tf = new TextField[9][9];
	private static int[][] board = new int[9][9];

	private Button bt = new Button("リセット");
    
	private Label lb = new Label("空いているマスに1桁の数字を入力してください");
	
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

                if(board[i][j]==0){
                    tf[i][j].setOnAction(new InputEventHandler());
                }
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
        bt.setOnAction(new ResetEventHandler());
		sub_gp2.add(bt,0,0);
		sub_gp2.setAlignment(Pos.CENTER);

        GridPane sub_gp3 = new GridPane();
		sub_gp3.add(lb,0,0);
		sub_gp3.setAlignment(Pos.CENTER);

        GridPane sub_gp4 = new GridPane();
        sub_gp4.setVgap(10);
        sub_gp4.add(sub_gp3,0,0);
        sub_gp4.add(sub_gp2,0,1);
        sub_gp4.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(main_gp);
		bp.setBottom(sub_gp4);

		Scene sc = new Scene(bp,500,450);
		stage.setScene(sc);
		stage.setTitle("数独");
		stage.show();
	}

    class InputEventHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			int judge = 0;
            int[][] a = new int[9][9];

            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    try{
                        a[i][j] = Integer.valueOf(tf[i][j].getText());
                        if(a[i][j]<0 || 9<a[i][j]){
                            a[i][j] = 0;
                        }
                    }catch(Exception ee){
                        a[i][j] = 0;
                    }
                    if(a[i][j]==0){
                        judge = 1;
                        break;
                    }
                }
            }

            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    for(int k=0; k<9; k++){
                        if(j!=k){
                            if(a[i][j]==a[i][k]){
                                judge = 1;
                            }
                        }
                    }
                }
            }

            for(int j=0; j<9; j++){
                for(int i=0; i<9; i++){
                    for(int k=0; k<9; k++){
                        if(i!=k){
                            if(a[i][j]==a[k][j]){
                                judge = 1;
                            }
                        }
                    }
                }
            }

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    for(int k=0; k<3; k++){
                        for(int l=0; l<3; l++){
                            for(int m=0; m<3; m++){
                                for(int n=0; n<3; n++){
                                    if (k != m || l != n) {
                                        if (a[i*3+k][j*3+l] == a[i*3+m][j*3+n]) judge = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(judge==0){
                lb.setText("ゲームクリア！おめでとうございます！！");
            }else{
                lb.setText("空いているマスに1桁の数字を入力してください");
            }

		}
	}

    class ResetEventHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			for(int i=0; i<9; i++){
				for(int j=0; j<9; j++){
					if (board[i][j] == 0) tf[i][j].setText("");
				}
			}
			lb.setText("空いているマスに1桁の数字を入力してください");
		}
	}
}