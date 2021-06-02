package Compiler;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Lady Java");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new GUI(frame));
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
                int c = 2+ returnArray()[2];
            }
        });
    }

    public static int[] returnArray(){
        int a[]=new int[2];
        a[0]=1;
        a[1]=3;
        return a;
    }
}
