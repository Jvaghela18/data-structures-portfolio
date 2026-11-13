package queue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.awt.Color;

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
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import javax.swing.Timer;

import edu.rutgers.cs112.node.LLNode;

public class Driver {

    private static final int SECONDS = 10; // the timeout duration

    private Timer actionTimer; // Java swing timer. we use a singular timer to only allow one action at a time (in case of infinte loops)
    private static final int TIMEOUT = SECONDS * 1000; // convert seconds to milliseconds

    private ThemeParkQueue queue1;
    private ThemeParkQueue queue2;

    private RideQueueDisplay rideQueue1;
    private RideQueueDisplay rideQueue2;

    private JLabel lengthLabel1;
    private JLabel lengthLabel2;

    private final Color[] possibleColors = {
            Color.RED, Color.BLUE, Color.GREEN,
            Color.YELLOW, Color.PINK, Color.ORANGE
    };

    private final String[] colorNames = {
            "RED", "BLUE", "GREEN",
            "YELLOW", "PINK", "ORANGE"
    };

    private int nextColor1 = 0;
    private int nextColor2 = 0;

    public Driver() {

        JFrame display = new JFrame("Theme Park Queue Lab");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel window = new JPanel();
        window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));

        queue1 = new ThemeParkQueue();
        queue2 = new ThemeParkQueue();

        rideQueue1 = new RideQueueDisplay(queue1);
        rideQueue2 = new RideQueueDisplay(queue2);

        lengthLabel1 = new JLabel("Queue 1 Length: 0");
        lengthLabel2 = new JLabel("Queue 2 Length: 0");

        JScrollPane scroll1 = new JScrollPane(
                rideQueue1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollPane scroll2 = new JScrollPane(
                rideQueue2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controls1 = new JPanel();

        JButton enqueue1 = new JButton("Enqueue Q1");
        enqueue1.addActionListener(e -> runStudentCode(
            () -> {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        queue1.enqueue(colorNames[nextColor1]);
                        nextColor1 = (nextColor1 + 1) % colorNames.length;
                        refresh();
                    }
                });
            }
        ));

        

        JButton dequeue1 = new JButton("Dequeue Q1");
        dequeue1.addActionListener(e -> runStudentCode(
            () -> {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        System.out.println(queue1.dequeue());
                        refresh();
                    }
                });
            }

        ));

        controls1.add(enqueue1);
        controls1.add(dequeue1);
        controls1.add(lengthLabel1);

        JPanel controls2 = new JPanel();

        JButton enqueue2 = new JButton("Enqueue Q2");
        enqueue2.addActionListener(e -> runStudentCode(
            () -> {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        queue2.enqueue(colorNames[nextColor2]);
                        nextColor2 = (nextColor2 + 1) % colorNames.length;
                        refresh();
                    }
                });
            }
        ));

        JButton dequeue2 = new JButton("Dequeue Q2");
        dequeue2.addActionListener(e -> runStudentCode(
            () -> {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        System.out.println(queue2.dequeue());
                        refresh();
                    }
                });
            
            }
        ));

        controls2.add(enqueue2);
        controls2.add(dequeue2);
        controls2.add(lengthLabel2);

        JPanel interleavePanel = new JPanel();

        JButton interleave21 = new JButton("Interleave Q2 into Q1");
        interleave21.addActionListener(e -> runStudentCode(
            () -> {
                queue1.interleaveQueue(queue2);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        refresh();
                    }
                });
            }
        ));



        JButton interleave12 = new JButton("Interleave Q1 into Q2");
        interleave12.addActionListener(e -> runStudentCode(
            () -> {
                queue2.interleaveQueue(queue1);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        refresh();
                    }
                });

            }
        ));

        interleavePanel.add(interleave21);
        interleavePanel.add(interleave12);

        window.add(controls1);
        window.add(scroll1);

        window.add(controls2);
        window.add(scroll2);

        window.add(interleavePanel);

        display.add(window);
        display.setResizable(false);
        display.pack();
        display.setVisible(true);
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            lengthLabel1.setText("Queue 1 Length: " + queue1.getLineLength());
            lengthLabel2.setText("Queue 2 Length: " + queue2.getLineLength());

            rideQueue1.revalidate();
            rideQueue1.repaint();

            rideQueue2.revalidate();
            rideQueue2.repaint();
        });
    }

    private class RideQueueDisplay extends JPanel {

        private ThemeParkQueue queue;

        private RideQueueDisplay(ThemeParkQueue q) {
            this.queue = q;
            this.setPreferredSize(new Dimension(420, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int x = 50;
            int y = 100;

            for (LLNode<String> ptr = queue.getFront();
                 ptr != null;
                 ptr = ptr.getNext()) {

                for (int i = 0; i < colorNames.length; i++) {
                    if (colorNames[i].equals(ptr.getData())) {
                        g.setColor(possibleColors[i]);
                    }
                }

                g.fillOval(x - 40, y - 50, 50, 120);  // body
                g.fillOval(x - 40, y - 90, 50, 50);   // head
                x += 60;
            }

            this.setPreferredSize(new Dimension(60 * queue.getLineLength() + 20, 200));
        }
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
                    //frame.dispose(); 
                    System.exit(0);
                }
            }
        });
        actionTimer.start();
        worker.execute(); 
    }

    public static void main(String args[]) {
        ThemeParkQueue queue = new ThemeParkQueue();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Driver();
            }
        });
    }
}