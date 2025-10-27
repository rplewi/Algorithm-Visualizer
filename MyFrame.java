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
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import sortingAlgorithms.SortAlgorithm;
import sortingAlgorithms.quickSort;

public class MyFrame extends JFrame{
    private int[] array;
    private int sortNum = 0; // Placeholder for when I want specified numbers
    private SortAlgorithm currentAlgorithm;
    private Timer timer;
    private int barHeightMultiplier = 10; // Controls how tall the bars are
    
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
        timer = new Timer(30, e -> {
            boolean done = currentAlgorithm.step();
            visualizer.repaint();
            if (done) {
                ((Timer) e.getSource()).stop();
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
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                int barWidth = width / array.length;
            
                quickSort qs = (currentAlgorithm instanceof quickSort) ? (quickSort) currentAlgorithm : null;
                Set<Integer> pivotIndices = qs != null ? qs.getPivotIndices() : Collections.emptySet();
                Set<Integer> currentIndices = qs != null ? qs.getCurrentIndices() : Collections.emptySet();
                Set<Integer> partitionIndices = qs != null ? qs.getPartitionIndices() : Collections.emptySet();
                Set<Integer> rangeIndices = qs != null ? qs.getRangeIndices() : Collections.emptySet();

                for (int i = 0; i < array.length; i++) {
                    if (pivotIndices.contains(i)) {
                        g.setColor(Color.RED);      // Pivot - RED
                    } else if (currentIndices.contains(i)) {
                        g.setColor(Color.YELLOW);   // Current element being compared - YELLOW
                    } else if (partitionIndices.contains(i)) {
                        g.setColor(Color.GREEN);    // Partition position - GREEN
                    } else if (rangeIndices.contains(i)) {
                        g.setColor(Color.BLUE);     // Range being partitioned - BLUE
                    } else {
                        g.setColor(Color.WHITE);    // Regular - WHITE
                    }
                    int barHeight = array[i] * barHeightMultiplier;
                    g.fillRect(i * barWidth, height - barHeight, barWidth - 1, barHeight);
                }
            }
        };

        JPanel nav = new JPanel();
        nav.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                randomizeArray();
                visualizer.repaint();
            }
        });
        JButton sort = new JButton("Start Sorting");
        Choice dropdown = new Choice();
        dropdown.add("Quick-Sort");
        dropdown.add("Merge-Sort");
        dropdown.add("Bucket-Sort");
        dropdown.add("Placeholder");
        sort.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String usrSelect = (String) dropdown.getSelectedItem();
                System.out.println(usrSelect);
                switch (usrSelect){
                case "Quick-Sort" :
                    currentAlgorithm = new quickSort();
                    break;
                //case "Merge-Sort": currentAlgorithm = new mergeSort();
                //case "Bucket-Sort": currentAlgorithm = new bucketSort();
                default : 
                    JOptionPane.showMessageDialog(null, "please Choose a better option");
                    break;

                // Add more options in the future, when algos get updated, this should be modular, so not much work needs to be done.
                }
                currentAlgorithm.reset(array);
                beginSort(visualizer);
            }
        });

        nav.add(dropdown);
        nav.add(reset);
        nav.add(sort);
        c.add(nav, BorderLayout.NORTH);
        visualizer.setBackground(Color.BLACK);
        c.add(visualizer, BorderLayout.CENTER);

        setVisible(true);
        
        /* 
        setBounds(0, 0, 700, 500); // CoordsX, CoordsY, Length, Width sizes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Algorithm-Visualizer");
        setBackground(Color.DARK_GRAY);

        Container c = getContentPane();
        c.setLayout(new GridLayout(1, 3, 10, 10));
        
        JPanel btnScroll = new JPanel();
        btnScroll.setLayout(new BoxLayout(btnScroll, BoxLayout.Y_AXIS));
        
        JButton btnQuick = new JButton("Quick-Sort");
        btnQuick.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sortType = "Quick-Sort";
                System.out.println("working?");
            }
        });
        btnScroll.add(btnQuick);

        JButton btnMerge = new JButton("Merge-Sort");
        btnQuick.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sortType = "Merge-Sort";
                System.out.println("working?");
            }
        });
        btnScroll.add(btnMerge);

        JButton btnBucket = new JButton("Bucket-Sort");
        btnQuick.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sortType = "Bucket-Sort";
                System.out.println("working?");
                // What do I want to do with this
            }
        });
        btnScroll.add(btnBucket);

        for (int i = 0; i < 20; i++){
            JButton button = new JButton("PlaceHolder " + i);
            btnScroll.add(button);
        }
        JScrollPane scrollPaneBtn = new JScrollPane(btnScroll);
        scrollPaneBtn.setPreferredSize(new Dimension(100, 200));
        c.add(scrollPaneBtn);

        JPanel numSelect = new JPanel();
        numSelect.setLayout(new BoxLayout(numSelect, BoxLayout.Y_AXIS));
        JLabel inpTitle = new JLabel("Please Input the Amount of data you want sorted, ('1-1000')");
        inpTitle.setSize(1, 50);
        numSelect.add(inpTitle);
        JTextArea userNum = new JTextArea(1,5);
        userNum.setSize(1, 6);
        userNum.setLineWrap(true);
        userNum.setWrapStyleWord(true);
        userNum.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        numSelect.add(userNum);
        c.add(numSelect);


        JPanel confirm = new JPanel();
        confirm.setLayout(new BoxLayout(confirm, BoxLayout.Y_AXIS));
        JLabel usrConf = new JLabel("You chose: " + sortType);
        confirm.add(usrConf);
        JLabel num = new JLabel("and " + sortNum + " data values");
        confirm.add(num);
        JButton confBtn = new JButton("confirm?");
        confirm.add(confBtn);
        c.add(confirm);
        */

    }
    public MyFrame(){
        setupGUI();
    }
}
