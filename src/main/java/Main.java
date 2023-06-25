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

    private MouseInput mouseInput;
    int countDegree = 0;
    static float rot = 0f;
    Projection projection = new Projection(window.getWidth(),window.getHeight());

    Camera camera = new Camera();
    Camera freeCam = new Camera();//0
    Camera FPPcam = new Camera();  //1
    Camera TPPcam1 = new Camera();  //2
    Camera TPPcam2 = new Camera();  //3

    float distancePerson = 5f;
    float distanceBall = 2f;
    float angleAroundPerson = 0;
    float angleAroundBall = 0;

    Vector3f FPPpos= new Vector3f(0.0f, 4f, 0f);
    Vector3f TPPpos1 = new Vector3f(0f,5f,-5f);
    Vector3f TPPpos2 = new Vector3f(0.f, 2f, -2f);

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
        ball.get(0).translateObject(1.1f,1.8f,0.f);
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
        float thetaPerson = person.get(0).rotate + angleAroundPerson;
        float thetaBall = ball.get(0).rotate + angleAroundBall;
        float offsetXperson = (float) (distancePerson * Math.sin(Math.toRadians(thetaPerson)));
        float offsetZperson = (float) (distancePerson * Math.cos(Math.toRadians(thetaPerson)));
        float offsetXball = (float) (distanceBall * Math.sin(Math.toRadians(thetaBall)));
        float offsetZball = (float) (distanceBall * Math.cos(Math.toRadians(thetaBall)));

//        freecam
        freeCam.setPosition(-0.7f,1.8f,0.9f);
        freeCam.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(-5.0f));
//        FPP person
        FPPcam.setPosition(person.get(0).getCenterPoint().get(0) + FPPpos.x,person.get(0).getCenterPoint().get(1)+FPPpos.y,person.get(0).getCenterPoint().get(2)+FPPpos.z);
        FPPcam.setRotation(0,(float) Math.toRadians(180));
//        TPP person
        TPPcam1.setRotation(0f, (float) Math.toRadians(180 - thetaPerson));
        TPPcam1.setPosition(person.get(0).getCenterPoint().get(0) - offsetXperson, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2) - offsetZperson);
//        TPP ball
        TPPcam2.setRotation((float) Math.toRadians(10f), (float) Math.toRadians(180 - thetaBall));
        TPPcam2.setPosition(ball.get(0).getCenterPoint().get(0)- offsetXball, ball.get(0).getCenterPoint().get(1) + TPPpos2.y, ball.get(0).getCenterPoint().get(2)- offsetZball);
//        main
        camera.setPosition(0f,5f,-5f);
        camera.setRotation((float)Math.toRadians(20f),(float)Math.toRadians(180));
//        camera.setPosition(freeCam.getPosition().get(0),freeCam.getPosition().get(1),freeCam.getPosition().get(2));
//        camera.setRotation(freeCam.getRotation().x,freeCam.getRotation().y);


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
        float dxB = (float) (move * Math.sin(Math.toRadians(ball.get(0).rotate)));
        float dyB = (float) (move * Math.tan(Math.toRadians(ball.get(0).rotate)));
        float dzB = (float) (move * Math.cos(Math.toRadians(ball.get(0).rotate)));
        float thetaPerson = person.get(0).rotate + angleAroundPerson;
        float thetaBall = ball.get(0).rotate + angleAroundBall;
        float offsetXperson = (float) (distancePerson * Math.sin(Math.toRadians(thetaPerson)));
        float offsetZperson = (float) (distancePerson * Math.cos(Math.toRadians(thetaPerson)));
        float offsetXball = (float) (distanceBall * Math.sin(Math.toRadians(thetaBall)));
        float offsetZball = (float) (distanceBall * Math.cos(Math.toRadians(thetaBall)));
        float offsetX1person = (float) (TPPpos1.x * Math.sin(Math.toRadians(thetaPerson)));
        float offsetZ1person = (float) (TPPpos1.z * Math.cos(Math.toRadians(thetaPerson)));

