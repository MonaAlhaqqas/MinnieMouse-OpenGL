

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_LINE_LOOP;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * MinnieMouse.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class MinnieMouse implements GLEventListener {

    Texture tex;

    public static void main(String[] args) {
        Frame frame = new Frame("Minnie Mouse -- Mona Alhaqqas");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new MinnieMouse());
        frame.add(canvas);
        frame.setSize(420, 350);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.glEnable(GL.GL_TEXTURE_2D); //activate texture mapping for 2D
        try {
//load texture
            tex = TextureIO.newTexture(new File("dot.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(254 / 255f, 274 / 255f, 193/255f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(1.3f, -0.3f, -6.0f);

        // Move the "drawing cursor" to another position
        //gl.glTranslatef(3.0f, 0.0f, 0.0f);
        //gl.glColor3f(255 / 255f, 255 / 255f, 255 / 255f);              
        tex.bind();
        tex.enable();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(-5, 4f); //top left
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(2.5f, 3f); //top right
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(2.5f, -3f);// down right
        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(-5, -4f);//down left
        gl.glEnd();
        tex.disable();

        float numPoints = 50;

        gl.glTranslatef(-0.5f, 1.3f, -1.0f);
        gl.glLineWidth(1);

        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(0, 0, 0);
        gl.glVertex2f(-2.7f, 0f);
        gl.glVertex2f(-2.7f, -2.5f);
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        float Radiusx = 0.6f;
        float Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 238 / 255f, 229 / 255f);
        Radiusx = 0.2f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.65, Y + 0.85);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 11f, 229 / 255f);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.96, Y + 0.95);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        Radiusx = 0.24f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.69, Y + 0.82);
        }

        gl.glEnd();

        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-2.61f, -0.3f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-2.71f, -0.10f, 0.0f);//triangle one third vertex
        gl.glVertex3f(-2.81f, -0.3f, 0.0f);//triangle one second vertex        

        gl.glEnd();

        gl.glLineWidth(1.3f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0f, 0f, 0f);

        Radiusx = 0.6f;
        Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }

        gl.glEnd();

        gl.glTranslatef(-0.38f, -1.2f, 0.0f);
        gl.glLineWidth(1);
        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(0, 0, 0);
        gl.glVertex2f(-2.7f, 0f);
        gl.glVertex2f(-2.7f, -2.7f);
        gl.glEnd();
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-2.61f, -0.3f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-2.71f, -0.10f, 0.0f);//triangle one third vertex
        gl.glVertex3f(-2.81f, -0.3f, 0.0f);//triangle one second vertex        

        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.6f;
        Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 238 / 255f, 229 / 255f);
        Radiusx = 0.2f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.65, Y + 0.85);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 11f, 229 / 255f);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.96, Y + 0.95);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        Radiusx = 0.24f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.69, Y + 0.82);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0f, 0f, 0f);

        Radiusx = 0.6f;
        Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }
        gl.glEnd();

        gl.glTranslatef(0.85f, -0.75f, 0.0f);

        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(0, 0, 0);
        gl.glVertex2f(-2.7f, 0f);
        gl.glVertex2f(-2.7f, -2f);
        gl.glEnd();
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-2.61f, -0.3f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-2.71f, -0.10f, 0.0f);//triangle one third vertex
        gl.glVertex3f(-2.81f, -0.3f, 0.0f);//triangle one second vertex        

        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.6f;
        Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 238 / 255f, 229 / 255f);
        Radiusx = 0.2f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.65, Y + 0.85);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(248 / 255f, 11f, 229 / 255f);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.96, Y + 0.95);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        Radiusx = 0.24f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.69, Y + 0.82);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0f, 0f, 0f);

        Radiusx = 0.6f;
        Radiusy = 0.7f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 2.75, Y + 0.5);
        }
        gl.glEnd();

        gl.glColor3f(1, 1, 1);

