package application;


import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ShowGrid extends BorderPane{
	private Label label = new Label();
	private List<Label> labels = new ArrayList<Label>();

	public ShowGrid(int[][] array) {
		
	
		GridPane pane = new GridPane();
				
		pane.setPadding(new Insets(5,5,5,5));
		pane.setHgap(0);
		pane.setVgap(0);
	
			
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				label = new Label("" + array[row][column]);
				label.getStyleClass().add("hLabelStyle");
				
				
				
				label.setPadding(new Insets(5, 5, 5, 5));
				label.setAlignment(Pos.CENTER);
				label.setPrefSize(75, 75);
			
		        GridPane.setMargin(label, new Insets(0,10,10,0));
				
				labels.add(label);
				
				pane.add(label, column, row);

			}
		}
		shadeGrid();

		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		 
		Text t = new Text();
		t.setEffect(ds);
		t.setCache(true);
		t.setX(10.0f);
		t.setY(270.0f);
		t.setFill(Color.WHITE);
		t.setText("Sudoku");
		t.setFont(Font.font(null, FontWeight.BOLD, 50));
		HBox title = new HBox(t);	
		title.getStyleClass().add("borderStyle");
		title.setAlignment(Pos.CENTER);
		pane.getStyleClass().add("gridStyle");
		//setTop(title);
		setCenter(pane);
				
	}

	private void shadeGrid() {
		for(int i=0 ; i<(int)Math.sqrt(9); i++ )
        {
            for(int j=0 ; j<(int)Math.sqrt(9) ; j++ )
            {
               if( (i+j) % 2 != 0 )
               {
                   int r = i*((int)Math.sqrt(9)) ;
                   int c = j*((int)Math.sqrt(9)) ;
                   for(int x = 0 ; x < (int)Math.sqrt(9) ; x++)
                   {
                       for(int y = 0 ; y < (int)Math.sqrt(9) ; y++)
                       {
                           labels.get((r+x)*9 + (c+y)).getStyleClass().add("newLabelStyle");
                       }
                   }
                   
               }
            }
        }
		
	}
	

}
