package leapfx;

import javafx.animation.Transition;
import javafx.scene.layout.Region;
import javafx.util.Duration;

//  Animation to resize the height
public class ResizeHeightTranslation extends Transition {

    protected double heightDiff;
    protected double startHeight;
    protected double newHeight;
    protected Region region;
    
    public ResizeHeightTranslation(Duration duration, Region region, double newHeight) {
        setCycleDuration(duration);
        this.region = region;
        this.newHeight = newHeight;
        this.startHeight = region.getHeight();
        this.heightDiff = newHeight - startHeight;
    }
    
    @Override
    protected void interpolate(double fraction) {
        region.setPrefHeight(startHeight + (heightDiff * fraction));
    }
    
}
