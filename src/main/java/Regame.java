import javax.swing.*;

public class Regame extends JDialog{
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel imageLabel;
    private JLabel textLabel;

    private Regame() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        textLabel.setText("Проиграл...");
        buttonCancel.setText("Выход");

        buttonCancel.addActionListener(e -> System.exit(0));
    }

    static void run() {
        Regame dialog = new Regame();
        dialog.pack();
        dialog.setVisible(true);
    }
}