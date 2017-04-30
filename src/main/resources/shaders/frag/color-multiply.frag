#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform vec4 color_multiply;
uniform float alpha;

void main()
{
    color = texture(tex, fs_in.tc);
    // sets the color to the font color
    color.x = color_multiply.x * color.z;
    color.y = color_multiply.y * color.z;
    color.z = color_multiply.z * color.z;
    color.w = color_multiply.w * color.w * alpha;
}