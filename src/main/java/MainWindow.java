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
    private JLabel lifeRemainingLabel;
    private JLabel lifeRemainingField;
    private String capital;
    private int life = 5;


    private MainWindow() {

        setTitle("Guess The Capital");
        mainTitleLabel.setText("Угадай столицу!");
        countryTitleLabel.setText("Страна");
        capitalTitleLabel.setText("Столица");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        lifeRemainingLabel.setText("Попыток отсталось");


        buttonOK.setText("Поехали!");
        buttonOK.addActionListener(e -> onOK());
        contentPane.getInputContext().selectInputMethod(new Locale("ru", "RU"));

        buttonOK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                checkAnswer(e.getKeyChar());
            }
        });
    }

    private void onOK() {
        String[] countryArr = DataFile.getCountry().split(";");
        String country = countryArr[0];
        capital = countryArr[1];
        guessedCountryLabel.setText(country);
        hideAnswer();
    }

    private void checkAnswer(char ch) {
        String answer = "";
        char[] rightLetters = new char[capital.length()];
        int i = 0;
            while (i < capital.length()) {
                rightLetters[i] = '_';
                if(capital.charAt(i) == '-'){
                    rightLetters[i] = '-';
                }
                i++;
            }

        for (char c : rightLetters) {
            answer += (c + "");
        }
        guessedCapitalLabel.setText(answer);
        //
    }

    private void hideAnswer(){
        StringBuilder hide = new StringBuilder();
        for (char c : capital.toCharArray()) {
            hide.append("_ ");
        }
        guessedCapitalLabel.setText(String.valueOf(hide));

        lifeRemainingField.setText(String.valueOf(life));
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