//        movement obj 1 -> lebron 0 free, 1 fpp, 2 tpp
        if (window.isKeyPressed(GLFW_KEY_W )) {
            if (obj == 1) {
                if (camMode == 2) {
//            update TPP
                    person.get(0).translateObject(dx, 0f, dz);
//                if (!checkCollision()) {
                    TPPcam1.setRotation(0f, (float) Math.toRadians(180 - thetaPerson));
                    TPPcam1.setPosition(person.get(0).getCenterPoint().get(0) - offsetXperson, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2) - offsetZperson);
//            update FPP
                    FPPcam.setPosition(person.get(0).getCenterPoint().get(0) - offsetX1person, person.get(0).getCenterPoint().get(1) + FPPpos.y, person.get(0).getCenterPoint().get(2) - offsetZ1person);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                } else if (camMode == 1) {
                    camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
                    camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
                } else if (camMode == 2) {
                    camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
                    camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
//                }
                }
            }else if (obj == 2) {
                if (camMode == 3) {
//            update TPP
                    ball.get(0).translateObject(dxB, 0f, dzB);
//                if (!checkCollision()) {
                    TPPcam2.setRotation(0f, (float) Math.toRadians(180 - thetaBall));
                    TPPcam2.setPosition(ball.get(0).getCenterPoint().get(0) - offsetXball, ball.get(0).getCenterPoint().get(1) + TPPpos2.y, ball.get(0).getCenterPoint().get(2) - offsetZball);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                }  else if (camMode == 3) {
                    camera.setRotation(TPPcam2.getRotation().x, TPPcam2.getRotation().y);
                    camera.setPosition(TPPcam2.getPosition().x, TPPcam2.getPosition().y, TPPcam2.getPosition().z);
//                }
                }
            }
        } //W

        if (window.isKeyPressed(GLFW_KEY_S )) {
            if (obj == 1) {
                if (camMode == 2) {
//            update TPP
                    person.get(0).translateObject(-dx, 0f, -dz);
//                if (!checkCollision()) {
                    TPPcam1.setRotation(0f, (float) Math.toRadians(180 - thetaPerson));
                    TPPcam1.setPosition(person.get(0).getCenterPoint().get(0) - offsetXperson, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2) - offsetZperson);
//            update FPP
                    FPPcam.setPosition(person.get(0).getCenterPoint().get(0) - offsetX1person, person.get(0).getCenterPoint().get(1) + FPPpos.y, person.get(0).getCenterPoint().get(2) - offsetZ1person);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                } else if (camMode == 1) {
                    camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
                    camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
                } else if (camMode == 2) {
                    camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
                    camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
//                }
                }
            }else if (obj == 2) {
                if (camMode == 3) {
//            update TPP
                    ball.get(0).translateObject(-dxB, 0f, -dzB);
//                if (!checkCollision()) {
                    TPPcam2.setRotation(0f, (float) Math.toRadians(180 - thetaBall));
                    TPPcam2.setPosition(ball.get(0).getCenterPoint().get(0) - offsetXball, ball.get(0).getCenterPoint().get(1) + TPPpos2.y, ball.get(0).getCenterPoint().get(2) - offsetZball);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                }  else if (camMode == 3) {
                    camera.setRotation(TPPcam2.getRotation().x, TPPcam2.getRotation().y);
                    camera.setPosition(TPPcam2.getPosition().x, TPPcam2.getPosition().y, TPPcam2.getPosition().z);
//                }
                }
            }
        } //S

        if (window.isKeyPressed(GLFW_KEY_A )) {
            if (obj == 1) {
                if (camMode == 2) {
                    Vector3f currPos = new Vector3f(person.get(0).getCenterPoint().get(0), person.get(0).getCenterPoint().get(1), person.get(0).getCenterPoint().get(2));
                    person.get(0).translateObject(-currPos.x,-currPos.y,-currPos.z);
                    person.get(0).rotateObject((float)Math.toRadians(2),0f,1f,0f);
                    person.get(0).rotate +=2;
                    person.get(0).translateObject(currPos.x,currPos.y,currPos.z);

                    //            update TPP
                    TPPcam1.setRotation(0f, (float) Math.toRadians(180 - thetaPerson));
                    TPPcam1.setPosition(person.get(0).getCenterPoint().get(0) - offsetXperson, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2) - offsetZperson);
//            update FPP
                    FPPcam.addRotation(0,(float) Math.toRadians(-2));
                    FPPcam.setPosition(person.get(0).getCenterPoint().get(0) - offsetX1person, person.get(0).getCenterPoint().get(1) + FPPpos.y, person.get(0).getCenterPoint().get(2) - offsetZ1person);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                } else if (camMode == 1) {
                    camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
                    camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
                } else if (camMode == 2) {
                    camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
                    camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
//                }
                }
            }else if (obj == 2) {
                if (camMode == 3) {
                    Vector3f currPos = new Vector3f(ball.get(0).getCenterPoint().get(0), ball.get(0).getCenterPoint().get(1), ball.get(0).getCenterPoint().get(2));
                    ball.get(0).translateObject(-currPos.x,-currPos.y,-currPos.z);
                    ball.get(0).rotateObject((float)Math.toRadians(2),0f,1f,0f);
                    ball.get(0).rotate +=2;
                    ball.get(0).translateObject(currPos.x,currPos.y,currPos.z);

                    //            update TPP
                    TPPcam2.setRotation(0f, (float) Math.toRadians(180 - thetaBall));
                    TPPcam2.setPosition(ball.get(0).getCenterPoint().get(0) - offsetXball, ball.get(0).getCenterPoint().get(1) + TPPpos2.y, ball.get(0).getCenterPoint().get(2) - offsetZball);

                }
//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                }  else if (camMode == 3) {
                    camera.setRotation(TPPcam2.getRotation().x, TPPcam2.getRotation().y);
                    camera.setPosition(TPPcam2.getPosition().x, TPPcam2.getPosition().y, TPPcam2.getPosition().z);
//                }
                }
            }
        } //A

        if (window.isKeyPressed(GLFW_KEY_D )) {
            if (obj == 1) {
                if (camMode == 2) {
                    Vector3f currPos = new Vector3f(person.get(0).getCenterPoint().get(0), person.get(0).getCenterPoint().get(1), person.get(0).getCenterPoint().get(2));
                    person.get(0).translateObject(-currPos.x,-currPos.y,-currPos.z);
                    person.get(0).rotateObject((float)Math.toRadians(-2),0f,1f,0f);
                    person.get(0).rotate -=2;
                    person.get(0).translateObject(currPos.x,currPos.y,currPos.z);

                    //            update TPP
                    TPPcam1.setRotation(0f, (float) Math.toRadians(180 - thetaPerson));
                    TPPcam1.setPosition(person.get(0).getCenterPoint().get(0) - offsetXperson, person.get(0).getCenterPoint().get(1) + TPPpos1.y, person.get(0).getCenterPoint().get(2) - offsetZperson);
//            update FPP
                    FPPcam.addRotation(0,(float) Math.toRadians(2));
                    FPPcam.setPosition(person.get(0).getCenterPoint().get(0) - offsetX1person, person.get(0).getCenterPoint().get(1) + FPPpos.y, person.get(0).getCenterPoint().get(2) - offsetZ1person);
//                } else {
//                    person.get(0).translateObject(-dx, 0f, -dz);
//                }
                }

//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                } else if (camMode == 1) {
                    camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
                    camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
                } else if (camMode == 2) {
                    camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
                    camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
//                }
                }
            }else if (obj == 2) {
                if (camMode == 3) {
                    Vector3f currPos = new Vector3f(ball.get(0).getCenterPoint().get(0), ball.get(0).getCenterPoint().get(1), ball.get(0).getCenterPoint().get(2));
                    ball.get(0).translateObject(-currPos.x,-currPos.y,-currPos.z);
                    ball.get(0).rotateObject((float)Math.toRadians(-2),0f,1f,0f);
                    ball.get(0).rotate -=2;
                    ball.get(0).translateObject(currPos.x,currPos.y,currPos.z);

                    //            update TPP
                    TPPcam2.setRotation(0f, (float) Math.toRadians(180 - thetaBall));
                    TPPcam2.setPosition(ball.get(0).getCenterPoint().get(0) - offsetXball, ball.get(0).getCenterPoint().get(1) + TPPpos2.y, ball.get(0).getCenterPoint().get(2) - offsetZball);

                }
//            if (!checkCollision()) {
                if (camMode == 0) {
                    camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
                    camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
                }  else if (camMode == 3) {
                    camera.setRotation(TPPcam2.getRotation().x, TPPcam2.getRotation().y);
                    camera.setPosition(TPPcam2.getPosition().x, TPPcam2.getPosition().y, TPPcam2.getPosition().z);
//                }
                }
            }
        }//D

        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            ball.get(0).translateObject(0f, 0.01f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            ball.get(0).translateObject(0f, -0.01f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_I)) {
            ball.get(0).translateObject(0.01f, 0.0f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_J)) {
            ball.get(0).translateObject(0f, 0f, -0.01f);
        }
        if (window.isKeyPressed(GLFW_KEY_K)) {
            ball.get(0).translateObject(-0.01f, 0.0f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_L)) {
            ball.get(0).translateObject(0f, 0.0f, 0.01f);
        }


