#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int flip = 0;

void main()
{
    vec2 tc = fs_in.tc;
    if (flip == 1) {
        tc.y = 1.0 - tc.y;
    }
    color = texture(tex, tc);
    if (color.w < 1.0) discard;
}