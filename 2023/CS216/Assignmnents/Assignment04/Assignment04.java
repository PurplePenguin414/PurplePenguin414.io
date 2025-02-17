package CS216.Assignment04;

// Delta College - CST 283 - Gibbs
// This program executes a application to simulate a fire. the grid will start green and transition every 5 secconds to
// assign the new color of the grid until the whole grid is burned

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import java.util.Random;



public class Assignment04 extends Application
{
    private final int SCENE_WIDTH = 900;             // Pixel dimensions of application window
    private final int SCENE_HEIGHT = 600;

    private final int NUM_ROWS = 7;                 // Grid dimensions within window
    private final int NUM_COLS = 7;

    private final double ANIMATION_FRAME_RATE = 5000; // 5000 milliseconds or 5 seconds

    private Random randomNumGenerator;                 // Define random generator classwide

    private Pane mainGridArea;                // Main drawing area

    private Rectangle theGridRectangles[][];  // 2-D array of grid elements for background color
    private Text theGridText[][];             // 2-D array of grid elements for foreground text

    private boolean BurntGrid[][];        // 2-D marker array to retain clicked grid elements
    
    private int count = 0;             //itterations count
    
    private String wind = getRandomWind();
    
    private int numBurned = 0;            //burned square count
    
    private boolean dead = false;