//        cam modes
//        free
        if (window.isKeyPressed(GLFW_KEY_0)) {
            camMode = 0;
            camera.setRotation(freeCam.getRotation().x, freeCam.getRotation().y);
            camera.setPosition(freeCam.getPosition().x, freeCam.getPosition().y, freeCam.getPosition().z);
        }
//      fpp person
        if (window.isKeyPressed(GLFW_KEY_1)) {
            camMode = 1;
//            System.out.println("1");
            camera.setRotation(FPPcam.getRotation().x, FPPcam.getRotation().y);
            camera.setPosition(FPPcam.getPosition().x, FPPcam.getPosition().y, FPPcam.getPosition().z);
        }
//      tpp person
        if (window.isKeyPressed(GLFW_KEY_2)) {
            camMode = 2;
            camera.setRotation(TPPcam1.getRotation().x, TPPcam1.getRotation().y);
            camera.setPosition(TPPcam1.getPosition().x, TPPcam1.getPosition().y, TPPcam1.getPosition().z);
        }
//        tpp ball
        if (window.isKeyPressed(GLFW_KEY_3)) {
            camMode = 3;
            camera.setRotation(TPPcam2.getRotation().x, TPPcam2.getRotation().y);
            camera.setPosition(TPPcam2.getPosition().x, TPPcam2.getPosition().y, TPPcam2.getPosition().z);
        }


//
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT_SHIFT)) {
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT_CONTROL)) {
            camera.moveDown(move);
        }
//        rotating cam
        if (window.isKeyPressed(GLFW_KEY_R)){
            camera.moveRight(move);
            camera.addRotation( 0, -0.01f);
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
//      obj selection
        if (window.isKeyPressed(GLFW_KEY_F11)) {
            obj=1;
        }
        if (window.isKeyPressed(GLFW_KEY_F12)) {
            obj=2;
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
            rotateObj();
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

    public static float getRot() {
        return rot;
    }

    public static void setRot(float rot) {
        Main.rot += rot;
    }
    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}