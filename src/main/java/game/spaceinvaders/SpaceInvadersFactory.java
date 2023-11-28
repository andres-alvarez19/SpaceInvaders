package game.spaceinvaders;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class SpaceInvadersFactory implements EntityFactory {
    @Spawns("bigExplosion")
    public Entity showBigCollisionImage(SpawnData data) {
        Texture collisionImage = texture("explosion.png");
        collisionImage.setFitHeight(100);
        collisionImage.setFitWidth(100);
        return FXGL.entityBuilder(data)
                .type(EntityType.EXPLOSION)
                .viewWithBBox(collisionImage)
                .with(new OffscreenCleanComponent())
                .build();
    }
    @Spawns("explosion")
    public Entity showCollisionImage(SpawnData data) {
        Texture collisionImage = texture("explosion.png");
        collisionImage.setFitHeight(40);
        collisionImage.setFitWidth(40);
        return FXGL.entityBuilder(data)
                .type(EntityType.EXPLOSION)
                .viewWithBBox(collisionImage)
                .with(new OffscreenCleanComponent())
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data ){
        Image imagen = new Image(Objects.requireNonNull(SpaceInvadersApplication.class.getResource("meteors.png")).toExternalForm());
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
    @Spawns("specialAttack")
    public Entity newSpecialAttack(SpawnData data){
        Image imagen = new Image(Objects.requireNonNull(SpaceInvadersApplication.class.getResource("especial-missile.png")).toExternalForm());
        ImageView view = new ImageView(imagen);
        view.setFitWidth(40);
        view.setFitHeight(20);
        return FXGL.entityBuilder(data)
                .type(EntityType.SPECIAL)
                .viewWithBBox(view)
                .collidable()
                .with(new ProjectileComponent(new Point2D(1,0),700))
                .build();
    }
    @Spawns("projectile")
    public Entity newProjectile(SpawnData data){
        Image imagen = new Image(Objects.requireNonNull(SpaceInvadersApplication.class.getResource("missil.png")).toExternalForm());
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
        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox(Player.getView())
                .collidable()
                .build();

    }
}
