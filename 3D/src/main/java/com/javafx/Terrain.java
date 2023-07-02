package com.javafx;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Terrain {
    // save heightmap of previous frame (terrain)
    Map<Pair<Float, Float>, Float> map = new HashMap<>();

    MeshView generateTerrain(int seed, CameraHandler camera) {
        int minX = ((int) camera.getCoordinateX() - 2000) / 5 * 5;
        int maxX = ((int) camera.getCoordinateX() + 2000) / 5 * 5;
        int minZ = ((int) camera.getAltitude() - 2000) / 5 * 5;
        int maxZ = ((int) camera.getAltitude() + 2000) / 5 * 5;

        TriangleMesh mesh = new TriangleMesh();

        // temporarily store heightmap for current frame
        Map<Pair<Float, Float>, Float> maptemp = new HashMap<>();

        // generating triangles
        for (float x = minX; x < maxX; x += 5) {
            for (float z = minZ; z < maxZ; z += 5) {
                Pair<Float,Float> key = new Pair<Float, Float>(x, z);
                float y;
                if (map.containsKey(key)) {
                    //take y from old heightmap
                    y = map.get(key);
                } else {
                    // ytemp hoplds the y value calculated by the noise function
                    float ytemp = (1 * OpenSimplex2S.noise2(seed, 0.0005 * x, 0.005 * z));
                    // add more variety to map
                    ytemp += (0.4 * OpenSimplex2S.noise2(seed, 0.001 * x, 0.01 * z));
                    ytemp += (0.2 * OpenSimplex2S.noise2(seed, 0.002 * x, 0.02 * z));
                    ytemp /= 1.6;
                    y = (float) Math.pow(Math.abs(ytemp), 3);
                    y *= 2000;
                    mesh.getPoints().addAll(x, y, z);
                }

                maptemp.put(new Pair<Float, Float>(x, z), y);
            }
        }
        map = maptemp;
        mesh.getTexCoords().addAll(0, 0);
        addFaces(mesh);
        MeshView finishView = generateGroup(mesh);

        return finishView;
    }

    private void addFaces(TriangleMesh mesh) {
        int numPoints = mesh.getPoints().size() / 3;
        int numRows = (int) Math.sqrt(numPoints);
        int numCols = numRows;

        for (int row = 0; row < numRows - 1; row++) {
            for (int col = 0; col < numCols - 1; col++) {
                int i = row * numCols + col;
                mesh.getFaces().addAll(i, 0, i + numCols + 1, 0, i + numCols, 0);
                mesh.getFaces().addAll(i + numCols + 1, 0, i, 0, i + 1, 0);
            }
        }
    }

    private MeshView generateGroup(TriangleMesh mesh) {
        // define materials
        final PhongMaterial grass = new PhongMaterial();
        grass.setSpecularColor(Color.LIGHTGREEN);
        grass.setDiffuseColor(Color.GREEN);

        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(grass);

        return meshView;
    }
}
