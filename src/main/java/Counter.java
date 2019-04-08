//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.text.SimpleDateFormat;
//import javax.swing.Timer;
//
//class Counter extends Thread{
//
//    private int count = 10000;
//    private MainWindow mainWindow = new MainWindow();
//
//    private void counter(){
//        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
//         Timer timer = new Timer(0, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (count > 0) {
//                    count--;
//                    mainWindow.setData(sdf.format(count));
//                    System.out.println(sdf.format(count));
//                }
//            }
//        });
//         timer.start();
//    }
//
//    public void run() {
//        counter();
//    }
//
//}
