package game.spaceinvaders;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameUI {
    private static final Text TIME = new Text();
    private static final Text POINTS = new Text();

    public static String getTimeText(int timer){
        return "Timer: " + Time.formatToTime(timer);
    }
    public static String getPointsText(int points){
        return "Points: "+ points;
    }
    public static Text getPoints() {
        POINTS.setStroke(Color.BLACK);
        POINTS.setFont(Font.font(24));
        POINTS.setTranslateX(670);
        POINTS.setTranslateY(40);
        return POINTS;
    }
    public static Text getTime() {
        TIME.setStroke(Color.BLACK);
        TIME.setFont(Font.font(24));
        TIME.setTranslateX(20);
        TIME.setTranslateY(40);
        return TIME;
    }
}
