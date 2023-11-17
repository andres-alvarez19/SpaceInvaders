package game.spaceinvaders;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SpaceInvadersApplication extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Space Invaders");
        settings.setVersion("0.2");
        settings.setFullScreenAllowed(false);
        settings.setMainMenuEnabled(true);
        settings.setAppIcon( "logo.png");


    }
    @Override
    protected void initGame() {
        Image imagen = new Image(SpaceInvadersApplication.class.getResource("fondo.jpg").toExternalForm());
        Background background = new Background(new BackgroundImage(imagen,
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(800,600,true,true, FXGL.getInput().getRegisterInput(), FXGL.getInput().getRegisterInput())));
        getGameScene().getRoot().setBackground(background);

        getGameWorld().addEntityFactory(new MapObjects());
        spawn("player",0, (double) getAppHeight() /2);
        spawnEnemies();
    }
    @Override
    protected void initInput(){
        double speed = 4;
        onBtnDown(MouseButton.PRIMARY, ()-> {
            double x = getGameWorld().getSingleton(EntityType.PLAYER).getX();
            double y = getGameWorld().getSingleton(EntityType.PLAYER).getY();
            spawn("projectile",x,y+12);
            return null;
        });
        onKey(KeyCode.D, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateX(speed);
            return null;
        });

        onKey(KeyCode.A, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateX(-speed);
            return null;
        });

        onKey(KeyCode.W, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateY(-speed);
            return null;
        });

        onKey(KeyCode.S, () -> {
            getGameWorld().getSingleton(EntityType.PLAYER).translateY(speed);
            return null;
        });
        onKey(KeyCode.ESCAPE, () -> {
            getGameController().pauseEngine();
            return null;
        });
    }
    protected void spawnEnemies(){
        FXGL.run(()->{
            double x = getAppWidth();
            double y = random(0,getAppHeight()-40);
            spawn("enemy",x,y);
        }, Duration.seconds(0.25));
    }
    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.PROJECTILE, EntityType.ENEMY, (projectile, enemy) -> {
            projectile.removeFromWorld();
            enemy.removeFromWorld();
        });
    }
    @Override
    protected void onUpdate(double tpf) {
        var enemiesReachBase = getGameWorld().getEntitiesFiltered(e -> e.isType(EntityType.ENEMY)&& e.getX() < 0);
        if(!enemiesReachBase.isEmpty()){
            showMessage("Game Over",() -> getGameController().gotoMainMenu());
        }
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        double x = player.getX();
        double y = player.getY();
        x = FXGLMath.clamp((float) player.getX(), (float) 0, (float) (getAppWidth() - player.getWidth()));
        y = FXGLMath.clamp((float) player.getY(), (float) 0, (float) (getAppHeight() - player.getHeight()));
        player.setPosition(x, y);

    }
    public static void main(String[] args) {
        launch(args);
    }
}