package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Model {
    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Vector2f> texture = new ArrayList<>();
    public ArrayList<Integer> vertexIndices = new ArrayList<>();
    public ArrayList<Integer> normalIndices = new ArrayList<>();

    public Model() {

    }
}