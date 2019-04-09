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
    private int life;
    private String answer = "";
    private char[] rightLetters;


    MainWindow() {
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
                System.out.println("Pressed " + e.getKeyChar());
                System.out.println("length is " + rightLetters.length);
                checkAnswer(e.getKeyChar());
            }
        });
    }

    void onOK() {
        String[] countryArr = DataFile.getCountry().split(";");
        String country = countryArr[0];
        capital = countryArr[1];
        rightLetters = new char[capital.length()];
        guessedCountryLabel.setText(country);
        hideAnswer();
        life = 5;
        lifeRemainingField.setText(String.valueOf(life));

    }

    private void checkAnswer(char ch) {
        if (life >= 1) {
            if (capital.contains(ch + "")){
                for (int j = 0; j < capital.length() ; j++) {
                    if(capital.charAt(j) == ch) {
                        rightLetters[j] = ch;
                    }
                }
            } else {
                life--;
            }
        } else {
            Regame.run("Проиграл");
        }

        guessedCapitalLabel.setText(String.valueOf(rightLetters));
        lifeRemainingField.setText(String.valueOf(life));
    }

    private void hideAnswer(){
        StringBuilder answer = new StringBuilder();
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
            answer.append(c + "");
        }
        guessedCapitalLabel.setText(answer.toString());
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