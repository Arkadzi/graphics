package me.gumennyi.lab2.graphics;

import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Point3D;

public class IsometricTransformer {
    private final double[][] changeArray;

    public IsometricTransformer(double fiAngle, double thetaAngle) {
        double[][] fiMatrix = calculateFiMatrix(fiAngle);
        double[][] thetaMatrix = calculateThetaMatrix(thetaAngle);
        changeArray = multiplyMatrices(thetaMatrix, fiMatrix);
    }

    private double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
        int aHeight = matrixA.length;
        int aWidth = matrixA[0].length;
        int bWidth = matrixB[0].length;
        double[][] result = new double[aHeight][bWidth];
        double sum;

        for (int i = 0; i < aHeight; i++) {
            for (int j = 0; j < bWidth; j++) {
                sum = 0;
                for (int k = 0; k < aWidth; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public Point2D transform(Point3D points3D) {

        double[][] tempPoint = new double[3][1];

        tempPoint[0][0] = points3D.x;
        tempPoint[1][0] = points3D.y;
        tempPoint[2][0] = points3D.z;

        tempPoint = multiplyMatrices(changeArray, tempPoint);

        return new Point2D(tempPoint[0][0], tempPoint[1][0]);
    }

    private double[][] calculateFiMatrix(double fiAngle) {
        double fi = fiAngle * Math.PI / 180.0;
        return new double[][]{
                {Math.cos(fi), -Math.sin(fi), 0},
                {Math.sin(fi), Math.cos(fi), 0},
                {0, 0, 1}
        };
    }

    private double[][] calculateThetaMatrix(double thetaAngle) {
        double theta = thetaAngle * Math.PI / 180.0;
        return new double[][]{
                {1, 0, 0},
                {0, Math.cos(theta), Math.sin(theta)},
                {0, -Math.sin(theta), Math.cos(theta)}
        };
    }
}