    // ---------------------------------------------------------------------------
    public static void main(String[] args)
    {
        // Launch the application.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        randomNumGenerator = new Random();   // Global random object for creation of random colors

        mainGridArea = new Pane();           // Main drawing pane for grid

        initializeGrid();                    // Set up the arrays and grid elements.

        // Set up primary scene
        Scene scene = new Scene(mainGridArea, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation Grid");
        primaryStage.show();

        // Launch animation
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_FRAME_RATE), new EventHandler<ActionEvent>()
        {
            // This method is executed periodically for the given frame rate.  It fires a method that updates
            // the state of the grid color rectangle and text for the next animation cycle.
            @Override
            public void handle(ActionEvent t)
            {
                updateGridForCycle();
            }
        }));

        // Set number of cycles (infinite) and play simulation.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
   }
    // ===========================================================================
    // ===========================================================================

    // Various actions related to the simulation
    public void initializeGrid()
    {
        // Instantiate all required 2-D arrays
        theGridRectangles = new Rectangle[NUM_ROWS][NUM_COLS];
        theGridText       = new Text[NUM_ROWS][NUM_COLS];
        BurntGrid     = new boolean[NUM_ROWS][NUM_COLS];
        
        Font labelFont = Font.font("Arial", 30);

        // Loop through entire grid row-by-row and initilize.  For each cell, instantiate
        // a Rectangle object for background color and a Text object for a foreground label.
        for (int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++)
            {
                // Set up Rectangle background.  Add to drawing pane.
                Rectangle cell = new Rectangle(getLeftCellEdge(j), getTopCellEdge(i), getCellWidth(), getCellHeight());
                cell.setFill(Color.rgb(52, 140, 49));
                cell.setStroke(Color.BLACK);
                theGridRectangles[i][j] = cell;
                mainGridArea.getChildren().add(theGridRectangles[i][j]);

                // Set up Text foreground.  Add to drawing pane.
                theGridText[i][j] = new Text(" ");
                theGridText[i][j].setFont(labelFont);
                mainGridArea.getChildren().add(theGridText[i][j]);
            }
         }
    }

    
    public void updateGridForCycle()
    {
        for (int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++)
            {
               // Remove rectangle and text objects from current grid.
                  mainGridArea.getChildren().remove(theGridRectangles[i][j]);
                  mainGridArea.getChildren().remove(theGridText[i][j]);
   
                  theGridRectangles[i][j].setStroke(Color.BLACK);    // Draw grid boundary
                  
               if (theGridRectangles[i][j].getFill().equals(Color.rgb(254, 222, 23))){
                  // Convert pixel coordinates into row/column grid coordinates
                  int row = getRowBurned(i);
                  int column = getColumnBurned(j);

                  // mark cell burned
                  BurntGrid[row][column] = true;
                  
                  
               }else{
               
                  // Change to random colors for remaining
                  // grid elements.
                    Color newColor = getRandomColor();
                    theGridRectangles[i][j].setFill(newColor);

                    newColor = getRandomColor();
                    theGridText[i][j].setStroke(newColor);
                
               }
               
                // Add Text object at calculated center of grid
                theGridText[i][j].setX(getCenteredText_X(theGridText[i][j],j));
                theGridText[i][j].setY(getCenteredText_Y(theGridText[i][j],i));

                // Add rectangle and text objects back to drawing area.
                mainGridArea.getChildren().add(theGridRectangles[i][j]);
                mainGridArea.getChildren().add(theGridText[i][j]);
                
                

            }
         }
         // increase the count of itterations
         count++;
         
         //set numburned to 0 so we can count if all are burned
            numBurned = 0;
            for (int i = 0; i < NUM_ROWS; i++){
               for (int j = 0; j < NUM_COLS; j++){
                  if (theGridRectangles[i][j].getFill().equals(Color.rgb(254, 222, 23))){
                     numBurned++;              
                  }else;
               }
            }
            
            //if all are burned show how many itterations it took to do this
            if (numBurned == 49){
               dead = false;
               System.out.println("\nThe number of itterations until the board was burned was " + count);
               System.out.println("The wind direction was " + wind);
               System.exit(0);
            }else;
            
    }

    // ---------------------------------------------------------------------------
    // These methods receive a text object and a row or column.  They return the
    // calculated pixel position of the object within the center of the drawn grid
    // element.  Font "metrics" are used to assist in the is calculation.
    public double getCenteredText_X(Text textObject, int col)
    {
        double textWidth = textObject.getLayoutBounds().getWidth();
        return getLeftCellEdge(col) + 0.5 * getCellWidth() - 0.5 * textWidth;
    }

    public double getCenteredText_Y(Text textObject, int row)
    {
        double textHeight = textObject.getLayoutBounds().getHeight();
        return getTopCellEdge(row) + textHeight;
    }

    // ---------------------------------------------------------------------------
    // Generates a random color by random number selection.
    public Color getRandomColor()
    {
        Color fire = Color.rgb(255,119,0);
        Color grass = Color.rgb(52, 140, 49);
        Color burnt = Color.rgb(254, 222, 23);
        int randomNum = new Random().nextInt(3);
        if (randomNum == 1){
         return fire;
        }else if (randomNum == 2){
         return burnt;
        }else{
         return grass;
        }
    }
    
    // ---------------------------------------------------------------------------
    // Generates a random wind direction by random number selection.
    public String getRandomWind()
    {
        String north = "N";
        String south = "S";
        String east = "E";
        String west = "W";
        int ranNum = new Random().nextInt(4);
        if (ranNum == 1){
         return north;
        }else if (ranNum == 2){
         return south;
        }else if (ranNum == 3){
         return east;
        }else{
         return west;
        }
    }

    // ---------------------------------------------------------------------------
    // Various set/get actions required for working grid

    public int getRowBurned(double x)
    {
        return (int) Math.floor(x / SCENE_HEIGHT * NUM_ROWS);
    }

    public int getColumnBurned(double y)
    {
        return (int) Math.floor(y / SCENE_WIDTH * NUM_COLS);
    }

    public int getLeftCellEdge(int column)
    {
        return (SCENE_WIDTH / NUM_COLS) * column;
    }

    public int getTopCellEdge(int row)
    {
        return (SCENE_HEIGHT / NUM_ROWS) * row;
    }

    public int getCellWidth()
    {
        return SCENE_WIDTH / NUM_COLS;
    }

    public int getCellHeight()
    {
        return SCENE_HEIGHT / NUM_ROWS;
    }

}
