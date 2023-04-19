package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class MyFrame1 extends JFrame {
    Container c;
    JLabel label1;
    JTextField user;
    JButton btn;
    JButton btn1;

    MyFrame1(){
        // For existing users to login
        setTitle("LoginForm");
        setVisible(true);
        setSize(100,300);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        c = getContentPane();
        c.setLayout(null);
        label1 = new JLabel("Username");
        label1.setBounds(10,50,100,20);
        c.add(label1);
        user = new JTextField();
        user.setBounds(100,100,100,20);
        btn = new JButton("Register User");
        btn.setBounds(100,150,70,20);
        c.add(btn);


    }
}
public class Register {

    public static void main(String[] args) {
        MyFrame1 frame1 = new MyFrame1();
    }
}
