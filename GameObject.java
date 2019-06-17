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
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;


public class GameObject
{
    private int x;
    private int y;
    private int velX;
    private int velY;
    private Image image;
    private boolean isAlive;
    private boolean isShot;
    private int delay;

    public GameObject(int x, int y, Image image)
    {
        this.x = x;
        this.y = y;
        velX = 0;
        velY = 0;
        this.image = image;
        isAlive = true;
        isShot = false;
        delay = 60;
    }

    public Rectangle2D bounds()
    {

        return (new Rectangle2D (x, y, image.getWidth(), image.getHeight()));
    }

    public Image getImage()
    {

        return image;
    }

    public void setImage(Image image)
    {

        this.image = image;
    }

    public boolean isAlive()
    {

        return isAlive;
    }

    public void switchDead()
    {

        isAlive = false;
    }

    public void switchAlive()
    {

        isAlive = true;
    }

    public int getX()
    {

        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x += x;
    }
    public void setY(int y)
    {
        this.y += y;
    }

    public int getVelX()
    {
        return velX;
    }

    public int getVelY()
    {
        return velY;
    }

    public void setVelX(int velX)
    {
        this.velX = velX;
    }

    public void setVelY(int velY)
    {

        this.velY = velY;
    }

    public boolean isShot()
    {
        return isShot;
    }

    public void switchShot()
    {
        isShot = true;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int num)
    {
        delay = num;
    }
}