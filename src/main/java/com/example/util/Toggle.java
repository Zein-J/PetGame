package com.example.util;

    import javafx.animation.TranslateTransition;
    import javafx.scene.control.ToggleButton;
    import javafx.scene.layout.StackPane;
    import javafx.scene.shape.Circle;
    import javafx.scene.shape.Rectangle;
    import javafx.util.Duration;
    
    public class Toggle extends ToggleButton {
        private final Rectangle background;
        private final Circle thumb;
        private final TranslateTransition transition;
    
        public Toggle() {
            background = new Rectangle(52, 32);
            background.setArcWidth(32);
            background.setArcHeight(32);
            background.getStyleClass().add("toggle-background");
    
            thumb = new Circle(14);
            thumb.setTranslateX(16);
            thumb.getStyleClass().add("toggle-thumb");
    
            StackPane layout = new StackPane(background, thumb);
            setGraphic(layout);
    
            transition = new TranslateTransition(Duration.millis(200), thumb);
    
            updateState(false);
    
            selectedProperty().addListener((obs, oldVal, newVal) -> {
                updateState(newVal);
            });
    
            getStyleClass().add("ios-toggle");
        }
    
        private void updateState(boolean selected) {
            transition.stop();
            transition.setToX(selected ? 36 : 16);
            transition.play();
        }
    }
