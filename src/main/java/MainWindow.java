import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainWindow extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel countryTitleLabel, capitalTitleLabel, globeTitleLabel;
    private JLabel guessedCountryLabel, guessedCapitalLabel, lifeRemainingLabel, lifeRemainingField;
    private JLabel mainLabel, guessedCountTitle, guessedCountLabel;
    private JPanel guessPanel;
    private String country;
    private String capital;
    private int countryNumber;
    private ArrayList<Integer> rightAnswersArr = new ArrayList<>();
    private int life;
    private char[] rightLetters;
    private int guessedCount;
    private AudioPlay audioPlay = new AudioPlay();

    MainWindow() {

        guessPanel.setVisible(false);
        setContentPane(contentPane);
        setModal(true);

        setTitle("Guess The Capital");
        mainLabel.setText("Угадай столицу!");
        countryTitleLabel.setText("Страна");
        capitalTitleLabel.setText("Столица");

        lifeRemainingLabel.setText("Попыток отсталось");
        guessedCountTitle.setText("Количество отгаданных");

        buttonOK.setText("Поехали!");
        buttonOK.addActionListener(e -> onOK());
        buttonOK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                checkAnswer(e.getKeyChar());
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    onOK();
                }
            }
        });

        contentPane.getInputContext().selectInputMethod(new Locale("ru", "RU"));

    }

    void startNewGame() {
        guessPanel.setVisible(false);
        setContentPane(contentPane);
        setModal(true);
        buttonOK.setText("Поехали!");
    }

    private void onOK() {
        if(buttonOK.getText().equals("Пропустить") && life > 0) {
            lifeRemainingField.setText(String.valueOf(--life));
            System.out.println("life is " + life);
                if (life != 0) {
                    setCountry();
                } else {
                    Regame.run();
                }
        } else {
            life = 5;
            guessedCount = 0;
            setCountry();
            lifeRemainingField.setText(String.valueOf(life)); // выводит жизни на экран
        }
        buttonOK.setText("Пропустить");
    }

    private void setCountry() { //выбирает страну

        guessPanel.setVisible(true);
        try {
            String[] countryArr = DataFile.getCountry().split(";");
            countryNumber = Integer.parseInt(countryArr[0]);
            country = countryArr[1];
            capital = countryArr[2];
        } catch (ArrayIndexOutOfBoundsException exp) {
            System.out.println(exp.getMessage());
        }

        guessedCountLabel.setText(guessedCount + "/" + DataFile.LineCount()); // кол-во угаданных / всего стран

        rightLetters = new char[capital.length()];
        guessedCapitalLabel.setText(String.copyValueOf(hideAnswer()));

        guessedCountryLabel.setText(country); //выводит страну на экран


        setGlobalImage(country);
    }

    private void checkAnswer(char ch) { // проверяет совпадение букв
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
                        audioPlay.wrongAnswer();
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

        guessedCapitalLabel.setText(String.valueOf(rightLetters).toUpperCase()); // выводит угаданные буквы на экран

        boolean regame = false;
        for (char c : rightLetters) {
            if (c == '*') {
                regame = true;
            }
        }

        if(!regame){ // если слово угадано
            rightAnswersArr.add(countryNumber); // добавляет в массив номер угаданной страны
            rightAnswersArr.forEach((i)-> System.out.println("Right answers is " + i));
            audioPlay.correctAnswer();
            ++life;
            guessedCount++;
            rightAnswer();
        }
    }

    private void setGlobalImage(String coun){ // выводит флаг страны
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

    private Timer timer;
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

    ArrayList getRigthAnswerNum() {
        return rightAnswersArr;
    }

    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();

        new  DataFile().start();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }
}