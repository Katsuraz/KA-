# Homestead Player Animations (experimental source project)

Minecraft 1.20.1 Fabric client-side mixin. This is a *first prototype*, not the requested complete Actions & Stuff-style animation suite.

## What it implements
- Subtle idle breathing, locomotion torso sway and arm counter-motion, and airborne leg offsets.
- Copies final base-part transforms to skin overlays.
- Does not replace the player renderer or modify existing mods or saves.

## What it does NOT guarantee
- Full walking/running/crawling/swimming/combat keyframe animation suite.
- Compatibility with every custom wearable/accessory renderer. This requires in-game testing.
- Compatibility with animation mods that overwrite player poses after this mixin.

## Build
Install JDK 17 and Gradle 8.x, then in this folder run `gradle build`.
The output should be in `build/libs/` if compilation succeeds. A Gradle wrapper is not included.
Copy only the built mod JAR to the instance `mods/` folder. Back up the instance first.

## Test
Run in third person with Not Enough Animations, Accessories, Trinkets, Elytra Slot, Sophisticated Backpacks, Carry On, and Better Climbing. Test idle, walking, running, jumping, swimming, crouching, eating, combat, and wearing each accessory. Remove this JAR if any rendering issue occurs.
