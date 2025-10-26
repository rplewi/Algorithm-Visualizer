import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;


public class MyFrame extends JFrame{
    private JTextArea textArea;


    public void setupGUI(){
        setBounds(0, 0, 700, 500); // CoordsX, CoordsY, Length, Width sizes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Algorithm-Visualiser");
        setBackground(Color.DARK_GRAY);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        
        
        // This will be used later when we do the overall layout of the JPane.
        JPanel btnScroll = new JPanel();
        btnScroll.setLayout(new BoxLayout(btnScroll, BoxLayout.Y_AXIS));
        JButton btnQuick = new JButton("Quick-Sort");
        btnScroll.add(btnQuick);
        JButton btnMerge = new JButton("Merge-Sort");
        btnScroll.add(btnMerge);
        JButton btnBucket = new JButton("Bucket-Sort");
        btnScroll.add(btnBucket);
        for (int i = 0; i < 20; i++){
            JButton button = new JButton("PlaceHolder " + i);
            btnScroll.add(button);
        }

        JScrollPane scrollPaneBtn = new JScrollPane(btnScroll);

        c.add(scrollPaneBtn, BorderLayout.WEST);


        
        JPanel numSelect = new JPanel();
        numSelect.setLayout(new BoxLayout(numSelect, BoxLayout.Y_AXIS));
        JLabel inpTitle = new JLabel("Please Input the Amount of data you want sorted, ('1-1000')");
        numSelect.add(inpTitle);
        JTextArea userNum = new JTextArea(1,5);
        numSelect.add(userNum);
        c.add(numSelect, BorderLayout.CENTER);
        


        

    }
    public MyFrame(){
        setupGUI();
    }
}
