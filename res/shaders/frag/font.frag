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
}