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
    ArrayList<Object> objects = new ArrayList<>();
    ArrayList<Object> ball = new ArrayList<>();
    ArrayList<Object> person = new ArrayList<>();
    ArrayList<Object> lighthouse = new ArrayList<>();

    private MouseInput mouseInput;
    int countDegree = 0;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();  //0
    Camera freeCam = new Camera();
    Camera FPPcam = new Camera();  //1
    Camera TPPcam1 = new Camera();  //2
    Camera TPPcam2 = new Camera();  //3

    float distanceCamera = 2f;
    float angleAroundPlayer = 0;

    Vector3f FPPpos= new Vector3f(0.0f, 4f, 0f);
    Vector3f TPPpos1 = new Vector3f(0f,5f,-5f);
    Vector3f TPPpos2 = new Vector3f(0.f, 2f, -0.5f);

//    utk milih kamera mana yg dipake
    int obj=1, camMode=0;

    public void init() throws IOException {
        window.init();
        GL.createCapabilities();
        mouseInput = window.getMouseInput();


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
                "fixed res\\ball.obj"
        ));
//        #4#1
        ball.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,0.0f,1.0f),
                "fixed res\\stripe ball.obj"
        ));
        ball.get(0).translateObject(1.1f,1.9f,-0.2f);
        ball.get(0).scaleObject(0.8f,0.8f,0.8f);
//        main tribun #2
        objects.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "fixed res\\stage.obj"
        ));
//        main chair #3
        objects.add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.02f,0.02f,1.0f),
                "fixed res\\chair.obj"
        ));
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
//        child main field #0#4 outer field
        objects.get(0).getChildObject().add(new ObjLoader(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.0f,0.0f,1.0f),
                "fixed res\\outer field.obj"
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

//        cam setting
        float angle=-10;
        float theta = person.get(0).rotate + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//        freecam
        freeCam.setPosition(-0.7f,1.8f,0.9f);
        freeCam.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(-5.0f));
//        FPP person
        FPPcam.setPosition(person.get(0).getCenterPoint().get(0) + FPPpos.x,person.get(0).getCenterPoint().get(1)+FPPpos.y,person.get(0).getCenterPoint().get(2)+FPPpos.z);
        FPPcam.setRotation(0,0);
//        TPP person
        TPPcam1.setRotation((float) Math.toRadians(angle), (float) Math.toRadians(180 - theta));
        TPPcam1.setPosition(person.get(0).getCenterPoint().get(0)-offsetX, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2)-offsetZ);
//        main
        camera.setPosition(0f,5f,-5f);
        camera.setRotation((float)Math.toRadians(20f),(float)Math.toRadians(180));
//        camera.setPosition(freeCam.getPosition().get(0),freeCam.getPosition().get(1),freeCam.getPosition().get(2));
//        camera.setRotation(freeCam.getRotation().x,freeCam.getRotation().y);



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
        float dx = (float) (move * Math.sin(Math.toRadians(person.get(0).rotate)));
        float dz = (float) (move * Math.cos(Math.toRadians(person.get(0).rotate)));
        float theta = person.get(0).rotate + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
        float offsetX1 = (float) (TPPpos1.x * Math.sin(Math.toRadians(theta)));
        float offsetZ1 = (float) (TPPpos1.z * Math.cos(Math.toRadians(theta)));

//        movement obj 1 -> lebron 0 free, 1 fpp, 2 tpp
//        if (window.isKeyPressed(GLFW_KEY_W )&& obj == 1) {
//            if (camMode == 0) camera.moveForward(move);
//            else if (camMode == 1){
////                FPP
//
////                TPP
//                person.get(0).translateObject(dx, 0f, dz);
//            }else if (camMode == 2){
//
//            }
//        }

//        cam modes
        if (window.isKeyPressed(GLFW_KEY_0)) {
            camMode = 0;
            camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
            camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_1)) {
            camMode = 1;
//            System.out.println("1");
            camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
            camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_2)) {
            camMode = 2;
            camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
            camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
        }
        if (window.isKeyPressed(GLFW_KEY_3)) {
            camMode = 3;
            camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
            camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
        }


//        movement obj 2 -> bola 0 free, 3 tpp

//        coba
//        obj ball movement
//        if (window.isKeyPressed(GLFW_KEY_1)) {
//            Vector3f objectDir = new Vector3f(1.0f, 0.0f, 0.0f);
//            ball.get(0).model.transformDirection(objectDir, objectDir);
//            Vector3f translation = new Vector3f(objectDir).mul(0.1f);
//            ball.get(0).translateObject(translation.x/10, translation.y/10, translation.z/10);
//        }
//        if (window.isKeyPressed(GLFW_KEY_2)) {
//            Vector3f objectDir = new Vector3f(0.0f, 1.0f, 0.0f);
//            ball.get(0).model.transformDirection(objectDir, objectDir);
//            Vector3f translation = new Vector3f(objectDir).mul(0.1f);
//            ball.get(0).translateObject(translation.x/10, translation.y/10, translation.z/10);
//        }
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

//      cam position
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
//      still need mouse tho :v
        if (window.getMouseInput().getScroll().y != 0) {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

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
            glClearColor(0.0f,
                    0.0f, 0.0f,
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