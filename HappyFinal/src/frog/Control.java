
package frog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Control {

    private FrogGame f;
    JButton btnF = new JButton();
    private Timer timer;
    List<JButton> list = new ArrayList<>();
    List<JButton> listSave = new ArrayList<>();
    int yfrog = 40;
    int mark = 0;
    int count = 120;
    Key key = new Key();
    private boolean checkSave = false;

    public Control(FrogGame f) {
        this.f = f;
        f.getjButton2().addKeyListener(key);
        f.getjButton3().addKeyListener(key);
        f.getjPanel1().add(btnF);
        btnF.addKeyListener(key);
    }

    public void save() {
        checkSave = true;
        try {
            FileWriter fw = new FileWriter("data.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (JButton btn : list) {
                int x = btn.getX();
                int y = btn.getY();
                int width = btn.getWidth();
                int height = btn.getHeight();
                bw.write(x + ";" + y + ";" + width + ";" + height);
                bw.newLine();
            }
            bw.write(count + ";" + mark + ";" + yfrog);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }
    }

    public boolean checkTouch() {
        if (btnF.getY() <= 0 || btnF.getY() >= 218) {
            return true;
        }
        Rectangle bf = new Rectangle(btnF.getX(), btnF.getY(), btnF.getWidth(), btnF.getHeight());
        for (JButton btn : list) {
            Rectangle b = new Rectangle(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
            if (bf.intersects(b)) {
                return true;
            }
        }
        return false;
    }

    public void addButton() {
        Random r = new Random();
        JButton btnUp = new JButton();
        int hup = r.nextInt(40) + 80;
        btnUp.setBounds(424, 0, 40, hup);
        JButton btnDown = new JButton();

        int hdown = r.nextInt(20) + 40;
        btnDown.setBounds(424, 258 - hdown, 40, hdown);
        list.add(btnUp);
        list.add(btnDown);
        f.getjPanel1().add(btnUp);
        f.getjPanel1().add(btnDown);
    }

    public void showMes() {
        if (checkSave == false) {
            Object mes[] = {"New Game", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
                f.jPanel1.removeAll();
                f.jPanel1.repaint();
                list.clear();
                mark = 0;
                count = 120;
                f.jLabel1.setText("Point: 0");
                yfrog = 40;
                f.getjPanel1().add(btnF);
                timer.restart();
            }
            if (option == 1) {
                System.exit(0);
            }
        } else {
            Object mes[] = {"New Game", "Continue", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
                f.jPanel1.removeAll();
                f.jPanel1.repaint();
                list.clear();
                mark = 0;
                count = 120;
                f.jLabel1.setText("Point: 0");
                yfrog = 40;
                f.getjPanel1().add(btnF);
                timer.restart();
            }
            if (option == 1) {
                f.jPanel1.removeAll();
                f.jPanel1.repaint();
                list.clear();
                try {
                    FileReader fr = new FileReader("data.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line = "";
                    do {
                        line = br.readLine().trim();
                        if (line == null) {
                            break;
                        }
                        JButton btn = new JButton();
                        String txt[] = line.split(";");
                        int arr[] = new int[txt.length];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = Integer.parseInt(txt[i]);
                        }
                        if (arr.length == 4) {
                            btn.setBounds(arr[0], arr[1], arr[2], arr[3]);
                            list.add(btn);
                            f.jPanel1.add(btn);
                        } else {
                            count = arr[0];
                            mark = arr[1];
                            f.jLabel1.setText("Point: " + mark / 2);
                            yfrog = arr[2];
                        }
                    } while (true);
                    br.close();
                } catch (Exception e) {
                }
                f.getjPanel1().add(btnF);
                timer.restart();
            }
            if (option == 2) {
                System.exit(0);
            }
        }
    }
    boolean checkPause = false;

    public void pause() {
        if (checkPause == false) {
            timer.stop();
            checkPause = true;
        } else {
            timer.restart();
            checkPause = false;
        }
    }

    public void getMark() {
        for (JButton btn : list) {
            if (btnF.getX() == btn.getX()) {
                mark++;
                f.jLabel1.setText("Point: " + mark / 2);
            }
        }
    }

    public void run() {
        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (key.isPress()) {
                    yfrog = yfrog - 20;
                    key.setPress(false);
                }
                yfrog++;
                btnF.setBounds(60, yfrog, 40, 40);
                for (int i = 0; i < list.size(); i++) {
                    int x = list.get(i).getBounds().x - 1;
                    int y = list.get(i).getBounds().y;
                    list.get(i).setLocation(x, y);
                    if (x <= -40) {
                        list.remove(i);
                        i--;
                    }
                }
                getMark();
                count++;
                if (count == 121) {
                    addButton();
                    count = 0;
                }
                if (checkTouch()) {
                    timer.stop();
                    showMes();
                }
            }
        });
        timer.start();
    }

}
