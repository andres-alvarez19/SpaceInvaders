module game.spaceinvaders {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    requires annotations;

    opens game.spaceinvaders to javafx.fxml;
    exports game.spaceinvaders;
}