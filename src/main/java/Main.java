import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window =
            new Window
    (1600,1400,"Hello World");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private ArrayList<Object> ball
            = new ArrayList<>();

    private ArrayList<Object> person
            = new ArrayList<>();

    private MouseInput mouseInput;
    int countDegree = 0;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    public void init() throws IOException {
        window.init();
        GL.createCapabilities();
        mouseInput = window.getMouseInput();
        camera.setPosition(0,2f,1.0f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(30.0f));

//skybox    #0
        objects.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.86f,0.86f,0.86f,1.0f),
                "fixed res\\skybox.obj"
        ));
//        main field #1
        objects.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.27f,0.35f,1.0f),
                "fixed res\\main field.obj"
        ));
        //        ball #0
        ball.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.54f,0.0f,1.0f),
                "fixed res\\basket.obj"
        ));
//        #4#1
        ball.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,0.0f,1.0f),
                "fixed res\\basket line.obj"
        ));
        ball.get(0).translateObject(0f,1f,0f);
////        main tribun #3
//        objects.add(new ObjLoader(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.5f,0.5f,0.5f,1.0f),
//                "fixed res\\stage.obj"
//        ));
////        main chair #4
//        objects.add(new ObjLoader(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.5f,0.02f,0.02f,1.0f),
//                "fixed res\\chair.obj"
//        ));
//child main field #0#0
        objects.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,0.0f,1.0f),
                "fixed res\\ring black.obj"
        ));
        //child main field #0#1
        objects.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                "fixed res\\backboard.obj"
        ));
        //child main field #0#2
        objects.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.0f,0.0f,1.0f),
                "fixed res\\head ring.obj"
        ));
        //child main field #0#3
        objects.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                "fixed res\\mesh.obj"
        ));
//        person #0
        person.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.016f,0.23f,0.36f,1.0f),
                "fixed res\\LeBron James\\navy bron.obj"
        ));
        //        person #0#0
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.63f,0.32f,0.18f,1.0f),
                "fixed res\\LeBron James\\body bron.obj"
        ));
        //        person #0#1
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.0f,0.0f,1.0f),
                "fixed res\\LeBron James\\red bron.obj"
        ));
        //        person #0#2
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                "fixed res\\LeBron James\\white bron.obj"
        ));
        //        person #0#3
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,1.0f,1.0f),
                "fixed res\\LeBron James\\blue bron.obj"
        ));
        //        person #0#4
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.63f,0.32f,0.18f,1.0f),
                "fixed res\\LeBron James\\face bron.obj"
        ));
        //        person #0#5
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,0.0f,1.0f),
                "fixed res\\LeBron James\\hair bron.obj"
        ));
        //        person #0#6
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                "fixed res\\LeBron James\\teeth eye bron.obj"
        ));
        //        person #0#7
        person.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.49f,0.24f,0.2f,1.0f),
                "fixed res\\LeBron James\\lips bron.obj"
        ));




        //coba texture
//        objects.add(new ObjLoader(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(),
//                "C:\\Users\\Frenky\\Documents\\GitHub\\uasjuan\\GRAFKOMUAS\\coba texture.obj"
//        ));


    }

    public void rotateObj() {
        if (countDegree > 0) {
            countDegree--;
            camera.addRotation(0, (float) Math.toRadians(0.95));
        }
    }


    public void input(){
        float move = 0.1f;
//        coba
//        obj ball movement
        if (window.isKeyPressed(GLFW_KEY_1)) {
            Vector3f objectDir = new Vector3f(1.0f, 0.0f, 0.0f);
            ball.get(0).model.transformDirection(objectDir, objectDir);
            Vector3f translation = new Vector3f(objectDir).mul(0.1f);
            ball.get(0).translateObject(translation.x/10, translation.y/10, translation.z/10);
        }
        if (window.isKeyPressed(GLFW_KEY_2)) {
            Vector3f objectDir = new Vector3f(0.0f, 1.0f, 0.0f);
            ball.get(0).model.transformDirection(objectDir, objectDir);
            Vector3f translation = new Vector3f(objectDir).mul(0.1f);
            ball.get(0).translateObject(translation.x/10, translation.y/10, translation.z/10);
        }
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
//
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }

        if (window.isKeyPressed(GLFW_KEY_F1)) {
            camera.setPosition(48f,15f,0f);
            camera.setRotation(0.2f, -1.55f);
        }
        if (window.isKeyPressed(GLFW_KEY_F2)) {
            camera.setPosition(48f,15f,-30f);
            camera.setRotation(0.2f, -2.2f);
        }
        if (window.isKeyPressed(GLFW_KEY_F3)) {
            camera.setPosition(0.0f,15f,-30f);
            camera.setRotation(0.2f, -3.1f);
        }
        if (window.isKeyPressed(GLFW_KEY_F4)) {
            camera.setPosition(-48f,15f,-30f);
            camera.setRotation(0.2f, -4.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_F5)) {
            camera.setPosition(-48f,15f,0f);
            camera.setRotation(0.2f, 1.55f);
        }
        if (window.isKeyPressed(GLFW_KEY_F6)) {
            camera.setPosition(-48f, 15f, 30f);
            camera.setRotation(0.2f, 0.9f);
        }
        if (window.isKeyPressed(GLFW_KEY_F7)) {
            camera.setPosition(0.0f,15f,30f);
            camera.setRotation(0.2f, 0.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_F8)) {
            camera.setPosition(48f,15f,30f);
            camera.setRotation(0.2f, -0.9f);
        }
//
        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
            window.getMouseInput().setScroll(new Vector2f());

    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(1.0f,
                    1.0f, 1.0f,
                    1.0f);
            GL.createCapabilities();
            input();

            //code
            for(Object object: objects){
                object.draw(camera,projection);
            }
            for(Object b : ball){
                b.draw(camera,projection);
            }
            for(Object p : person){
                p.draw(camera,projection);
            }
//            rotateObj();
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() throws IOException {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}