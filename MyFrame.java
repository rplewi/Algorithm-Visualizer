import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import sortingAlgorithms.SortAlgorithm;
import sortingAlgorithms.quickSort;
import sortingAlgorithms.bubbleSort;
import sortingAlgorithms.insertionSort;



public class MyFrame extends JFrame{
    private int[] array;
    private SortAlgorithm currentAlgorithm;
    private Timer timer;
    private int barHeightMultiplier = 10; // Controls how tall the bars are
    private boolean done = false;
    private JButton stop;
    private JButton sort;
    private Choice dropdown;
    private JButton reset;
    private int algoSpeed = 15; // SPEED IN ms FOR ALGORITHM.
    
    public void randomizeArray() {
        int size = array.length;

        // Fill with ordered numbers (e.g., 1 to size)
        for (int i = 0; i < size; i++) {
            array[i] = i + 1; // ensures uniqueness
        }

        // Shuffle them randomly
        List<Integer> list = new ArrayList<>();
        for (int num : array) list.add(num);
        Collections.shuffle(list);

        // Copy back into the array
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
    }   

    public void beginSort(JPanel visualizer) {
        timer = new Timer(algoSpeed, e -> {
            done = currentAlgorithm.step();
            visualizer.repaint();
            if (done) {
                ((Timer) e.getSource()).stop();
                stop.setEnabled(false);
                // User must reset the data before starting again.
                dropdown.setEnabled(true);
                reset.setEnabled(true);
            }
        });
        timer.start();
    }

    public void setupGUI(){
        setBounds(100,200,600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Algorithm-Visualizer");
        Container c = getContentPane();
        array = new int[50];
        randomizeArray();

        JPanel visualizer = new JPanel(){
            // Color mapping for different visualization states
            // Each algorithm can use any of these keys, and we'll color them accordingly
            private final Map<String, Color> colorMap = new HashMap<String, Color>() {{
                put("pivot", Color.RED);
                put("current", Color.YELLOW);
                put("compare", Color.YELLOW);
                put("partition", Color.GREEN);
                put("range", Color.BLUE);
                put("sorted", Color.GREEN);
            }};
            
            // Priority order for coloring (first match wins)
            private final String[] priorityOrder = {"pivot", "current", "compare", "partition", "range", "sorted"};
            
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                int barWidth = width / array.length;
            
                // Get visualization state from current algorithm (works with any algorithm!)
                Map<String, Set<Integer>> vizState = (currentAlgorithm != null) 
                    ? currentAlgorithm.getVisualizationState() 
                    : Collections.emptyMap();

                for (int i = 0; i < array.length; i++) {
                    Color barColor = Color.WHITE; // default color
                    
                    // Check each priority level to determine color
                    for (String key : priorityOrder) {
                        Set<Integer> indices = vizState.get(key);
                        if (indices != null && indices.contains(i)) {
                            barColor = colorMap.get(key);
                            break; // Use first matching color
                        }
                    }
                    
                    g.setColor(barColor);
                    int barHeight = array[i] * barHeightMultiplier;
                    g.fillRect(i * barWidth, height - barHeight, barWidth - 1, barHeight);
                    if (done){ // checks if visualizer is done and resets colors to white.
                        vizState.clear();
                    }
                    
                }
            }
        };
        
        JPanel nav = new JPanel();
        nav.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                randomizeArray();
                visualizer.repaint();
                sort.setEnabled(true);
            }
        });
        sort = new JButton("Start Sorting");
        stop = new JButton("Stop Sorting");
        stop.setEnabled(false);
        dropdown = new Choice();
        dropdown.add("Quick-Sort");
        dropdown.add("Bubble-Sort");
        dropdown.add("Insertion-Sort");
        dropdown.add("More, coming soon!");
        sort.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String usrSelect = (String) dropdown.getSelectedItem();
                System.out.println(usrSelect);
                switch (usrSelect){
                case "Quick-Sort" :
                    currentAlgorithm = new quickSort();
                    break;
                case "Bubble-Sort": 
                    currentAlgorithm = new bubbleSort();
                    break;
                case "Insertion-Sort":
                    currentAlgorithm = new insertionSort();
                    break;
                default : 
                    JOptionPane.showMessageDialog(null, "Please choose a better option");
                    break;
                // Add more options in the future, when algos get updated, this should be modular, so not much work needs to be done.
                }
                reset.setEnabled(false);
                dropdown.setEnabled(false);
                sort.setEnabled(false);
                stop.setEnabled(true);
                currentAlgorithm.reset(array);
                beginSort(visualizer);
            }
        });
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timer.stop();
                sort.setEnabled(true);
                reset.setEnabled(true);
                dropdown.setEnabled(true);
                stop.setEnabled(false);
            }
        });
        nav.add(dropdown);
        nav.add(reset);
        nav.add(sort);
        nav.add(stop);
        c.add(nav, BorderLayout.NORTH);
        visualizer.setBackground(Color.BLACK);
        c.add(visualizer, BorderLayout.CENTER);
        setVisible(true);

    }
    public MyFrame(){
        setupGUI();
    }
}
