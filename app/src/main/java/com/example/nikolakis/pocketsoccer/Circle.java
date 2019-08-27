package com.example.nikolakis.pocketsoccer;

import android.graphics.Rect;

public class Circle {

    public static double MAX_SWIPE_LENGTH = 300;

    private int x, y, radius;
    private double a = 0.001, maxVelocity = 3, xVelocity, yVelocity, mass;

    public Circle(int x, int y, int radius, double mass, double maxVelocity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.maxVelocity = maxVelocity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCourseVectorAndVelocity(float x1, float y1, float x2, float y2, double swipeLength) {
        double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        xVelocity = maxVelocity * (x2 - x1) / distance * Math.min(1, swipeLength / MAX_SWIPE_LENGTH);
        yVelocity = maxVelocity * (y2 - y1) / distance * Math.min(1, swipeLength / MAX_SWIPE_LENGTH);
    }

    public void updatePositionAndVelocity(double time) {
        updateXAxisPositionAndVelocity(time);
        updateYAxisPositionAndVelocity(time);
    }

    private void updateXAxisPositionAndVelocity(double time) {
        if (xVelocity != 0) {
            double prevxVelocity = xVelocity;
            if (xVelocity > 0) {
                x += xVelocity * time;
                //x += xVelocity * time - a * time * time / 2;
                xVelocity += -a * time;
            }
            else {
                x -= -xVelocity * time;
                //x -= -xVelocity * time - a * time * time / 2;
                xVelocity += a * time;
            }

            if (prevxVelocity * xVelocity < 0)
                xVelocity = 0;
        }
    }

    private void updateYAxisPositionAndVelocity(double time) {
        if (yVelocity != 0) {
            double prevyVelocity = yVelocity;
            if (yVelocity > 0){
                y += yVelocity * time;
                //y += yVelocity * time - a * time * time / 2;
                yVelocity += -a * time;
            }
            else {
                y -= -yVelocity * time;
                //y -= -yVelocity * time - a * time * time / 2;
                yVelocity += a * time;
            }

            if (prevyVelocity * yVelocity < 0)
                yVelocity = 0;
        }
    }

    public double getMass() {
        return mass;
    }

    public void resolvePossibleEdgeCollision(int width, int height) {
        if (y - radius <= 0) {
            yVelocity *= -1;
            y = radius + 1;
        }
        else if (y + radius >= height) {
            yVelocity *= -1;
            y = height - radius - 1;
        }
        else if (x - radius <= 0) {
            xVelocity *= -1;
            x = radius + 1;
        }
        else if (x + radius >= width) {
            xVelocity *= -1;
            x = width - radius - 1;
        }
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public boolean resolvePossibleCollision(Circle c) {
        double distance = Math.sqrt((x - c.getX()) * (x - c.getX()) + (y - c.getY()) * (y - c.getY()));
        if (distance < radius + c.getRadius()){
            CollisionResolver.resolveCollision(this, c);
            return true;
        }

        return false;
    }

    public boolean resolvePossibleCollision(Rect rect) {
        CollisionResolver.resolveCollision(this, rect);
        return false;
    }

    public boolean containsPoint(float x, float y) {
        double distance = Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
        return distance <= radius;
    }

}
