package mario;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;


public class Driver {
    private JFrame frame;
    private JPanel mainPanel;
    private MarioSort marioSort;
    private JPanel outputPanel;
    private JComboBox<String> fileComboBox;
    private JButton loadButton;
    private JButton sortButton;
    private JLabel sortTypeLabel;

    private static final Color FIRST = new Color(255,255,200);
    private static final Color SECOND = new Color(220,235,255);
    private static final Color THIRD = new Color(220,255,220);


    private static final String[] FILES = {
        "marioKart1.csv", "marioKart2.csv", "marioParty1.csv", "marioParty2.csv",
        "marioParty3.csv", "marioParty4.csv", "marioParty5.csv"
    };

    public Driver() {
        marioSort = new MarioSort();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Mario Games Sorter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
 
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        fileComboBox = new JComboBox<>(FILES);
        fileComboBox.setSelectedIndex(-1);
        fileComboBox.addActionListener(e -> {
            String selected = (String)fileComboBox.getSelectedItem();
            if (selected == null) return;
            if (selected.toLowerCase().contains("kart")) {
                sortTypeLabel.setText("Sort: Selection Sort (Mario Kart)");
            } else if (selected.toLowerCase().contains("party")) {
                sortTypeLabel.setText("Sort: Quick Sort (Mario Party)   ");
            } else {
                sortTypeLabel.setText("Select a file and press Load!");
            }
        });
        loadButton = new JButton("Load");
        sortButton = new JButton("Sort!");
        sortTypeLabel = new JLabel("Select a file and press Load!");
        sortTypeLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        topBar.add(new JLabel("Input File:"));
        topBar.add(fileComboBox);
        topBar.add(loadButton);
        topBar.add(sortButton);
        topBar.add(sortTypeLabel);
 
        outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.pack();

        loadButton.addActionListener(e -> handleLoad());
        sortButton.addActionListener(e -> handleSort());
    }

