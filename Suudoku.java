import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import javafx.event.*;
import javafx.collections.*;

public class Kadai05_1 extends Application
{
	private ArrayList<ComboBox<String>> cb_list
			= new ArrayList<ComboBox<String>>();
	private static int[][] board = new int[9][9];

	private Button bt = new Button("リセット");
    
	private Label lb = new Label("各コンボボックスで数字を選択してください");
	
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
		ObservableList<String> candibate =
			FXCollections.observableArrayList
				("1","2","3","4","5","6","7","8","9");
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				ComboBox<String> cb = new ComboBox<String>();
				if (board[i][j] == 0){
					cb.setItems(candibate);
					cb.setOnAction(new InputEventHandler());
				}else{
					ObservableList<String> ol=
						FXCollections.observableArrayList();
					ol.add(String.valueOf(board[i][j]));
					cb.setItems(ol);
					cb.setValue(String.valueOf(board[i][j]));
					cb.setBackground(new Background
					  (new BackgroundFill(Color.GRAY, null, null)));
				}
				cb_list.add(cb);
			}
		}

		GridPane main_gp = new GridPane();
		main_gp.setHgap(16);
		main_gp.setVgap(20);
		main_gp.setAlignment(Pos.CENTER);

		for(int k=0; k<3; k++){
			for(int l=0; l<3; l++){
				GridPane sub_gp1 = new GridPane();
				sub_gp1.setHgap(4);
				sub_gp1.setVgap(4);
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						sub_gp1.add(cb_list.get((k*3+i)*9+(l*3+j)),j,i);
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

		Scene sc = new Scene(bp,650,400);
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
                	if(cb_list.get(9*i+j).getValue() != null){
		                try{
		                    a[i][j] = Integer.parseInt(cb_list.get(9*i+j).getValue());
		                    if(a[i][j]<0 || 9<a[i][j]){
                            a[i][j] = 0;
                        	}
		                }catch(Exception ee){
		                    a[i][j] = 0;
		                }
					}else{
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
                lb.setText("各コンボボックスで数字を選択してください");
            }

		}
	}

    class ResetEventHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			for(int i=0; i<9; i++){
				for(int j=0; j<9; j++){
					if (board[i][j] == 0) cb_list.get(9*i+j).setValue(null);
				}
			}
			lb.setText("各コンボボックスで数字を選択してください");
		}
	}
}