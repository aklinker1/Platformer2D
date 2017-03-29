#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform vec4 color_overlay;

void main()
{
    color = texture(tex, fs_in.tc);
    if (color.w <= 0) discard;
    else {
        // sets the color to the font color
        color.x = color_overlay.x;
        color.y = color_overlay.y;
        color.z = color_overlay.z;
        color.w = color_overlay.w * color.w;
    }
}