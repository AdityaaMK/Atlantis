import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

	public class Demo extends Application implements EventHandler<InputEvent>{

		GraphicsContext gc;
		AnimateObjects animate;
		Canvas canvas;
		Image trooper;
		int x = 0;
		int y = 0;

		public static void main(String[] args){
			launch();
		}

		public void start(Stage stage){
			stage.setTitle("Final Project Title");
			Group root = new Group();
			canvas = new Canvas(800, 400);
			root.getChildren().add(canvas);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			gc = canvas.getGraphicsContext2D();
			trooper = new Image("trooper.jpg");
			gc.drawImage( trooper, 180, 100 );
			animate = new AnimateObjects();
			animate.start();
			stage.show();
			scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
			URL resource = getClass().getResource("test.wav");
			AudioClip clip = new AudioClip(resource.toString());
			clip.play();
		}

		public void handle(final InputEvent event){
			if (((KeyEvent)event).getCode() == KeyCode.LEFT )
					x-=1;
			if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
					x+=1;
			if (((KeyEvent)event).getCode() == KeyCode.UP )
					y-=1;
			if (((KeyEvent)event).getCode() == KeyCode.DOWN )
					y+=1;
		}

		public class AnimateObjects extends AnimationTimer{
			public void handle(long now){
				if (x>-50) {
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					gc.drawImage(trooper, 180+x, 180+y);
					Rectangle2D rect1 = new Rectangle2D( 400,100,100,100 );
					gc.fillRect(400,100,100,100);
					Rectangle2D rect2 = new Rectangle2D(180+x, 100, trooper.getWidth(), trooper.getHeight());
					if (rect1.intersects(rect2))
						System.out.println("HIT");
				}
				else{
					gc.setFill( Color.YELLOW); //Fills the text in yellow
					gc.setStroke( Color.BLACK ); //Changes the outline the black
					gc.setLineWidth(1); //How big the black lines will be
					Font font = Font.font( "Arial", FontWeight.NORMAL, 48 );
					gc.setFont( font );
					gc.fillText( "Game Over", 100, 50 ); //draws the yellow part of the text
					gc.strokeText( "Game Over", 100, 50 ); //draws the outline part of the text
				}
			}
		}

	}