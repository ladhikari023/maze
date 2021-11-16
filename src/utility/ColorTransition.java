package utility;

import javafx.scene.paint.Color;

import java.util.Random;

public class ColorTransition {
    private final Color[] COLORS = {
            Color.PINK,
            Color.GREY,
            Color.RED,
            Color.ORANGE,
            Color.GREEN,
            Color.YELLOWGREEN,
            Color.YELLOW,
            Color.GREEN,
            Color.INDIGO
    };

    // index into the COLORS array
    private int currentTransition;
    // values for current color
    private double r;
    private double g;
    private double b;
    private Random rand;

    public ColorTransition() {
        rand = new Random();
        this.currentTransition = rand.nextInt(9);
        int color = rand.nextInt(9);
        this.r = Math.round(COLORS[color].getRed()*256);
        this.b = Math.round(COLORS[color].getBlue()*256);
        this.r = Math.round(COLORS[color].getRed()*256);
    }

    public ColorTransition(Color c) {
        this.setColor(c);
    }

    public void transition() {
        double desiredRed = Math.round(
                COLORS[currentTransition].getRed() * 255
        );
        double desiredBlue = Math.round(
                COLORS[currentTransition].getBlue() * 255
        );
        double desiredGreen = Math.round(
                COLORS[currentTransition].getGreen() * 255
        );
        // stores the number of RGB values that are equal to the desired color
        // for example, if the red is equal but not the blue and green, it is 1.
        // if it is 3 by the end of this method, we move onto the next color
        int equals = 0;

        if (this.r > desiredRed) {
            this.r--;
        } else if (this.r < desiredRed) {
            this.r++;
        } else {
            equals++;
        }

        if (this.b > desiredBlue) {
            this.b--;
        } else if (this.b < desiredBlue) {
            this.b++;
        } else {
            equals++;
        }

        if (this.g > desiredGreen) {
            this.g--;
        } else if (this.g < desiredGreen) {
            this.g++;
        } else {
            equals++;
        }

        if (equals == 3) {
            currentTransition++;
            currentTransition %= 9;
        }
    }

    public Color getColor() {
        return Color.rgb((int) this.r, (int) this.b, (int) this.g);
    }

    public void setColor(Color c) {
        this.r = Math.round(c.getRed() * 255);
        this.b = Math.round(c.getBlue() * 255);
        this.g = Math.round(c.getGreen() * 255);
    }
}
