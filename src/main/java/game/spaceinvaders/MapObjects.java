package game.spaceinvaders;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MapObjects implements EntityFactory {

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data ){
        Image imagen = new Image(SpaceInvadersApplication.class.getResource("meteors.png").toExternalForm());
        ImageView view = new ImageView(imagen);
        view.setFitWidth(40);
        view.setFitHeight(40);
        return FXGL.entityBuilder(data)
                .type(EntityType.ENEMY).
                viewWithBBox(view).
                collidable().
                with(new ProjectileComponent( new Point2D(-1,0), FXGLMath.random(30,120)))
                .build();
    }
    @Spawns("projectile")
    public Entity newProjectile(SpawnData data){
        Image imagen = new Image(SpaceInvadersApplication.class.getResource("missil.png").toExternalForm());
        ImageView view = new ImageView(imagen);
        view.setFitWidth(30);
        view.setFitHeight(10);
        return FXGL.entityBuilder(data)
                .type(EntityType.PROJECTILE)
                .viewWithBBox(view)
                .collidable()
                .with(new ProjectileComponent(new Point2D(1,0),700))
                .build();
    }
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        Image imagen = new Image(SpaceInvadersApplication.class.getResource("ship.png").toExternalForm());
        ImageView view = new ImageView(imagen);
        view.setFitWidth(40);
        view.setFitHeight(40);
        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox(view)
                .collidable()
                .build();

    }
}
