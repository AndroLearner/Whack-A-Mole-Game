import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
/**
 * @author priyanka deoskar
 */
/**
 * This class contains code for Whack A Mole game.
 * @author priyanka deoskar
 */
public class Game implements ActionListener, Runnable {
      /**
       * For displaying hole with no mole(down configuration).
       */
      private static final String FROWNY = ":-(";
      /**
       * For displaying hole with mole(up configuration).
       */
      private static final String SMILEY = ":-)";
      /**
       * For displaying successful hit(hit configuration).
       */
      private static final String HIT = "HIT";
      /**
       * array of JButtons for holes.
       */
      private JButton[] holes = new JButton[64];
      /**
       * for starting the game.
       */
      private JButton start;
      /**
       * for displaying remaining time.
       */
      private JTextField timeBox;
      /**
       * for displaying score.
       */
      private JTextField scoreBox;
      /**
       * for counting time.
       */
      private int count;
      /**
       * for counting player's score.
       */
      private int score;
      /**
       * default constructor.
       */
      public Game() {

        JFrame frame = new JFrame("Whack A Mole");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 100, 500, 550);

        JPanel jPanel = new JPanel();
        start = new JButton("Start");
        jPanel.add(start);
        JLabel time = new JLabel("Time Left:");
        jPanel.add(time);
        timeBox = new JTextField(7);
        timeBox.setEditable(false);
        jPanel.add(timeBox);
        JLabel pscore = new JLabel("Score:");
        jPanel.add(pscore);
        scoreBox = new JTextField(7);
        scoreBox.setText("0");
        scoreBox.setEditable(false);
        jPanel.add(scoreBox);

        JPanel jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(450, 450));
        jPanel1.setLayout(new GridLayout(8, 8, 5, 5));
        jPanel.add(jPanel1);

        for (int i = 0; i < holes.length; i++) {
            holes[i] = new JButton(FROWNY);
            Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
            holes[i].setBorder(border);
            jPanel1.add(holes[i]);
            holes[i].addActionListener(this);
        }
        start.addActionListener(this);
        frame.setContentPane(jPanel);
        frame.setVisible(true);
      }
    /**
     * contains code for actions on clicking start button and mole button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            start.setEnabled(false);
            Thread timer = new Thread(this);
            timer.start();

            for (int i = 0; i < holes.length; i++) {
                holes[i].setText(FROWNY);
                Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
                holes[i].setBorder(border);
                MyRunnable r = new MyRunnable(holes[i]);
                Thread thread = new Thread(r);
                thread.start();
            }
        } else {
                JButton temp = (JButton) e.getSource();
                if (temp.getText() == SMILEY) {
                    score = score + 1;
                    scoreBox.setText(Integer.toString(score));
                    temp.setText(HIT);
                    Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.blue);
                    temp.setBorder(border);
                }
          }
    }
    /**
     * run() method of Runnable class overriden by Game class.
     */
    @Override
    public void run() {
        count = 20;
        try {
            while (count > 0) {
                timeBox.setText(Integer.toString(count));
                Thread.sleep(1000);
                count = count - 1;
                timeBox.setText(Integer.toString(count));
            }
            for (int i = 0; i < holes.length; i++) {
                holes[i].setText(FROWNY);
                Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
                holes[i].setBorder(border);
            }
            Thread.sleep(5000);
            start.setEnabled(true);
            score = 0;
            scoreBox.setText("0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Nested Class.
     * @author priyanka deoskar
     */
    private class MyRunnable implements Runnable {
        /**
         * reference variable of JButton class.
         */
        private JButton button;
        /**
         * creating object of Random class.
         */
        private Random random = new Random();
        /**
         * parameterized constructor.
         * @param button fetches the hole[i] created in actionPerformed().
         */
        public MyRunnable(JButton button) {
            this.button = button;
        }
        /**
         * run() method of Runnable class overriden by MyRunnable class.
         */
        @Override
        public void run() {
            try {
               int delay = (random.nextInt(400) * 10);
               Thread.sleep(delay);
               while (count > 0) {
                   button.setText(SMILEY);
                   Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.green);
                   button.setBorder(border);
                   int uptime = 1000 + (random.nextInt(300) * 10);
                   Thread.sleep(uptime);
                   button.setText(FROWNY);
                   Border border1 = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
                   button.setBorder(border1);
                   Thread.sleep(2000);
               }
               if (count == 0) {
                   button.setText(FROWNY);
                   Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
                   button.setBorder(border);
               }
            } catch (IllegalArgumentException | InterruptedException e) {
                 e.printStackTrace();
            }
        }
    }
    /**
     * this is main class.
     * @param args array of String arguments.
     */
    public static void main(String[] args) {
        new Game();
    }
}