    private void handleLoad() {
        String filename = (String) fileComboBox.getSelectedItem();
        if (filename == null) return;
        if (filename.toLowerCase().contains("kart")) {
            displayUnsortedMarioKartResults(filename);
            frame.pack();
        } else if (filename.toLowerCase().contains("party")) {
            displayUnsortedMarioPartyResults(filename);
            frame.pack();
        } else {
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Unknown file type."), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }

    // Display unsorted Mario Kart results
    private void displayUnsortedMarioKartResults(String filename) {
        try {
            marioSort.readData(filename);
            Racer[] racers = marioSort.getMarioKart();
            if (racers == null || racers.length == 0) throw new Exception("No data");
            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBackground(Color.WHITE);

            JPanel header = new JPanel(new GridLayout(1, 5));
            header.setBackground(new Color(230,230,230));
            header.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            header.add(new JLabel("Orig", JLabel.CENTER));
            header.add(new JLabel("Name", JLabel.CENTER));
            header.add(new JLabel("Lap 1", JLabel.CENTER));
            header.add(new JLabel("Lap 2", JLabel.CENTER));
            header.add(new JLabel("Lap 3", JLabel.CENTER));
            header.add(new JLabel("Avg", JLabel.CENTER));
            listPanel.add(header);

            DecimalFormat df = new DecimalFormat("0.0");
            for (int i = 0; i < racers.length; i++) {
                JPanel row = new JPanel(new GridLayout(1, 6));
                row.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(200,200,200)));
                row.setBackground(Color.WHITE);
                row.add(new JLabel(String.valueOf(i+1), JLabel.CENTER));
                row.add(new JLabel(racers[i].getName(), JLabel.CENTER));
                int[] laps = racers[i].getLapTime();
                row.add(new JLabel(laps[0] + " s", JLabel.CENTER));
                row.add(new JLabel(laps[1] + " s", JLabel.CENTER));
                row.add(new JLabel(laps[2] + " s", JLabel.CENTER));
                double avg = (laps[0] + laps[1] + laps[2]) / 3.0;
                row.add(new JLabel(df.format(avg) + " s", JLabel.CENTER));
                listPanel.add(row);
            }

            outputPanel.removeAll();
            outputPanel.setLayout(new BorderLayout());
            outputPanel.add(listPanel, BorderLayout.NORTH);
            outputPanel.revalidate();
            outputPanel.repaint();
        } catch (Exception ex) {
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Error loading Mario Kart data: " + ex.getMessage()), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }
 
    private void displayUnsortedMarioPartyResults(String filename) {
        try {
            marioSort.readData(filename);
            Player[] players = marioSort.getMarioParty();
            if (players == null || players.length == 0) throw new Exception("No data");
            JPanel colPanel = new JPanel();
            colPanel.setLayout(new BoxLayout(colPanel, BoxLayout.Y_AXIS));
            colPanel.setBackground(Color.WHITE);

            for (int i = 0; i < players.length; i++) {
                JPanel playerPanel = new JPanel();
                playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
                playerPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,0,1,new Color(200,200,200)),
                        BorderFactory.createEmptyBorder(10,10,10,10)));
                playerPanel.setBackground(Color.WHITE);

                JLabel name = new JLabel(players[i].getName(), JLabel.CENTER);
                name.setFont(new Font("Monospaced", Font.BOLD, 14));
                JLabel stars = new JLabel("Stars: " + players[i].getStars(), JLabel.CENTER);
                JLabel coins = new JLabel("     Coins: " + players[i].getCoins(), JLabel.CENTER);
                playerPanel.add(stars);
                playerPanel.add(coins);
                playerPanel.add(Box.createVerticalStrut(10));
                playerPanel.add(name);
                colPanel.add(playerPanel);
            }

            outputPanel.removeAll();
            outputPanel.setLayout(new BorderLayout());
            outputPanel.add(colPanel, BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        } catch (Exception ex) {
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Error loading Mario Party data: " + ex.getMessage()), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    } 

    private void handleSort() {
        String filename = (String) fileComboBox.getSelectedItem();
        if (filename == null) return;
        if (filename.toLowerCase().contains("kart")) { 
            displayMarioKartResults(filename);
            frame.pack();
        } else if (filename.toLowerCase().contains("party")) { 
            displayMarioPartyResults(filename);
            frame.pack();
        } else {
            sortTypeLabel.setText("Unknown file type");
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Unknown file type."), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }

    private void displayMarioKartResults(String filename) {
        try {
            marioSort.readData(filename);
            marioSort.sortMarioKart();
            Racer[] racers = marioSort.getMarioKart(); 

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBackground(Color.WHITE);
 
            JPanel header = new JPanel(new GridLayout(1, 5));
            header.setBackground(new Color(230,230,230));
            header.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            header.add(new JLabel("Place", JLabel.CENTER));
            header.add(new JLabel("Name", JLabel.CENTER));
            header.add(new JLabel("Lap 1", JLabel.CENTER));
            header.add(new JLabel("Lap 2", JLabel.CENTER));
            header.add(new JLabel("Lap 3", JLabel.CENTER));
            header.add(new JLabel("Avg", JLabel.CENTER));
            listPanel.add(header);

            DecimalFormat df = new DecimalFormat("0.0");
            for (int i = 0; i < racers.length; i++) {
                JPanel row = new JPanel(new GridLayout(1, 6));
                row.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(200,200,200)));
                if (i == 0) row.setBackground(FIRST);
                else if (i == 1) row.setBackground(SECOND);
                else if (i == 2) row.setBackground(THIRD);
                else row.setBackground(Color.WHITE);
                row.add(new JLabel(String.valueOf(i+1), JLabel.CENTER));
                row.add(new JLabel(racers[i].getName(), JLabel.CENTER));
                int[] laps = racers[i].getLapTime();
                row.add(new JLabel(laps[0] + " s", JLabel.CENTER));
                row.add(new JLabel(laps[1] + " s", JLabel.CENTER));
                row.add(new JLabel(laps[2] + " s", JLabel.CENTER));
                row.add(new JLabel(df.format(racers[i].getAverage()) + " s", JLabel.CENTER));
                listPanel.add(row);
            }

            outputPanel.removeAll();
            outputPanel.setLayout(new BorderLayout());
            outputPanel.add(listPanel, BorderLayout.NORTH);
            outputPanel.revalidate();
            outputPanel.repaint();
        } catch (Exception ex) {
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Error loading Mario Kart data: " + ex.getMessage()), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }

    private void displayMarioPartyResults(String filename) {
        try {
            marioSort.readData(filename);
            marioSort.sortMarioParty();
            Player[] players = marioSort.getMarioParty();

            JPanel colPanel = new JPanel();
            colPanel.setLayout(new BoxLayout(colPanel, BoxLayout.Y_AXIS));
            colPanel.setBackground(Color.WHITE);

            for (int i = 0; i < players.length; i++) {
                JPanel playerPanel = new JPanel();
                playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
                playerPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,0,1,new Color(200,200,200)),
                        BorderFactory.createEmptyBorder(10,10,10,10)));
                if (i == 0) playerPanel.setBackground(FIRST);
                else if (i == 1) playerPanel.setBackground(SECOND);
                else if (i == 2) playerPanel.setBackground(THIRD);
                else playerPanel.setBackground(Color.WHITE);

                JLabel name = new JLabel(players[i].getName(), JLabel.CENTER);
                name.setFont(new Font("Monospaced", Font.BOLD, 14));
                JLabel stars = new JLabel("Stars: " + players[i].getStars(), JLabel.CENTER);
                JLabel coins = new JLabel("     Coins: " + players[i].getCoins(), JLabel.CENTER);
                playerPanel.add(stars);
                playerPanel.add(coins);
                playerPanel.add(Box.createVerticalStrut(10));
                playerPanel.add(name);
                colPanel.add(playerPanel);
            }

            outputPanel.removeAll();
            outputPanel.setLayout(new BorderLayout());
            outputPanel.add(colPanel, BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        } catch (Exception ex) {
            outputPanel.removeAll();
            outputPanel.add(new JLabel("Error loading Mario Party data: " + ex.getMessage()), BorderLayout.CENTER);
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Driver::new);
    }
}
