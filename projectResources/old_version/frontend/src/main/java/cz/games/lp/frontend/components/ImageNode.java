package cz.games.lp.frontend.components;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;

public class ImageNode {

    @Getter
    private final ImageView imageView = new ImageView();
    private final ImageView tooltipView = new ImageView();

    public ImageNode(double width, double height) {
        this(width, height, 3 * width, 3 * height);
    }

    public ImageNode(double width, double height, double toolkitWidth, double toolkitHeight) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Tooltip tooltip = new Tooltip();
        tooltipView.setFitWidth(toolkitWidth);
        tooltipView.setFitHeight(toolkitHeight);
        tooltip.setGraphic(tooltipView);
        Tooltip.install(imageView, tooltip);
    }

    public void setImage(String imageName) {
        Image image;
        try (InputStream pictureInResources = getClass().getClassLoader().getResourceAsStream("img/" + imageName + ".png")) {
            image = new Image(Objects.requireNonNull(pictureInResources));
        } catch (Exception exp) {
            throw new IllegalArgumentException(exp);
        }
        imageView.setImage(image);
        tooltipView.setImage(image);
    }

}
