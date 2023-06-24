//#version 330
//layout (location = 0) in vec3 position;
//
//uniform mat4 model;
////pert 9 - projection n camera
//uniform mat4 view;
//uniform mat4 projection;
//
////kalo nemu rumus di internet dibalik?
//
//void main(){
//    gl_Position = projection * view * model * vec4(position, 1.0);
//}

//shading 1
#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec3 Normal;
out vec3 FragPos;
void main(){
    gl_Position = projection * view * model * vec4(position, 1.0);
    FragPos = vec3(model * vec4(position, 1.0));
    Normal = normalize(model * vec4(normal, 1.0)).xyz;
}