#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 position_matrix;
uniform mat4 translation_matrix;
uniform mat4 camera_matrix;

out DATA {
    vec2 tc;
} vs_out;

void main()
{
    gl_Position = position_matrix * translation_matrix * camera_matrix * position;
    vs_out.tc = tc;
}