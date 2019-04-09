import javax.swing.*;
import java.awt.event.*;

public class Regame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel imageLabel;
    private JLabel textLabel;

    private Regame(String s) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        textLabel.setText(s);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
    }

    private void onOK() {
        new MainWindow().onOK();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    static void run(String s) {
        Regame dialog = new Regame(s);
        dialog.pack();
        dialog.setVisible(true);
    }
}
