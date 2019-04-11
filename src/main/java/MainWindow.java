import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

public class MainWindow extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel countryTitleLabel, capitalTitleLabel, globeTitleLabel;
    private JLabel guessedCountryLabel, guessedCapitalLabel, lifeRemainingLabel, lifeRemainingField;
    private JLabel mainLabel;
    private JPanel guessPanel;
    private JLabel guessedCountTitle;
    private JLabel guessedCountLabel;
    private String capital;
    private int life = 5;
    private char[] rightLetters;
    private Timer timer;
    private int guessedCount;

    private MainWindow() {
        guessPanel.setVisible(false);
        setTitle("Guess The Capital");
        mainLabel.setText("Угадай столицу!");
        countryTitleLabel.setText("Страна");
        capitalTitleLabel.setText("Столица");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        lifeRemainingLabel.setText("Попыток отсталось");
        guessedCountTitle.setText("Количество отгаданных");



        buttonOK.setText("Поехали!");
        buttonOK.addActionListener(e -> onOK());
        contentPane.getInputContext().selectInputMethod(new Locale("ru", "RU"));

        buttonOK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                checkAnswer(e.getKeyChar());
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    onOK();
                }
            }
        });

    }

    private void onOK() {
        guessPanel.setVisible(true);

        String[] countryArr = DataFile.getCountry().split(";");
        String country = countryArr[0];
        capital = countryArr[1];

        guessedCountLabel.setText(String.valueOf(guessedCount));
        rightLetters = new char[capital.length()];
        guessedCountryLabel.setText(country);

        lifeRemainingField.setText(String.valueOf(life));

        buttonOK.setText("Пропустить");

        guessedCapitalLabel.setText(String.copyValueOf(hideAnswer()));

        setGlobalImage(country);
    }

    private void checkAnswer(char ch) {
        char[] alphabet = new char[]{'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
        if (life > 0) {
            for (char a : alphabet) {
                if (String.valueOf(a).equalsIgnoreCase(String.valueOf(ch))) {
                    if (capital.contains(ch + "") || capital.contains(Character.toUpperCase(ch) + "")) {
                        for (int j = 0; j < capital.length(); j++) {
                            if (capital.charAt(j) == ch || Character.toLowerCase(capital.charAt(j)) == ch) {
                                rightLetters[j] = ch;
                            }
                        }
                    } else {
                        lifeRemainingField.setText(String.valueOf(--life));
                        if (life == 0) {
                            Regame.run();
                        }
                    }
                }
            }
        } else {
            Regame.run();
        }

        guessedCapitalLabel.setText(String.valueOf(rightLetters).toUpperCase());

        boolean regame = false;
        for (char c : rightLetters) {
            if (c == '*') {
                regame = true;
            }
        }

        if(!regame){
            ++life;
            guessedCount++;
            rightAnswer();
        }
    }

    private void setGlobalImage(String coun){
        String flagDir = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + File.separator + coun + ".png";
        String globeImage = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + File.separator + "globe.gif";
        File file = new File(flagDir);

        if (file.exists()) {
            ImageIcon flag = new ImageIcon(new ImageIcon(flagDir).getImage());
            globeTitleLabel.setIcon(flag);
            System.out.println(file.exists());
        } else {
            ImageIcon globe = new ImageIcon(new ImageIcon(globeImage).getImage());
            globeTitleLabel.setIcon(globe);
        }
    }

    private char[] hideAnswer(){
        rightLetters = new char[capital.length()];
            for (int i = 0; i < capital.length() ; i++) {
                rightLetters[i] = '*';
                if(capital.charAt(i) == '-'){
                    rightLetters[i] = '-';
                } else if (capital.charAt(i) == ' '){
                    rightLetters[i] = ' ';
                }
            }
        return rightLetters;
    }

    private void rightAnswer(){
        String correctImage = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + File.separator + "correct.png";
        ImageIcon ic = new ImageIcon(new ImageIcon(correctImage).getImage().getScaledInstance(22,27, Image.SCALE_DEFAULT));
        buttonOK.setIcon(ic);

        //buttonOK.setText("Правильно!");
        buttonOK.setText("");

        timer = new Timer(0, e -> {
            int sec = 50000;
            while (sec > 0){
                sec--;
                System.out.println(sec);
            }
            timer.stop();
            buttonOK.setText("Пропустить");
            buttonOK.setIcon(null);
            buttonOK.setBackground(null);
            onOK();
        });
        timer.start();
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