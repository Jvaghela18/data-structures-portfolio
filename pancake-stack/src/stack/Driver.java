package stack;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import javax.swing.Timer;

import edu.rutgers.cs112.LL.LLNode;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Driver {
    
    private static final int SECONDS = 10; // the timeout duration

    private Timer actionTimer; // Java swing timer. we use a singular timer to only allow one action at a time (in case of infinte loops)
    private static final int TIMEOUT = SECONDS * 1000; // convert seconds to milliseconds

    private PancakeStack pancakes;
    private JFrame frame;
    private DisplayPanel displayPanel;

    private class DisplayPanel extends JPanel {
        private DisplayPanel(){
            super();
        }

        @Override
        public Dimension getPreferredSize() {
            int totalHeight = 20 * pancakes.getNumPancakes() + 200; 
            int newHeight = Math.max(totalHeight, 400); 
            return new Dimension(600, newHeight); 
        }


        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            LLNode<String> ptr = pancakes.getStack();
            String[] flavors = new String[pancakes.getNumPancakes()];

            
            for(int i = flavors.length - 1; i >= 0; i--){
                flavors[i] = ptr.getData();
                ptr = ptr.getNext();
            }

            int x = displayPanel.getWidth() / 2;
            int y = displayPanel.getHeight() - 120;

            g.setColor(new Color(139, 0, 0)); 
            g.fillOval(x - 150, y, 300, 100);
            g.setColor(Color.BLACK);
            g.drawOval(x - 150, y, 300, 100);

            Color pancakeColor = new Color(255, 204, 153); 
            Color flippedColor = new Color(255, 229, 204);    
            
            for (int i = 0; i < flavors.length; i++) {
                g.setColor(flippedColor);
                g.fillOval(x - 100, y + i * -20 + 10, 200, 40);
                g.setColor(pancakeColor);
                g.fillOval(x - 100, y + i * -20, 200, 40);
                if(flavors[i].equals("chocolate")){
                    g.setColor(new Color(102, 51, 0));
                    g.fillOval(x - 100 + 50, y + i * -20 + 15, 8, 8);
                    g.fillOval(x - 100 + 100, y + i * -20 + 10, 8, 8);
                    g.fillOval(x - 100 + 165, y + i * -20 + 25, 8, 8);
                    g.fillOval(x - 100 + 128, y + i * -20 + 3, 8, 8);
                    g.fillOval(x - 100 + 75, y + i * -20 + 27, 8, 8);
                    g.fillOval(x - 100 + 30, y + i * -20 + 8, 8, 8);
                }
                else if(flavors[i].equals("blueberry")){
                    g.setColor(new Color(70, 70, 180));
                    g.fillOval(x - 100 + 100, y + i * -20 + 10, 8, 8);
                    g.fillOval(x - 100 + 50, y + i * -20 + 5, 8, 8);
                    g.fillOval(x - 100 + 140, y + i * -20 + 25, 8, 8);
                    g.fillOval(x - 100 + 75, y + i * -20 + 20, 8, 8);
                    g.fillOval(x - 100 + 165, y + i * -20 + 10, 8, 8);
                    g.fillOval(x - 100 + 10, y + i * -20 + 19, 8, 8);
                }
                else{
                    g.setColor(new Color(139, 69, 19));

                    for(int s = 0; s < 4; s++){
                        int swirlWidth = 180 - s * 40;
                        int swirlHeight = 35 - s * 8;
                        int swirlX = x - swirlWidth / 2;
                        int swirlY = y + i * -20 + 20 - swirlHeight / 2;
                        g.drawOval(swirlX, swirlY, swirlWidth, swirlHeight);
                    }
                }
            }
        }
    }

    public Driver(){
        frame = new JFrame("Pancake Stack Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));

        pancakes = new PancakeStack();

        displayPanel = new DisplayPanel();

        JScrollPane scroller = new JScrollPane(displayPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();

        JButton chocolateButton = new JButton("Cook Chocolate");
        JButton blueberryButton = new JButton("Cook Blueberry");
        JButton cinnamonButton = new JButton("Cook Cinnamon");
        JButton eatButton = new JButton("Eat Pancake");
        JButton isEmptyButton = new JButton("Is Empty?");
        JButton reverseButton = new JButton("Reverse Stack");

        JLabel numberOfPancakes = new JLabel("Number of pancakes: " + pancakes.getNumPancakes());

        chocolateButton.addActionListener(e -> runStudentCode(
            () -> {
                pancakes.push("chocolate");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));

        blueberryButton.addActionListener(e -> runStudentCode(
            () -> {
                pancakes.push("blueberry");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));

        cinnamonButton.addActionListener(e -> runStudentCode(
            () -> {
                pancakes.push("cinnamon");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));

        eatButton.addActionListener(e -> runStudentCode(
            () -> {
                String eatenPancakeType = pancakes.pop();
                if(eatenPancakeType == null){
                    JOptionPane.showMessageDialog(null, "Pop returned null, there might be no pancakes on the plate", "", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    System.out.println(eatenPancakeType + " pancake eaten");
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));

        isEmptyButton.addActionListener(e -> runStudentCode(
            () -> {
                boolean empty = pancakes.isEmpty();
                if(empty){
                    JOptionPane.showMessageDialog(null, "The pancake stack is empty right now", "", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "The pancake stack is NOT empty", "", JOptionPane.INFORMATION_MESSAGE);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));

        reverseButton.addActionListener(e -> runStudentCode(
            () -> {
                pancakes.reverse();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        numberOfPancakes.setText("Number of pancakes: " + pancakes.getNumPancakes());
                        displayPanel.revalidate();
                        displayPanel.repaint();
                        scroller.revalidate();
                        scroller.repaint();
                    }
                });
            }
        ));


        textPanel.add(numberOfPancakes);

        buttonPanel.add(chocolateButton);
        buttonPanel.add(blueberryButton);
        buttonPanel.add(cinnamonButton);
        buttonPanel.add(eatButton);
        buttonPanel.add(isEmptyButton);
        buttonPanel.add(reverseButton);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        buttonPanel.setPreferredSize(new Dimension(600, 80));

        controlPanel.add(buttonPanel);
        controlPanel.add(textPanel);

        containerPanel.add(controlPanel);

        scroller.setPreferredSize(new Dimension(600, 410));
        containerPanel.add(scroller);

        frame.add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void runStudentCode(Runnable task) {
        this.runStudentCode(Executors.callable(task));
    }

    private <T> void runStudentCode(Callable<T> task) {  
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    task.call();
                } 
                catch (NullPointerException npe){
                    SwingUtilities.invokeLater(() ->  npe.printStackTrace());
                    JOptionPane.showMessageDialog(null, "Your code threw a NullPointerException, see the terminal for the error stack trace", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                    SwingUtilities.invokeLater(() ->  e.printStackTrace());
                }
                this.done();
                actionTimer = null;
                return null;
            } 
        }; 
        actionTimer = new Timer(TIMEOUT, e -> {
            if (!worker.isDone()) {
                worker.cancel(true);
                actionTimer = null;
                int choice = JOptionPane.showConfirmDialog( null, 
                    "Student code is taking more time than expected -- if you're not using the debugger right now, there may be an infinite loop.\nWould you like to close the Driver?", 
                    "Warning", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose(); 
                    System.exit(0);
                }
            }
        });
        actionTimer.start();
        worker.execute(); 
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Driver();
            }
        });
    }
}
