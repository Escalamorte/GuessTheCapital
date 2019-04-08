import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel mainTitleLabel;
    private JLabel countryTitleLabel;
    private JLabel capitalTitleLabel;
    private JLabel globeTitleLabel;
    private JLabel guessedCountryLabel;
    private JLabel guessedCapitalLabel;
    private char[] capital;


    private MainWindow() {

        setTitle("Guess The Capital");
        mainTitleLabel.setText("Угадай столицу!");
        countryTitleLabel.setText("Страна");
        capitalTitleLabel.setText("Столица");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.setText("Поехали!");
        buttonOK.addActionListener(e -> onOK());
        contentPane.getInputContext().selectInputMethod(new Locale("ru", "RU"));

        buttonOK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });
    }

    private void onOK() {
        String[] countryArr = DataFile.getCountry().split(";");
        String country = countryArr[0];
        capital = countryArr[1].toCharArray();
        guessedCountryLabel.setText(country);
        hideAnswer();
    }

    private void hideAnswer(){
        StringBuilder hide = new StringBuilder();
        for (char c : capital) {
            hide.append("_ ");
        }
        guessedCapitalLabel.setText(String.valueOf(hide));
    }

    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        new DataFile().run();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }

}
