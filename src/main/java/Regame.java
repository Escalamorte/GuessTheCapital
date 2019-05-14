import javax.swing.*;

public class Regame extends JDialog{
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel imageLabel;
    private JLabel textLabel;

    private Regame() {
        AudioPlay audioPlay = new AudioPlay();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        textLabel.setText("Game Over...");
        buttonCancel.setText("Выход");
        audioPlay.gameOver();

        buttonCancel.addActionListener(e -> System.exit(0));
    }

    static void run() {
        Regame dialog = new Regame();
        dialog.pack();
        dialog.setVisible(true);
    }
}