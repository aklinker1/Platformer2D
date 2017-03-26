#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform vec4 font_color;

void main()
{
    color = texture(tex, fs_in.tc);
    if (color.w <= 0) discard;
    else {
        // sets the color to the font color
        color.x = font_color.x;
        color.y = font_color.y;
        color.z = font_color.z;
        color.w = font_color.w * color.w;
    }
}