import java.awt.Point;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellView extends Application{
	/*
	 * GUI settings
	 */
	private final int MILLISECOND_DELAY = 15;	// speed of animation
	private final int EXTRA_VERTICAL = 100; 	// GUI area allowance when making the scene width
	private final int EXTRA_HORIZONTAL = 150; 	// GUI area allowance when making the scene width
	private final int BLOCK_SIZE = 12;     		// size of each cell in pixels
	private int NUM_ROWS = 5; 
	private int NUM_COLUMNS = 10;

	private Scene myScene;						// the container for the GUI
	private boolean paused = false;		
	private Button pauseButton;
	


	private Rectangle[][] mirrorCell;	// the Rectangle objects that will get updated and drawn.  It is 
	// called "mirror" maze because there is one entry per square in 
	// the maze.
	
	/*
	 * Maze color settings
	 */
	private Color[] color  = new Color[] {
			Color.rgb(200,0,0),		// wall color
			Color.rgb(128,128,255),	// path color
			Color.WHITE,			// empty cell color
			Color.rgb(200,200,200)	// visited cell color
	};  		// the color of each of the states  
	
	private CellController cellController;
	
	// Start of JavaFX Application
		public void start(Stage stage) {
			// Initializing logic state
			//cellController=new CellController(NUM_ROWS, NUM_COLUMNS, this);
			cellController=new CellController(NUM_ROWS,NUM_COLUMNS,this);

			
			// Initializing the gui
			myScene = setupScene();
			stage.setScene(myScene);
			stage.setTitle("Wild Fire");
			stage.show();

			// Makes the animation happen.  Will call "step" method repeatedly.
			KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(MILLISECOND_DELAY));
			Timeline animation = new Timeline();
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.getKeyFrames().add(frame);
			animation.play();
		}
		
		// Create the scene - Controls and Maze areas
		private Scene setupScene () {
			// Make three container 
			Group mazeDrawing = setupForest();

			HBox controls = setupControlButtons();
			HBox changes = setupChangeButtons();

			VBox root = new VBox();
			root.setAlignment(Pos.TOP_CENTER);
			root.setSpacing(10);
			root.setPadding(new Insets(10, 10, 10, 10));
			root.getChildren().addAll(mazeDrawing,controls,changes);

			Scene scene = new Scene(root, NUM_COLUMNS*BLOCK_SIZE+ EXTRA_HORIZONTAL, 
					NUM_ROWS*BLOCK_SIZE + EXTRA_VERTICAL, Color.ANTIQUEWHITE);

			return scene;
		}
		
		private HBox setupControlButtons(){
			// Make the controls part
			HBox controls = new HBox();
			controls.setAlignment(Pos.BASELINE_CENTER);
			controls.setSpacing(10);

			

			pauseButton = new Button("Pause");
			pauseButton.setOnAction(value ->  {
				pressPause();
			});
			controls.getChildren().add(pauseButton);

			Button stepButton = new Button("Step");
			stepButton.setOnAction(value ->  {
				cellController.doOneStep(MILLISECOND_DELAY);
			});
			controls.getChildren().add(stepButton);
			
			
			return controls;
		}
		
		private HBox setupChangeButtons(){
			HBox changes = new HBox();
			changes.setAlignment(Pos.BASELINE_CENTER);
			changes.setSpacing(5);
			
			Button newMazeButton = new Button("New Forest");
			newMazeButton.setOnAction(value ->  {
				cellController.newForest(NUM_ROWS,NUM_COLUMNS);
			});
			changes.getChildren().add(newMazeButton);

			Label burnTime= new Label("Burn Time:");
			TextField burnAmount= new TextField();
			newMazeButton.setOnAction(value -> {
				String burnAmountString=burnAmount.getText();
				int burnAmountInt=Integer.parseInt(burnAmountString);
				cellController.setBurnTime(burnAmountInt);
			});
			changes.getChildren().addAll(burnTime, burnAmount);
			
			Label gridRows= new Label("Rows:");
			TextField rowAmount= new TextField();
			newMazeButton.setOnAction(value -> {
				String rowAmountString=rowAmount.getText();
				int rowAmountInt=Integer.parseInt(rowAmountString);
				NUM_ROWS=rowAmountInt;
			});
			changes.getChildren().addAll(gridRows,rowAmount);
			
			
//		
//			Button increaseSpreadProbability= new Button("Increase Spread Probability");
//			increaseSpreadProbability.setOnAction(value -> {
//				cellController.increaseSpreadProbability();
//			});
//			changes.getChildren().add(increaseSpreadProbability);
//			
//			Button decreaseSpreadProbability= new Button("Decrease Spread Probability");
//			decreaseSpreadProbability.setOnAction(value -> {
//				cellController.decreaseSpreadProbability();
//			});
//			changes.getChildren().add(decreaseSpreadProbability);
//			
//			Button increaseForestDensity= new Button("Increase Forest Density");
//			increaseForestDensity.setOnAction(value -> {
//				cellController.increaseForestDensity();
//			});
//			changes.getChildren().add(increaseForestDensity);
//			
//			Button decreaseForestDensity= new Button("Decrease Forest Density");
//			decreaseForestDensity.setOnAction(value -> {
//				cellController.decreaseForestDensity();
//			});
//			changes.getChildren().add(decreaseForestDensity);
//			
//			Button increaseNumberOfBurningTrees= new Button("Increase Number of Burning Trees");
//			increaseNumberOfBurningTrees.setOnAction(value -> {
//				cellController.increaseNumberOfBurningTrees();
//			});
//			changes.getChildren().add(increaseNumberOfBurningTrees);
//			
//			Button decreaseNumberOfBurningTrees= new Button("Decrease Number of Burning Trees");
//			decreaseNumberOfBurningTrees.setOnAction(value -> {
//				cellController.decreaseNumberOfBurningTrees();
//			});
//			changes.getChildren().add(decreaseNumberOfBurningTrees);
			return changes;
		}



		public Point getMazeDimensions() {
			return new Point(NUM_ROWS, NUM_COLUMNS);
		}

		/*
		 * Setup the maze part for drawing. In particular,
		 * make the mirrorMaze.
		 */
		private Group setupForest(){
			Group drawing = new Group();
			mirrorCell = new Rectangle[NUM_ROWS][NUM_COLUMNS];
			for(int i = 0; i< NUM_ROWS; i++){
				for(int j =0; j < NUM_COLUMNS; j++){
					Rectangle rect = new Rectangle(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					rect.setFill(color[cellController.getCellState(new Point(i,j))]);
					mirrorCell[i][j] = rect;
					drawing.getChildren().add(rect);
				}	
			}
			return drawing;
		}
		

		/*
		 * Toggle the pause button
		 */
		public void pressPause(){
			this.paused = !this.paused;
			if(this.paused){
				pauseButton.setText("Resume");
			}
			else{
				pauseButton.setText("Pause");
			}
		}

		/*
		 * Pause the animation (regardless of current state of pause button)
		 */
		public void pauseIt(){
			this.paused = true;
			pauseButton.setText("Resume");
		}

		/*
		 * resets all the rectangle colors according to the 
		 * current state of that rectangle in the maze.  This 
		 * method assumes the display maze matches the model maze
		 */
		public void redraw(){
			for(int i = 0; i< mirrorCell.length; i++){
				for(int j =0; j < mirrorCell[i].length; j++){
					mirrorCell[i][j].setFill(color[cellController.getCellState(new Point(i,j))]);
				}
			}
		}

		/*
		 * Does a step in the search only if not paused.
		 */
		public void step(double elapsedTime){
			if(!paused) {
				cellController.doOneStep(elapsedTime);
			}
		}



		public static void main(String[] args) {
			launch(args);
		}



}
