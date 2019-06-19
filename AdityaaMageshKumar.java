import javafx.animation.AnimationTimer; //--module-path $PATH_TO_FX --add-modules javafx.controls,javafx.media
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
import javafx.scene.media.AudioClip;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.util.ArrayList;

	public class AdityaaMageshKumar extends Application implements EventHandler<InputEvent> {

		GraphicsContext gc, gc2, gc3;
		Canvas cs, cs2, cs3;
		Scene sn, sn2, sn3;
		AnimateObjects an;
		Image bt;
		Image hs;
		Image bg;
		Image ss1;
		Image ss2;
		Image t1;
		Image t2;
		Image t3;
		Image ex;
		Image mi;
		Image go;
		Tower tr1;
		Tower tr2;
		Tower tr3;
		ArrayList<Alien> aliens;
		ArrayList<GameObject> totList;
		ArrayList<GameObject> alienShots;
		ArrayList<GameObject> explosions;
		boolean inSession;
		int x = 0;
		int y = 0;
		int numAliens = 0;
		int score = 0;
		int dp = 0;
		int delay = 17;
		int resp = 0;
		boolean s;
		URL r1;
		AudioClip clip;
		URL r2;
		AudioClip clip2;
		URL r3;
		AudioClip clip3;
		String wave = "ONE";
		ArrayList<Tower> subTowers;

		public static void main(String[] args) {

			launch();
		}

		public void start(Stage stage) {
			s=true;
			inSession = true;
			stage.setTitle("Atlantis");
			dp = (int) (Math.random() * 790) + 10;
			subTowers = new ArrayList<Tower>();
			explosions = new ArrayList<GameObject>();
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

			Button bn = new Button("Press to Defend Atlantis!");
			bn.setTranslateX(170);
			bn.setTranslateY(380);
			bn.setPrefSize(500, 45);
			bn.setStyle("-fx-background-color: #00ffff; ");
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
			ex = new Image("explosion.png");
			mi = new Image("missile.jpg");
			go = new Image("GameOver.png");
			gc.drawImage(hs, 0, 0);
			tr1 = new Tower(11, 263, t1);
			tr2 = new Tower(364, 233, t2);
			tr3 = new Tower(757, 236, t3);
			r1 = getClass().getResource("laser.wav");
			clip = new AudioClip(r1.toString());
			r2 = getClass().getResource("atari_boom.wav");
			clip2 = new AudioClip(r2.toString());
			r3 = getClass().getResource("kaboom.wav");
			clip3 = new AudioClip(r3.toString());
			gc2.drawImage(bg, 0, 0);
			gc2.drawImage(tr1.getImage(), tr1.getX(), tr1.getY());
			gc2.drawImage(tr2.getImage(), tr2.getX(), tr2.getY());
			gc2.drawImage(tr3.getImage(), tr3.getX(), tr3.getY());
			totList.add(tr1);
			totList.add(tr2);
			totList.add(tr3);

			Group rt3 = new Group();
			cs3 = new Canvas(800, 450);
			rt3.getChildren().add(cs3);
			Scene sn3 = new Scene(rt3);
			sn3.addEventHandler(KeyEvent.KEY_PRESSED,this);
			sn3.addEventHandler(KeyEvent.KEY_RELEASED,this);
			gc3 = cs3.getGraphicsContext2D();
			if(!inSession){
				stage.setScene(sn3);
			}

			an = new AnimateObjects();
			an.start();
			stage.show();
		}

		public void handle(final InputEvent event) {
			if (((KeyEvent) event).getCode() == KeyCode.LEFT && tr1.isAlive()&&s) {
				totList.add(new GameObject(tr1.getX(), tr1.getY(), bt));
				delay = 17;
				s = false;
				clip.play();
			}
			if (((KeyEvent) event).getCode() == KeyCode.UP && tr2.isAlive()&&s) {
				totList.add(new GameObject(tr2.getX(), tr2.getY(), bt));
				delay = 17;
				s = false;
				clip.play();
			}
			if (((KeyEvent) event).getCode() == KeyCode.RIGHT && tr3.isAlive()&&s) {
				totList.add(new GameObject(tr3.getX(), tr3.getY(), bt));
				delay = 17;
				s = false;
				clip.play();
			}
			if (((KeyEvent) event).getCode() == KeyCode.SPACE) {
				gc2.clearRect(0, 0, cs2.getWidth(), cs2.getHeight());
				tr1.switchAlive();
				tr2.switchAlive();
				tr3.switchAlive();
				totList.add(tr1);
				totList.add(tr2);
				totList.add(tr3);
				score = 0;
				wave = "ONE";
				for(Alien e: aliens) {
					e.switchDead();
				}
				for(int i=0; i<aliens.size(); i++) {
					aliens.remove(i);
				}
				numAliens = 0;
				inSession = true;
			}
		}

		public class AnimateObjects extends AnimationTimer {
			public void handle(long now) {
				if(inSession) {
					gc2.clearRect(0, 0, cs2.getWidth(), cs2.getHeight());
					gc2.drawImage(bg, 0, 0);

					gc2.setFill(Color.BLACK); //Fills the text in yellow
					gc2.setStroke(Color.BLACK); //Changes the outline the black
					gc2.setLineWidth(5); //How big the black lines will be
					Font font1 = Font.font("Arial", FontWeight.NORMAL, 35);
					gc2.setFont(font1);
					gc2.fillText("Score: " + score + " ", 280, 415); //draws the yellow part of the text
					gc2.strokeText("Score: " + score + " ", 280, 415); //draws the outline part of the text

					gc2.setFill(Color.BLUE); //Fills the text in yellow
					gc2.setStroke(Color.BLUE); //Changes the outline the black
					gc2.setLineWidth(5); //How big the black lines will be
					Font font2 = Font.font("Arial", FontWeight.NORMAL, 35);
					gc2.setFont(font2);
					gc2.fillText("Level: " + wave + " ", 565, 415); //draws the yellow part of the text
					gc2.strokeText("Level: " + wave + " ", 565, 415); //draws the outline part of the text

					if(delay==0)
						s = true;
					else
						delay--;

					for (int i = 0; i < totList.size(); i++) {
						if (!(totList.get(i).isAlive())) {
							totList.remove(i);
						}
					}

					for (int i = 0; i < alienShots.size(); i++) {
						if (!(alienShots.get(i).isAlive())) {
							alienShots.remove(i);
						}
					}

					for (int i = 0; i < aliens.size(); i++) {
						if (!(aliens.get(i).isAlive())) {
							aliens.remove(i);
						}
					}

					for (GameObject c : alienShots) {
						if (!c.isShot() && (c.getX()>10&&c.getX()<=364) && tr1.isAlive()) {
							c.setVelY(2);
							c.setVelX(0);
							c.switchShot();
						}
						c.setY(c.getVelY());
						gc2.drawImage(c.getImage(), c.getX(), c.getY());
					}

					for(GameObject c: alienShots) {
						if (!c.isShot() && (c.getX()>364&&c.getX()<=757) && tr2.isAlive()) {
							c.setVelY(2);
							c.setVelX(0);
							c.switchShot();
						}
						c.setY(c.getVelY());
						gc2.drawImage(c.getImage(), c.getX(), c.getY());
					}

					for(GameObject c: alienShots) {
						if (!c.isShot() && (c.getX()>757&&c.getX()<=770) && tr3.isAlive()) {
							c.setVelY(2);
							c.setVelX(0);
							c.switchShot();
						}
						c.setY(c.getVelY());
						gc2.drawImage(c.getImage(), c.getX(), c.getY());
					}

					if (numAliens == 0 && score < 1000) {
						int a = (int) (Math.random() * 2) + 1;
						if (a == 1) {
							Alien alien = new Alien((int) (Math.random() * 150) + 820, 25, ss1);
							int xSpeed = ((int) (Math.random() * -5) - 2);
							alien.setVelX(xSpeed);
							aliens.add(alien);
							numAliens++;
						} else if (a == 2) {
							Alien alien = new Alien((int) (Math.random() * -150) - 20, 25, ss2);
							int xSpeed = ((int) (Math.random() * 5) + 2);
							alien.setVelX(xSpeed);
							aliens.add(alien);
							numAliens++;
						}
					}

					else if (numAliens == 0 && score >= 1000 && score < 2000) {
						wave = "TWO";
						int yP = 0;
						for (int a = 0; a < 2; a++) {
							yP+=27;
							int b = (int) (Math.random() * 2) + 1;
							if (b == 1) {
								Alien alien = new Alien((int) (Math.random() * 150) + 820, 50+yP, ss1);
								int xSpeed = ((int) (Math.random() * -5) - 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							} else if (b == 2) {
								Alien alien = new Alien((int) (Math.random() * -150) - 20, 50+yP, ss2);
								int xSpeed = ((int) (Math.random() * 5) + 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							}
						}
					}

					else if(score>=2000 && score<3000 && numAliens==0) {
						wave = "THREE";
						int yP = 0;
						for (int a = 0; a < 3; a++) {
							yP += 27;
							int c = (int) (Math.random() * 2) + 1;
							if (c == 1) {
								Alien alien = new Alien((int) (Math.random() * 150) + 820, 100+yP, ss1);
								int xSpeed = ((int) (Math.random() * -5) - 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							} else if (c == 2) {
								Alien alien = new Alien((int) (Math.random() * -150) - 20, 100+yP, ss2);
								int xSpeed = ((int) (Math.random() * 5) + 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							}
						}
					}

					else if (score >= 3000 && numAliens == 0) {
						wave = "DEATH";
						int yP = 0;
						for (int a = 0; a < 5; a++) {
							yP += 27;
							if (a==0||a==3) {
								Alien alien = new Alien((int) (Math.random() * 150) + 820, 50+yP, ss1);
								int xSpeed = ((int) (Math.random() * -5) - 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							} else if(a==1||a==4){
								Alien alien = new Alien((int) (Math.random() * -150) - 20, 50+yP, ss2);
								int xSpeed = ((int) (Math.random() * 5) + 3);
								alien.setVelX(xSpeed);
								aliens.add(alien);
								numAliens++;
							} else if (a == 2) {
								int f = (int) (Math.random() * 2) + 1;
								if (f == 1) {
									int x = (int) (Math.random() * -150) - 20;
									int y = 50 + yP;
									int xSpeed = (9);
									Alien alien = new Alien(x, y, ss2);
									GameObject bb = new GameObject(x, y, mi);
									bb.setVelX(xSpeed);
									alien.setVelX(xSpeed);
									aliens.add(alien);
									alienShots.add(bb);
									numAliens++;
								} else if(f == 2 && tr3.isAlive()) {
									int x = (int) (Math.random() * 150) + 820;
									int y = 50 + yP;
									int xSpeed = (-9);
									Alien alien = new Alien(x, y, ss1);
									GameObject bb = new GameObject(x, y, mi);
									bb.setVelX(xSpeed);
									alien.setVelX(xSpeed);
									aliens.add(alien);
									alienShots.add(bb);
									numAliens++;
								}
							}
						}
					}

					for (Alien e : aliens) {
						e.setX(e.getVelX());
						gc2.drawImage(e.getImage(), e.getX(), e.getY());
					}

					for (GameObject c : alienShots) {
						c.setX(c.getVelX());
						gc2.drawImage(c.getImage(), c.getX(), c.getY());
					}

					for (GameObject i : totList) {
						if (i instanceof Tower) {
							gc2.drawImage(i.getImage(), i.getX(), i.getY());
						} else {
							if (!(i.isShot())) {
								if (i.getX() == (tr1.getX()) && i.getY() == tr1.getY() && tr1.isAlive()) {
									i.setVelX(5);
									i.setVelY(-5);
									i.setX(tr1.getX() + 5);
									i.switchShot();
								} else if (i.getX() == (tr2.getX()) && i.getY() == tr2.getY() && tr2.isAlive()) {
									i.setVelY(-5);
									i.setX(tr1.getX());
									i.switchShot();
								} else if (i.getX() == tr3.getX() && i.getY() == tr3.getY() && tr3.isAlive()) {
									i.setVelX(-5);
									i.setVelY(-5);
									i.switchShot();
								}
							}
							i.setX(i.getVelX());
							i.setY(i.getVelY());
							gc2.drawImage(i.getImage(), i.getX(), i.getY());
						}
						for (Alien e: aliens) {
							if (!(i instanceof Tower)) {
								if (i.bounds().intersects(e.bounds())) {
									score += 200;
									GameObject explo = new GameObject(e.getX(), e.getY(), ex);
									explosions.add(explo);
									gc2.drawImage(explo.getImage(), explo.getX(), explo.getY());
									i.switchDead();
									e.switchDead();
									clip2.play();
									for(GameObject c: alienShots) {
										if (e.getVelX() == c.getVelX()) {
											c.switchDead();
											score += 300;
										}
									}
									explosions.remove(explo);
									numAliens--;
									if(wave.equals("DEATH"))
										resp++;
								}
							}
						}
					}

					if(resp==20) {
						if(!tr2.isAlive()){
							tr2.switchAlive();
							totList.add(tr2);
							gc2.drawImage(tr2.getImage(), tr2.getX(), tr2.getY());
							resp = 0;
						} else if (!tr1.isAlive()) {
							tr1.switchAlive();
							totList.add(tr1);
							gc2.drawImage(tr1.getImage(), tr1.getX(), tr1.getY());
							resp = 0;
						} else if (!tr3.isAlive()) {
							tr3.switchAlive();
							totList.add(tr3);
							gc2.drawImage(tr3.getImage(), tr3.getX(), tr3.getY());
							resp = 0;
						}
					}

					for (int i=0; i<alienShots.size(); i++) {
						if (alienShots.get(i).bounds().intersects(tr1.bounds())) {
							tr1.switchDead();
							totList.remove(tr1);
							alienShots.remove(i);
							GameObject explo = new GameObject(tr1.getX(), tr1.getY(), ex);
							explosions.add(explo);
							gc2.drawImage(explo.getImage(), explo.getX(), explo.getY());
							clip3.play();
							explosions.remove(explo);
						}
						else if (alienShots.get(i).bounds().intersects(tr2.bounds())) {
							tr2.switchDead();
							totList.remove(tr2);
							alienShots.remove(i);
							GameObject explo = new GameObject(tr2.getX(), tr2.getY(), ex);
							explosions.add(explo);
							gc2.drawImage(explo.getImage(), explo.getX(), explo.getY());
							clip3.play();
							explosions.remove(explo);
						}
						else if (alienShots.get(i).bounds().intersects(tr3.bounds())) {
							tr3.switchDead();
							totList.remove(tr3);
							alienShots.remove(i);
							GameObject explo = new GameObject(tr3.getX(), tr3.getY(), ex);
							explosions.add(explo);
							gc2.drawImage(explo.getImage(), explo.getX(), explo.getY());
							clip3.play();
							explosions.remove(explo);
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

					for (GameObject c : alienShots) {
						if (c.getX() < -200) {
							c.setX(1200);
						}
						if (c.getX() > 1000) {
							c.setX(-1200);
						}
					}
					if (!tr1.isAlive() && !tr2.isAlive() && !tr3.isAlive()) {
						inSession = false;
					}
				}
				else{
					gc2.clearRect(0, 0, cs2.getWidth(), cs2.getHeight());
					gc2.drawImage(go, 0, 0);
					gc2.setFill(Color.YELLOW);
					gc2.setStroke(Color.YELLOW); //Changes the outline the black
					gc2.setLineWidth(1); //How big the black lines will be
					Font font = Font.font("Arial", FontWeight.NORMAL, 48);
					gc2.setFont(font);
					gc2.fillText("Press Space to Try Again!", 170, 100); //draws the yellow part of the text
					gc2.strokeText("Press Space to Try Again", 170, 100); //draws the outline part of the text
				}
			}
		}
	}

