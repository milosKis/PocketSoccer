package com.example.nikolakis.pocketsoccer;

import android.graphics.Rect;

public class CollisionResolver {

    public static void rotate(Circle circle, double angle) {
        double xVelocity = circle.getxVelocity();
        double yVelocity = circle.getyVelocity();
        xVelocity = xVelocity * Math.cos(angle) - yVelocity * Math.sin(angle);
        yVelocity = circle.getxVelocity() * Math.sin(angle) + yVelocity * Math.cos(angle);
        circle.setxVelocity(xVelocity);
        circle.setyVelocity(yVelocity);
    }

    public static void resolveCollision(Circle c1, Circle c2) {
        double xVelocityDiff = c1.getxVelocity() - c2.getxVelocity();
        double yVelocityDiff = c1.getyVelocity() - c2.getyVelocity();
        double xDist = c2.getX() - c1.getX();
        double yDist = c2.getY() - c1.getY();

        if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) { // Prevent accidental overlap of particles
            double angle = -Math.atan2(yDist, xDist); // Grab angle between the two colliding particles
            double m1 = c1.getMass(); // Store mass in var for better readability in collision equation
            double m2 = c2.getMass();
            rotate(c1, angle); // Velocity before equation
            rotate(c2, angle);
            double v1x = c1.getxVelocity() * (m1 - m2) / (m1 + m2) + c2.getxVelocity()* 2 * m2 / (m1 + m2); // Velocity after 1d collision equation
            double v2x = c2.getxVelocity() * (m1 - m2) / (m1 + m2) + c1.getxVelocity() * 2 * m2 / (m1 + m2);
            c1.setxVelocity(v1x);
            c2.setxVelocity(v2x);
            rotate(c1, -angle); // Final velocity after rotating axis back to original location
            rotate(c2, -angle);
        }
    }

    public static void resolveCollision(Circle circle, Rect rect) {
        int x = circle.getX();
        int y = circle.getY();
        int radius = circle.getRadius();
        int xRight = Math.abs(x - rect.right);
        int xLeft = Math.abs(x - rect.left);
        int yTop = Math.abs(y - rect.top);
        int yBottom = Math.abs(y - rect.bottom);

        if (y >= rect.top && y <= rect.bottom && (xRight <= radius || xLeft <= radius)){
            if (xRight < xLeft) {
                circle.setxVelocity(circle.getxVelocity() * -1);
                circle.setX(rect.right + radius + 1);
            }
            else {
                circle.setxVelocity(circle.getxVelocity() * -1);
                circle.setX(rect.left - radius - 1);
            }
        }
        else if (x >= rect.left && x <= rect.right && (yTop <= radius || yBottom <= radius)) {
            if (yTop < yBottom) {
                circle.setyVelocity(circle.getyVelocity() * -1);
                circle.setY(rect.top - radius - 1);
            }
            else {
                circle.setyVelocity(circle.getyVelocity() * -1);
                circle.setY(rect.bottom + radius + 1);
            }
        }
    }

    public static boolean ballIsInRect(Circle circle, Rect rect) {
        int x = circle.getX();
        int y = circle.getY();
        return  ((x >= rect.left) && (x <= rect.right) && (y >= rect.top) && (y <= rect.bottom));
    }
}
