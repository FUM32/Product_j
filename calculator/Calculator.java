import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;
import java.io.*;
import java.text.*;

public class Kadai06_2 extends Application{
	private TextField tf;
	private Button[][] bt = new Button[4][5];
	
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage stage)throws Exception{
		tf = new TextField();
		tf.setEditable(false);
		tf.setMaxWidth(380);
		tf.setFont(Font.font("MonoSpace",40));
		tf.setAlignment(Pos.CENTER_RIGHT);
		
		String[][] bt_str = {{"CE","C","BS","/"},{"7","8","9","*"},{"4","5","6","-"},
							 {"1","2","3","+"},{"+-","0",".","="}};
		for(int i=0; i<4; i++){
			for(int j=0; j<5; j++){
				bt[i][j] = new Button(bt_str[j][i]);
				bt[i][j].setPrefWidth(95);
				bt[i][j].setPrefHeight(95);
				bt[i][j].setFont(Font.font("MonoSpace",30));
				bt[i][j].setOnAction(new ButtonEventHandler());
			}
		}
		
		GridPane gp = new GridPane();
		gp.setHgap(2);
		gp.setVgap(2);
		for(int i=0; i<4; i++){
			for(int j=0; j<5; j++){
				gp.add(bt[i][j],i,j);
			}
		}
		gp.setAlignment(Pos.CENTER);
		
		BorderPane bp = new BorderPane();
		bp.setTop(tf);
		bp.setAlignment(tf,Pos.CENTER);
		bp.setCenter(gp);
		
		Scene sc = new Scene(bp,400,600);
		stage.setScene(sc);
		stage.setTitle("電卓");
		stage.show();
	}
	
	class ButtonEventHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			String in = ((Button)e.getSource()).getText();
			StringBuffer stb = new StringBuffer(tf.getText());
			
			if(stb.toString().equals("Error") && !in.equals("CE") && !in.equals("C")){

			}else if(in.equals("=")){
				String br = new String(stb.toString());
				
				String regex = "[+\\-]?[0-9]+\\.?[0-9]*[+\\-\\*/]{1}[0-9]+\\.?[0-9]*";
				if (br.matches(regex)){
					String regex2 = "(?=[+\\-\\*/])";
					String[] operands = br.split(regex2);
					int op1 = Integer.parseInt(operands[0]);
					int op2 = Integer.parseInt(operands[1].substring(1));

					StringBuffer opb = new StringBuffer(operands[1]);
					char op = opb.charAt(0);
					if(op=='/' && op2==0){
						stb = new StringBuffer("Error");
					}else{
						double result = 0;
						if(op == '+'){
							result = op1 + op2;
						}else if(op == '-'){
							result = op1 - op2;
						}else if(op == '*'){
							result = op1 * op2;
						}else if(op == '/'){
							result = op1 / op2;
						}
						NumberFormat nf = new DecimalFormat("0.######");
						String str2 = nf.format(result);
						stb = new StringBuffer(str2);
					}
				}

			}else if(in.equals("CE") || in.equals("C")){
				stb = new StringBuffer("");
			}else if(in.equals("BS")){
				if(stb.length() > 0){
					stb.deleteCharAt(stb.length() - 1);
				}
			}else if(in.equals("+-")){
				if(stb.length()>0){
					char c = stb.charAt(0);
					if(c == '-'){
						stb.deleteCharAt(0);
					}else if(c == '+'){
						stb.deleteCharAt(0);
						stb.insert(0,'-');
					}else{
						stb.insert(0, '-');
					}
				}
			}else{
				stb.append(in);
			}
			
			String str = new String(stb.toString());
			tf.setText(str);
		}
	}
}