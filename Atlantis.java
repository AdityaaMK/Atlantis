import javafx.animation.AnimationTimer; //--module-path $PATH_TO_FX --add-modules javafx.controls
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.util.ArrayList;

	public class Atlantis extends Application implements EventHandler<InputEvent> {

		GraphicsContext gc;
		GraphicsContext gc2;
		Canvas cs;
		Canvas cs2;
		Scene sn, sn2;
		AnimateObjects an;
		Image bt;
		Image hs;
		Image bg;
		Image ss1;
		Image ss2;
		Image t1;
		Image t2;
		Image t3;
		Tower tr1;
		Tower tr2;
		Tower tr3;
		ArrayList<Alien> aliens;
		ArrayList<GameObject> totList;
		ArrayList<GameObject> alienShots;
		boolean inSession;
		int x = 0;
		int y = 0;
		int numAliens = 0;
		int score = 0;

		public static void main(String[] args) {
			launch();
		}

		public void start(Stage stage) {
			stage.setTitle("Atlantis");
			aliens = new ArrayList<Alien>();
			totList = new ArrayList<GameObject>();
			alienShots = new ArrayList<GameObject>();
			Group rt = new Group();
			Group rt2 = new Group();
			cs = new Canvas(800, 450);
			cs2 = new Canvas(800, 450);
			rt.getChildren().add(cs);
			rt2.getChildren().add(cs2);
			Scene sn = new Scene(rt);
			Scene sn2 = new Scene(rt2);
			sn.addEventHandler(KeyEvent.KEY_PRESSED,this);
			sn2.addEventHandler(KeyEvent.KEY_PRESSED,this);
			sn2.addEventHandler(KeyEvent.KEY_RELEASED,this);
			stage.setScene(sn);

			Button bn = new Button("Press to Start Game");
			bn.setTranslateX(288);
			bn.setTranslateY(380);
			bn.setPrefSize(230, 35);
			bn.setStyle("-fx-background-color: #E76B03; ");
			rt.getChildren().add(bn);

			bn.setOnAction(click -> {
				inSession = true;
				stage.setScene(sn2);
			});

			gc = cs.getGraphicsContext2D();
			gc2 = cs2.getGraphicsContext2D();
			hs = new Image("HomeScreen.jpg");
			bg = new Image("Atlantis.png");
			ss1 = new Image("ss1.png");
			ss2 = new Image("ss2.png");
			t1 = new Image("t1.png");
			t2 = new Image("t2.png");
			t3 = new Image("t3.png");
			bt = new Image("bullet.png");
			gc.drawImage(hs, 0, 0);
			tr1 = new Tower(11, 263, t1);
			tr2 = new Tower(364, 233, t2);
			tr3 = new Tower(757, 236, t3);
			gc2.drawImage(bg, 0, 0);
			gc2.drawImage(tr1.getImage(), tr1.getX(), tr1.getY());
			gc2.drawImage(tr2.getImage(), tr2.getX(), tr2.getY());
			gc2.drawImage(tr3.getImage(), tr3.getX(), tr3.getY());
			totList.add(tr1);
			totList.add(tr2);
			totList.add(tr3);
			an = new AnimateObjects();
			an.start();
			stage.show();
		}

		public void handle(final InputEvent event) {
			if (((KeyEvent) event).getCode() == KeyCode.LEFT && tr1.isAlive())
				totList.add(new GameObject(tr1.getX(), tr1.getY(), bt));
			if (((KeyEvent) event).getCode() == KeyCode.RIGHT && tr2.isAlive())
				totList.add(new GameObject(tr3.getX(), tr3.getY(), bt));
			if (((KeyEvent) event).getCode() == KeyCode.UP && tr3.isAlive())
				totList.add(new GameObject(tr2.getX(), tr2.getY(), bt));
		}

		public class AnimateObjects extends AnimationTimer {
			public void handle(long now) {
				gc2.clearRect(0, 0, cs2.getWidth(), cs2.getHeight());
				gc2.drawImage(bg, 0, 0);

				gc2.setFill(Color.BLACK); //Fills the text in yellow
				gc2.setStroke(Color.BLACK); //Changes the outline the black
				gc2.setLineWidth(5); //How big the black lines will be
				Font font1 = Font.font("Arial", FontWeight.NORMAL, 35);
				gc2.setFont(font1);
				gc2.fillText("Score: " + score + " ", 310, 415); //draws the yellow part of the text
				gc2.strokeText("Score: " + score + " ", 310, 415); //draws the outline part of the text

				for(int i = 0; i < totList.size(); i++) {
					if(!(totList.get(i).isAlive())) {
						totList.remove(i);
					}
				}

				for(int i = 0; i < aliens.size(); i++) {
					if(!(aliens.get(i).isAlive())) {
						aliens.remove(i);
					}
				}

				if (numAliens == 0) {
					if (score < 1000) {
						int a = (int) (Math.random() * 3) + 1;
						/*if (a == 1) {
							aliens.add(new Alien((int) (Math.random() * 150) + 820, (int) (Math.random() * 50) + 5, ss1));
							numAliens++;
						} else if (a == 2) {
							aliens.add(new Alien((int) (Math.random() * -150) - 20, (int) (Math.random() * 50) + 5, ss2));
							numAliens++;
						} else {
						 */
						int x = (int) (Math.random() * -150) - 20;
						int y = (int) (Math.random() * 50) + 5;
						aliens.add(new Alien(x, y, ss2));
						alienShots.add(new GameObject(x, y, bt));
						numAliens++;
					}
				} else if (score >= 1000) {
					int i = (int) (Math.random() * 3) + 1;
					for (int a = 0; a < i; a++) {
						int b = (int) (Math.random() * 10) + 1;
							/*if (b == 1) {
								aliens.add(new Alien((int) (Math.random() * 150) + 820, (int) (Math.random() * 50) + 5, ss1));
								numAliens++;
							} else if (b == 2) {
								aliens.add(new Alien((int) (Math.random() * -150) - 20, (int) (Math.random() * 50) + 5, ss2));
								numAliens++;
							} else {*/
						int x = (int) (Math.random() * -150) - 20;
						int y = (int) (Math.random() * 50) + 5;
						aliens.add(new Alien(x, y, ss2));
						alienShots.add(new GameObject(x, y, bt));
						numAliens++;
						//}
					}
				}

				for (int i = 0; i < numAliens; i++) {
					if (score < 1000) {
						if (aliens.get(i).getImage() == ss1) {
							int x = ((int) (Math.random() * -5) - 1);
							aliens.get(i).setVelX(x);
							alienShots.get(i).setVelX(x);
						} else if (aliens.get(i).getImage() == ss2) {
							int x = ((int) (Math.random() * 5) + 1);
							aliens.get(i).setVelX(x);
							alienShots.get(i).setVelX(x);
						}
						aliens.get(i).setX(aliens.get(i).getVelX());
						alienShots.get(i).setX(alienShots.get(i).getVelX());
						gc2.drawImage(aliens.get(i).getImage(), aliens.get(i).getX(), aliens.get(i).getY());
						gc2.drawImage(alienShots.get(i).getImage(), alienShots.get(i).getX(), alienShots.get(i).getY());
					} else if (score >= 1000) {
						if (aliens.get(i).getImage() == ss1) {
							int x = ((int) (Math.random() * -5) - 1);
							aliens.get(i).setVelX(x);
							alienShots.get(i).setVelX(x);
						} else if (aliens.get(i).getImage() == ss2) {
							int x = ((int) (Math.random() * 5) + 1);
							aliens.get(i).setVelX(x);
							alienShots.get(i).setVelX(x);
						}
						aliens.get(i).setX(aliens.get(i).getVelX());
						alienShots.get(i).setX(alienShots.get(i).getVelX());
						gc2.drawImage(aliens.get(i).getImage(), aliens.get(i).getX(), aliens.get(i).getY());
						gc2.drawImage(alienShots.get(i).getImage(), alienShots.get(i).getX(), alienShots.get(i).getY());
					}
				}

				for (GameObject i : totList) {
					if (i instanceof Tower) {
						gc2.drawImage(i.getImage(), i.getX(), i.getY());
					} else {
						if (!(i.isLaunched())) {
							if (i.getX() == tr1.getX() && i.getY() == tr1.getY() && tr1.isAlive()) {
								i.setVelX(7);
								i.setVelY(-7);
								i.changeLaunched();
							} else if (i.getX() == tr2.getX() && i.getY() == tr2.getY() && tr2.isAlive()) {
								i.setVelY(-7);
								i.changeLaunched();
							} else if (i.getX() == tr3.getX() && i.getY() == tr3.getY() && tr3.isAlive()) {
								i.setVelX(-7);
								i.setVelY(-7);
								i.changeLaunched();
							}
						}
						i.setX(i.getVelX());
						i.setY(i.getVelY());
						gc2.drawImage(i.getImage(), i.getX(), i.getY());
					}
						for (int c = 0; c < numAliens; c++) {
							if (!(i instanceof Tower)) {
								if (i.bounds().intersects(aliens.get(c).bounds())) {
									score += 100;
									aliens.remove(c);
									alienShots.get(c).changeToDead();
									alienShots.remove(c);
									numAliens--;
								}
							}
						}
					}

				for (GameObject c : alienShots) {
					for (Alien e : aliens) {
						//	if (e.getX() > 10 && e.getX() < 790) {
						if (!(c.isLaunched())) {
							if (c.getX() == e.getX() && c.getY() == e.getY()) {
								c.setVelX(10);
								c.setVelY(10);
								c.changeLaunched();
							}
						}
						c.setX(c.getVelX());
						c.setY(c.getVelY());
						gc2.drawImage(c.getImage(), c.getX(), c.getY());
						//	}
					}
				}

				for (GameObject c : alienShots) {
					if (c.bounds().intersects(tr1.bounds())) {
						tr1.changeToDead();
						totList.remove(tr1);
						c.changeToDead();
					}
					if (c.bounds().intersects(tr2.bounds())) {
						tr2.changeToDead();
						totList.remove(tr2);
						c.changeToDead();
					}
					if (c.bounds().intersects(tr3.bounds())) {
						tr3.changeToDead();
						totList.remove(tr3);
						c.changeToDead();
					}
				}

				for (Alien e : aliens) {
					if (e.getX() < -200) {
						e.setX(1200);
					}
					if (e.getX() > 1000) {
						e.setX(-1200);
					}
				}

				if (!tr1.isAlive() && !tr2.isAlive() && !tr3.isAlive()) {
					gc2.setFill(Color.YELLOW); //Fills the text in yellow
					gc2.setStroke(Color.BLACK); //Changes the outline the black
					gc2.setLineWidth(1); //How big the black lines will be
					Font font = Font.font("Arial", FontWeight.NORMAL, 48);
					gc2.setFont(font);
					gc2.fillText("Game Over", 100, 50); //draws the yellow part of the text
					gc2.strokeText("Game Over", 100, 50); //draws the outline part of the text
				}
			}
		}
	}