//---------------------------------------------------
        gl.glTranslatef(0.85f, 1.6f, 1.0f);

        Radiusx = 1f;
        Radiusy = 1.03f;

        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(0, 0, 0);
        gl.glVertex3f(-2.4f, -3.6f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-0.3f, -3.6f, 0.0f);//triangle one second vertex
        gl.glVertex3f(-1.35f, -1.5f, 0.0f);//triangle one third vertex
        gl.glColor3f(1, 1, 1);
        gl.glEnd();

        gl.glColor3f(0f, 0f, 0f);
        //ear1
        gl.glBegin(GL.GL_POLYGON);
        float Radius = 0.5f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radius;
            double Y = Math.sin(Angle) * Radius;
            gl.glVertex2d(X - 0.4, Y - 0.2);

        }
        gl.glEnd();

        //Draw face of micky mouse
        gl.glColor3f(0f, 0f, 0f);     // Set the current drawing color to light blue

        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.33, Y - 1);

        }
        gl.glEnd();

        //ear2
        gl.glBegin(GL.GL_POLYGON);
        Radius = 0.5f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radius;
            double Y = Math.sin(Angle) * Radius;
            gl.glVertex2d(X - 2.2, Y - 0.2);

        }
        gl.glEnd();

        gl.glColor3f(255 / 255f, 215 / 255f, 196 / 255f);
        //eyeback1
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.4f;
        Radiusy = 0.5f;

        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1, Y - 0.7);

        }
        gl.glEnd();

        //eyeback2
        gl.glBegin(GL.GL_POLYGON);

        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.62, Y - 0.7);

        }
        gl.glEnd();

        //mouthback
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.81f;
        Radiusy = 0.48f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.32, Y - 1.4);

        }
        gl.glEnd();

        //mouth
        gl.glColor3f(255 / 255f, 215 / 255f, 196 / 255f);

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.3f;
        Radiusy = 0.4f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.31, Y - 1.55);
        }
        gl.glEnd();

        gl.glColor3f(0f, 0f, 0f);

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.7f;
        Radiusy = 0.3f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.299, Y - 1.26);
        }
        gl.glEnd();

        gl.glColor3f(0f, 0f, 0f);

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.26f;
        Radiusy = 0.36f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.31, Y - 1.53);
        }
        gl.glEnd();

        gl.glColor3f(255 / 255f, 215 / 255f, 196 / 255f);
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.7199f;
        Radiusy = 0.30f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.2999, Y - 1.22);
        }
        gl.glEnd();

        //eye1
        gl.glColor3f(0f, 0f, 0f);     // Set the current drawing color to light blue
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.1f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.08, Y - 0.8);
        }
        gl.glEnd();

        //eye2
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.1f;
        Radiusy = 0.2f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.50, Y - 0.8);
        }
        gl.glEnd();

        gl.glColor3f(255 / 255f, 215 / 255f, 196 / 255f);
        //insede eyes
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-1.3f, -0.90f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-1.1f, -0.80f, 0.0f);//triangle one second vertex
        gl.glVertex3f(-1.3f, -0.70f, 0.0f);//triangle one third vertex

        gl.glVertex3f(-1.73f, -0.90f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-1.53f, -0.80f, 0.0f);//triangle one second vertex
        gl.glVertex3f(-1.73f, -0.70f, 0.0f);//triangle one third vertex

        gl.glEnd();

        //nose
        gl.glColor3f(0f, 0f, 0f);
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.2f;
        Radiusy = 0.17f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.28, Y - 1.18);
        }
        gl.glEnd();

        //point in nose
        gl.glColor3f(246 / 255f, 242 / 255f, 233 / 255f);

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.05f;
        Radiusy = 0.04f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.37, Y - 1.15);
        }
        gl.glEnd();

        //tongue
        gl.glColor3f(255 / 266f, 160 / 255f, 160 / 255f);     // Set the current drawing color to light blue
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.10f;
        Radiusy = 0.11f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.24, Y - 1.7);
        }
        gl.glEnd();

        gl.glColor3f(255 / 266f, 160 / 255f, 160 / 255f);     // Set the current drawing color to light blue
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.10f;
        Radiusy = 0.11f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.37, Y - 1.7);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.11f;
        Radiusy = 0.09f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.30, Y - 1.752);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.09f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.42, Y - 1.68);
        }

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.09f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.189, Y - 1.68);
        }
        gl.glEnd();

        //---------------------------------bow-----------------------------
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);

        gl.glVertex3f(-0.6f, 0.9f, 0.0f);//triangle one first vertex
        gl.glVertex3f(-0.6f, -0.37f, 0.0f);//triangle one second vertex
        gl.glVertex3f(-1.3f, 0f, 0.0f);//triangle one third vertex

        gl.glVertex3f(-2f, 0.9f, 0.0f);//triangle one third vertex
        gl.glVertex3f(-2f, -0.37f, 0.0f);//triangle one third vertex
        gl.glVertex3f(-1.3f, 0f, 0.0f);//triangle one third vertex

        gl.glEnd();
        gl.glColor3f(248 / 255f, 11f, 229 / 255f);

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.07f;
        Radiusy = 0.1f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.3, Y);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.5, Y);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.6, Y + 0.2);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.7, Y);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.9, Y - 0.1);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.85, Y + 0.17);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.73, Y + 0.4);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.9, Y + 0.5);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.7, Y + 0.6);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.85, Y + 0.45);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.72, Y + 0.3);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.9, Y + 0.19);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.1, Y + 0.06);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.7, Y + 0.06);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.9, Y + 0.010);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_POLYGON);
        Radiusx = 0.06f;
        Radiusy = 0.06f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 0.7, Y - 0.2);
        }
        gl.glEnd();

        gl.glLineWidth(7);
        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(1, 1, 1);
        gl.glVertex2f(-1.35f, -2.1f);
        gl.glVertex2f(-1.35f, -2.8f);
        gl.glEnd();

        gl.glLineWidth(1);
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(1, 0, 0);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.349, Y - 2.2);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(1, 0, 0);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.349, Y - 2.44);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(255 / 255f, 64 / 255f, 117 / 255f);
        Radiusx = 0.03f;
        Radiusy = 0.03f;
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle) * Radiusx;
            double Y = Math.sin(Angle) * Radiusy;
            gl.glVertex2d(X - 1.349, Y - 2.68);
        }
        gl.glEnd();

        gl.glColor3f(1, 1, 1);

        //---------------------------------END BOW-------------------------------
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
