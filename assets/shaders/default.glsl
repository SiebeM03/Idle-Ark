#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;
layout (location=5) in float aCooldown;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;
out float fCooldown;

void main()
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;
    fCooldown = aCooldown;

    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;
in float fCooldown;

uniform sampler2D uTextures[8];

out vec4 color;

void main()
{
    if (fTexId > 0) {
        int id = int(fTexId);
        if (fCooldown == 0) {
            // if cooldown is 0, render the texture normally (no cooldown value was given)
            color = fColor * texture(uTextures[id], fTexCoords);
        } else {
            // if cooldown is not 0, render the texture with a cooldown effect
            bool aboveCooldown = fCooldown > fTexCoords.y;
            if (aboveCooldown) {
                color = fColor * texture(uTextures[id], fTexCoords);
            } else {
                vec4 newColor = vec4(fColor.rgb * 0.5, 1) * texture(uTextures[id], fTexCoords);
                color = newColor;
            }
        }
    } else {
        color = fColor;
    }
}