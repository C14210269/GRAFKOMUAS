//#version 330
////vec4 itu buat awarna
//uniform vec4 uni_color;
//out vec4 frag_color;
//void main() {
//    //    frag_color = vec4 (1.0,0.0,0.0,1.0);
//    //  warna buat segitiga bisa sama kyk di main
//    frag_color = uni_color;
//}

//shading 1

#version 330
uniform vec4 uni_color; // nyesuain sama yg di main ntar vec4 karena tipe data colornya vector 4
// out ngelempar variable yang ada di sini keluar
out vec4 frag_color;
// kalo in itu berharap masuk dari vert

uniform vec3 lightColor;
uniform vec3 lightPos;
in vec3 Normal;
in vec3 FragPos;

void main(){
    //    frag_color = vec4(1.0, 0.0, 0.0, 1.0);
    //    frag_color = uni_color;
    // ambient biasanya < 0.5f
    float ambientStrength = 0.5f;
    vec3 ambient = ambientStrength * lightColor;

    // diffuse
    vec3 lightDirection = normalize(lightPos - FragPos);
    float diff = max(dot(Normal, lightDirection), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 result = (ambient+diffuse) * vec3(uni_color);

    frag_color = vec4(result, 1.0);
}
