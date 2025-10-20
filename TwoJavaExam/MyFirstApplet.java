package TwoJavaExam;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MyFirstApplet extends Applet {
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.setFont(new Font("Serif", Font.BOLD, 28));
        g.drawString("Hello, this is my first Applet!", 20, 50);

        g.setColor(Color.red);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        g.drawString("Java Applet Demo", 20, 100);
    }
}
