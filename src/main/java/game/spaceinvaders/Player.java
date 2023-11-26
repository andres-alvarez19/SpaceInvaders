package game.spaceinvaders;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class Player {
    private static final ImageView view = new ImageView(new Image(Objects.requireNonNull(SpaceInvadersApplication.class.getResource("ship.png")).toExternalForm()));
    private static final Point2D size = new Point2D(40,40);
    private static double speed = 4;
    private static boolean dash = false;
    private static String lastMovement ;
    private static final int DASH_VALUE = 30;
    public static void inputs() {
        FXGL.onBtnDown(MouseButton.PRIMARY, () -> {
            double x = getGameWorld().getSingleton(EntityType.PLAYER).getX();
            double y = getGameWorld().getSingleton(EntityType.PLAYER).getY();
            spawn("projectile", x, y + 12);
        });
        FXGL.onKeyDown(KeyCode.D, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateX(speed);
            lastMovement = "d";
        });

        FXGL.onKey(KeyCode.A, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateX(-speed);
            lastMovement = "a";
        });

        FXGL.onKey(KeyCode.W, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateY(-speed);
            lastMovement = "w";
        });

        FXGL.onKey(KeyCode.S, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateY(speed);
            lastMovement = "s";
        });
        FXGL.onKey(KeyCode.SPACE, () -> {
            if (!dash) {
                dash = true;
                switch (lastMovement) {
                    case "a" -> getGameWorld().getSingleton(EntityType.PLAYER).translateX(-DASH_VALUE);
                    case "d" -> getGameWorld().getSingleton(EntityType.PLAYER).translateX(DASH_VALUE);
                    case "w" -> getGameWorld().getSingleton(EntityType.PLAYER).translateY(DASH_VALUE);
                    case "s" -> getGameWorld().getSingleton(EntityType.PLAYER).translateY(-DASH_VALUE);
                }
            }
            dash = false;
        });
    }

    public static ImageView getView() {
        view.setFitWidth(size.getX());
        view.setFitHeight(size.getY());
        return view;
    }
}
