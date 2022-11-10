import application.MazeController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private final int NUM_ROWS = 31; 
	private final int NUM_COLUMNS = 41;

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
			cellController=new cellController(NUM_ROWS, NUM_COLUMNS, this);

			
			// Initializing the gui
			myScene = setupScene();
			stage.setScene(myScene);
			stage.setTitle("aMAZEing");
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
			Group mazeDrawing = setupMaze();
			HBox searches = setupSearchButtons();
			HBox controls = setupControlButtons();

			VBox root = new VBox();
			root.setAlignment(Pos.TOP_CENTER);
			root.setSpacing(10);
			root.setPadding(new Insets(10, 10, 10, 10));
			root.getChildren().addAll(searches,mazeDrawing,controls);

			Scene scene = new Scene(root, NUM_COLUMNS*BLOCK_SIZE+ EXTRA_HORIZONTAL, 
					NUM_ROWS*BLOCK_SIZE + EXTRA_VERTICAL, Color.ANTIQUEWHITE);

			return scene;
		}


}
