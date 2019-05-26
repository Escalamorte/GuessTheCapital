import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Regame extends JDialog{
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel imageLabel;
    private JLabel textLabel;
    private JButton renewBtn;

    private Regame() {
        //MainWindow mainWindow = new MainWindow();
        AudioPlay audioPlay = new AudioPlay();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        textLabel.setText("Game Over...");
        buttonCancel.setText("Выход");
        renewBtn.setText("Играть сначала");
        audioPlay.gameOver();
        setImage();

        renewBtn.addActionListener(e -> {
            new MainWindow().startNewGame();
            dispose();
        });

        buttonCancel.addActionListener(e -> System.exit(0));
    }

    private void setImage() {
        String deadIcon = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + File.separator + "dead.png";
        ImageIcon icon = new ImageIcon(new ImageIcon(deadIcon).getImage().getScaledInstance(200, 169, Image.SCALE_DEFAULT));
        imageLabel.setIcon(icon);
    }

    static void run() {
        Regame dialog = new Regame();
        dialog.pack();
        dialog.setVisible(true);
    }
}