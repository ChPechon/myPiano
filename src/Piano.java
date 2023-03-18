package myPiano.src;

import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import  javax.sound.midi.*;

public class Piano extends JFrame implements ActionListener {
    private JLayeredPane Keyboard;
    private Synthesizer synthesizer;
    private MidiChannel midiChannel;
    private JPanel panel, inGamePanel;
    private JLabel titles, Description;
    private JButton[] key;
    private JCheckBox[] box;
    private Keys    sound;
    private JButton Play;// ปุ่ม Play
    private JLabel lbYourScore;
    private JLabel lbScore;
    private JButton btnNewGame;

    public Piano() {
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // โซนนี้เป็นการเซ็ตระบบเสียง ไม่แน่ใจเหมือนกัน ลองหาเพิ่มดู
        try {
            synthesizer = MidiSystem.getSynthesizer(); 
            synthesizer.open();
            midiChannel = synthesizer.getChannels()[0];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        ComponentsDetail();
        setVisible(true);
    }
    
    private void ComponentsDetail() {
        Keyboard = getLayeredPane();
        panel = new JPanel();
        inGamePanel = new JPanel();
        Description = new JLabel();
        titles = new JLabel("Ear Training");
        Play = new JButton("Play");
        Play.setBounds(460, 650, 120, 40);
        Play.setFocusable(false);
        Play.addActionListener(this);
        // label YourScore
        lbYourScore = new JLabel("Your Score");
        lbYourScore.setBounds(700, 700, 100, 100);
        add(lbYourScore);

        //label Score
        lbScore = new JLabel("0");
        lbScore.setBounds(800, 700, 100, 100);
        add(lbScore);

        //button NewGame
        btnNewGame = new JButton("New Game");
        btnNewGame.setBounds(300, 650, 120, 40);
        btnNewGame.setFocusable(false);
        panel.add(btnNewGame);
        buttonNewGame();
        key = new JButton[12];
        box = new JCheckBox[12];
        sound = Keys.C;
        for (int i = 0; i < 12; i++) {
            key[i] = new JButton();
            box[i] = new JCheckBox();
        }

        for (int i = 0; i < 7; i++) { // ลูปโน้ตตัวชาว
            key[i].setText(sound.name());
            key[i].setName(Integer.toString(sound.getMidi()));
            setWhiteNoteDetails(key[i], 100 * (i + 1), 300, i + 1);
            box[i].setText(sound.name());
            box[i].setName(Integer.toString(sound.getMidi()));
            setCheckBox(box[i], (100 * (i + 1)) + 20, 180, panel);
            sound = Keys.values()[(sound.ordinal() + 1) % 12];
        }

        for (int i = 7; i < 12; i++) { // ลูปโน้ตตัวดำ
            key[i].setText(sound.name());
            key[i].setName(Integer.toString(sound.getMidi()));
            if (i < 9)
                setBlackNoteDetails(key[i], (100 * (i - 6)) + 74, 300, i);
           else
                setBlackNoteDetails(key[i], (100 * (i - 5)) + 74, 300, i);
            box[i].setText(sound.name());
            box[i].setName(Integer.toString(sound.getMidi()));
            setCheckBox(box[i], (100 * (i - 6)) + 120, 220, panel);
            sound = Keys.values()[(sound.ordinal() + 1) % 12];
        }
        
        panel.setBounds(10, 10, 800, 700);
        inGamePanel.setBounds(10, 10, 800, 700);
        panel.setVisible(true);
        inGamePanel.setVisible(true);
        panel.setLayout(null);
        inGamePanel.setLayout(null);
        titles.setVisible(true);
        titles.setBounds(320, 5, 400, 200);
        titles.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        Description.setText("Choose the note of what you hear...");
        Description.setBounds(320, 120, 600, 200);
        inGamePanel.add(Description);
        panel.add(Play);
        add(titles);
        add(panel);
        add(inGamePanel);
    }

    private void setWhiteNoteDetails(JButton key, int x, int y, int z) {
        key.setBounds(x, y, 100, 300);
        key.setBackground(Color.white);
        key.setFocusable(false);
        key.setVerticalAlignment(JButton.BOTTOM);
        Keyboard.add(key, Integer.valueOf(z));
        key.addActionListener(this);
    }

    private void setBlackNoteDetails(JButton key, int x, int y, int z) {
        key.setBounds(x, y, 52, 200);
        key.setBackground(Color.black);
        key.setFocusable(false);
        key.setForeground(Color.white);
        key.setVerticalAlignment(JButton.BOTTOM);
        Keyboard.add(key, Integer.valueOf(z));
        key.addActionListener(this);
    }

    private void setCheckBox(JCheckBox box, int x, int y, JPanel panel) {
        box.setBounds(x, y, 45, 20);
        box.setFocusable(false);
        panel.add(box);
    }

    private void buttonNewGame(){
        btnNewGame.addActionListener(this);
    }

    //void NewGame
    public void NewGame(){
        Gameplay.score = 0;
        lbScore.setText("" + Gameplay.score);
    }
    // count score
    public void CountScore(){
        lbScore.setText("" + Gameplay.score);
    }
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        JButton source = (JButton)ev.getSource();
        if (source == Play) { // ถ้ากด play
            for (int i = 0; i < 12; i++)
                if (box[i].isSelected() == true)
                    Gameplay.addNoteToBox(Integer.valueOf(box[i].getName()));
            Gameplay.randomNote(midiChannel); // ก็สุ่มโน้ต
            Gameplay.IsInGame = true; // แล้วก็เปิดเกม
            panel.setVisible(false); // ปิดปุ่มไปซะ
            inGamePanel.setVisible(true);
        }
        else if (source == btnNewGame) {
            NewGame();
        }

        //โซนนี้คือเล่นโน้ตแล้วส่งโน้ตไปเช็ค
        for (int i = 0; i < 12; i++) {
            if (source == key[i]) {
                Gameplay.notePlay(midiChannel, panel, Integer.valueOf(key[i].getName()));
                CountScore();
            }
        }
    }

    public static void main(String[] args) {
        new Piano();
    }
}