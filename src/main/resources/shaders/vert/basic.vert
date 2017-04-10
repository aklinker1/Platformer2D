#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 proj_matrix;
uniform mat4 pos_matrix;

out DATA {
    vec2 tc;
} vs_out;

void main()
{
    gl_Position = proj_matrix * pos_matrix * position;
    vs_out.tc = tc;
}