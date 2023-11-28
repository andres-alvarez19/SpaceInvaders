package game.spaceinvaders;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SpaceInvadersApplication extends GameApplication {
    private Entity player;
    private static int points = 0;
    private static int timer;
    private Text puntosText ;
    private Text timeText ;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Space Invaders");
        settings.setVersion("0.2");
        settings.setFullScreenAllowed(false);
        settings.setMainMenuEnabled(true);
        settings.setAppIcon("icons/logo.png");
    }
    @Override
    protected void initGame() {
        Image image = new Image(Objects.requireNonNull(SpaceInvadersApplication.class.getResource("background.jpg")).toExternalForm());
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(800,600,true,true, FXGL.getInput().getRegisterInput(), FXGL.getInput().getRegisterInput())));
        FXGL.getGameScene().getRoot().setBackground(background);
        FXGL.getGameScene().setCursorInvisible();
        FXGL.getGameTimer().runAtInterval(()-> timer++,new Duration(1000));
        FXGL.getGameWorld().addEntityFactory(new SpaceInvadersFactory());
        spawn("player",0, (double) getAppHeight() /2);
        spawnEnemies();
    }
    @Override
    protected void initInput(){
        Player.inputs();
        FXGL.onKey(KeyCode.ESCAPE,"Pause", () -> getGameController().pauseEngine());

    }
    @Override
    protected void initUI() {
        timeText = GameUI.getTime();
        puntosText = GameUI.getPoints();
        FXGL.getGameScene().addUINode(puntosText);
        FXGL.getGameScene().addUINode(timeText);
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
            spawn("explosion",projectile.getX(),projectile.getY());
            deleteCollidedEntities(projectile,enemy);
            getGameTimer().runOnceAfter(() -> getGameWorld().getEntitiesByType(EntityType.EXPLOSION).forEach(Entity::removeFromWorld), Duration.seconds(0.1));
            points++;
        });
        onCollisionBegin(EntityType.SPECIAL, EntityType.ENEMY, (projectile, enemy) -> {
            List<Entity> reachMeteorite = new ArrayList<>();
            var enemies = getGameWorld().getEntitiesFiltered(e -> e.isType(EntityType.ENEMY));
            projectile.removeFromWorld();
            spawn("bigExplosion",projectile.getX(),projectile.getY());
            getGameTimer().runOnceAfter(() -> getGameWorld().getEntitiesByType(EntityType.EXPLOSION).forEach(Entity::removeFromWorld), Duration.seconds(0.1));
            for (Entity meteors : enemies) {
                if(Math.abs(meteors.getPosition().distance(projectile.getPosition())) <= 100 ){
                    reachMeteorite.add(meteors);
                }
            }
            for (Entity meteors:reachMeteorite) {
                meteors.removeFromWorld();
                points++;
            }
        });
    }
    @Override
    protected void onUpdate(double tpf) {
        refreshPlayer();
        setTexts();
        limitPlayerMovement(player);
        gameLogic();
    }
    private void refreshPlayer() {
        player = getGameWorld().getSingleton(EntityType.PLAYER);
    }
    private void setTexts() {
        timeText.setText(GameUI.getTimeText(timer));
        puntosText.setText(GameUI.getPointsText(points));
    }
    private static void limitPlayerMovement(Entity player) {
        double x = FXGLMath.clamp((float) player.getX(), (float) 0, (float) (getAppWidth() - player.getWidth()));
        double y = FXGLMath.clamp((float) player.getY(), (float) 0, (float) (getAppHeight() - player.getHeight()));
        player.setPosition(x, y);
    }
    private void gameLogic() {
        if(enemiesReachBase()){endGame();}
        if(playerColliesEnemy(player) != null){
            deleteCollidedEntities(player, Objects.requireNonNull(playerColliesEnemy(player)));
            endGame();
        }
    }
    private static boolean enemiesReachBase() {
        var enemiesReachBase = getGameWorld().getEntitiesFiltered(e -> e.isType(EntityType.ENEMY)&& e.getX() < 0);
        return !enemiesReachBase.isEmpty();
    }
    private static void endGame() {
        showMessage("Game Over",() -> getGameController().gotoMainMenu());
        points = 0;
        timer = 0;
    }
    private static Entity playerColliesEnemy(Entity player) {
        var enemies = getGameWorld().getEntitiesFiltered(e -> e.isType(EntityType.ENEMY));
        for (Entity enemy : enemies) {
            if (player.isColliding(enemy)){
                return enemy;
            }
        }
        return null;
    }

    private static void deleteCollidedEntities(Entity entity, Entity collidedEntity) {
        entity.removeFromWorld();
        collidedEntity.removeFromWorld();
    }
    public static void main(String[] args) {
        launch(args);
    }
}