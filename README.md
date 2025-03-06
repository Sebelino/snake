Sebastian Olsson's Snake
=====

<p align="center">
  <img src="https://github.com/user-attachments/assets/5e42d276-fc1d-40c5-b3ef-6b3671f00b8e" />
</p>

Created this game as a project in the subject Programmering B in upper secondary school, Fall 2008.

Usage
=====
Old method: Open Index.htm with a browser.

Modern method: Install OpenJDK and run it with `appletviewer`. You may need an
old version of OpenJDK:

```bash
jdk_path=/usr/lib/jvm/java-8-openjdk
"$jdk_path/bin/javac" *.java
"$jdk_path/bin/appletviewer" Index.htm
```

Gameplay
=====

I created two modes for this game:

* 2-player mode: Player 1 uses the arrow keys. Player 2 uses WASD.
* 3-player mode: There is a third AI-controlled snake.

3-player mode is the default. To switch to 2-player mode,
remove or comment out lines `496` and `45` in `SebastianOlssonsSnake.java`,
then recompile:

```diff
--- a/SebastianOlssonsSnake.java
+++ b/SebastianOlssonsSnake.java
@@ -42,7 +42,6 @@ public class SebastianOlssonsSnake extends Applet implements Runnable
     {
         ormlangdPL = 20;
         ormlangdPL2 = 20;
-        ormlangdAI = 20;
         amoebaPLX = 1;
         amoebaPLY = 0;
         amoebaPL2X = 1;
@@ -493,7 +492,6 @@ public class SebastianOlssonsSnake extends Applet implements Runnable
         {
             spelare();
             spelare2();
-            AI();
             repaint();
             try
             {
```

and then recompile:

```bash
"$jdk_path/bin/javac" *.java
```
