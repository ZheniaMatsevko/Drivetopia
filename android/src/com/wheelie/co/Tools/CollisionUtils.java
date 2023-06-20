package com.wheelie.co.Tools;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class CollisionUtils {
    public static boolean collides(Polygon polygon, Rectangle rectangle) {
        float[] polygonVertices = polygon.getTransformedVertices();
        float[] rectangleVertices = {
                rectangle.x, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y + rectangle.height,
                rectangle.x, rectangle.y + rectangle.height
        };

        return polygonRectangleCollision(polygonVertices, rectangleVertices);
    }

    private static boolean polygonRectangleCollision(float[] polygonVertices, float[] rectangleVertices) {
        int numAxes = polygonVertices.length / 2;

        for (int i = 0; i < numAxes; i++) {
            float axisX = -(polygonVertices[(i + 1) % numAxes * 2 + 1] - polygonVertices[i * 2 + 1]);
            float axisY = polygonVertices[(i + 1) % numAxes * 2] - polygonVertices[i * 2];

            float axisLength = (float) Math.sqrt(axisX * axisX + axisY * axisY);
            axisX /= axisLength;
            axisY /= axisLength;

            float polygonMin = Float.POSITIVE_INFINITY;
            float polygonMax = Float.NEGATIVE_INFINITY;
            for (int j = 0; j < numAxes; j++) {
                float dotProduct = polygonVertices[j * 2] * axisX + polygonVertices[j * 2 + 1] * axisY;
                polygonMin = Math.min(polygonMin, dotProduct);
                polygonMax = Math.max(polygonMax, dotProduct);
            }

            float rectangleMin = Float.POSITIVE_INFINITY;
            float rectangleMax = Float.NEGATIVE_INFINITY;
            for (int j = 0; j < 4; j++) {
                float dotProduct = rectangleVertices[j * 2] * axisX + rectangleVertices[j * 2 + 1] * axisY;
                rectangleMin = Math.min(rectangleMin, dotProduct);
                rectangleMax = Math.max(rectangleMax, dotProduct);
            }

            if (polygonMax < rectangleMin || polygonMin > rectangleMax) {
                return false;
            }
        }

        return true;
    }
}