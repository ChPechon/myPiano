package myPiano.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Piano extends JFrame implements ActionListener {
    private JLayeredPane Keyboard;
    private Synthesizer synthesizer;
    private MidiChannel midiChannel;
    private JPanel panel, inGamePanel;
    private JLabel titles, Description, lbScore, lbYourScore;
    private JButton[] key;
    private JCheckBox[] box;
    private Keys sound;
    private JButton Play, btnNewGame;

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
        Keyboard    = getLayeredPane();
        panel       = new JPanel();
        inGamePanel = new JPanel();
        Description = new JLabel();
        titles      = new JLabel("Ear Training");
        Play        = new JButton("Play");
        lbYourScore = new JLabel("Your Score");
        lbScore     = new JLabel("0");
        btnNewGame  = new JButton("New Game");

        // Play
        Play.setBounds(460, 650, 120, 40);
        Play.setFocusable(false);
        Play.addActionListener(this);

        // label YourScore
        lbYourScore.setBounds(700, 700, 100, 100);
        add(lbYourScore);

        // label Score
        lbScore.setBounds(800, 700, 100, 100);
        add(lbScore);

        //button NewGame
        btnNewGame.setBounds(300, 650, 120, 40);
        btnNewGame.setFocusable(false);
        btnNewGame.addActionListener(this);
        panel.add(btnNewGame);

        key = new JButton[12];
        box = new JCheckBox[12];
        sound = Keys.C;
        for (int i = 0; i < 12; i++) {
            key[i] = new JButton();
            box[i] = new JCheckBox();
        }

        for (int i = 0; i < 7; i++) { // White note loop
            setWhiteNoteDetails(key[i], 100 * (i + 1), 300, i + 1);
            setCheckBox(box[i], (100 * (i + 1)) + 20, 180, panel);
            sound = Keys.values()[(sound.ordinal() + 1) % 12];
        }

        for (int i = 7; i < 12; i++) { // Black note loop
            if (i < 9)
                setBlackNoteDetails(key[i], (100 * (i - 6)) + 74, 300, i);
           else
                setBlackNoteDetails(key[i], (100 * (i - 5)) + 74, 300, i);
            setCheckBox(box[i], (100 * (i - 6)) + 120, 220, panel);
            sound = Keys.values()[(sound.ordinal() + 1) % 12];
        }
        
        panel.setBounds(10, 10, 800, 700);
        panel.setVisible(true);
        panel.setLayout(null);
        inGamePanel.setBounds(10, 10, 800, 700);
        inGamePanel.setVisible(true);
        inGamePanel.setLayout(null);
        titles.setVisible(true);
        titles.setBounds(320, 5, 400, 200);
        titles.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        Description.setText("Choose the note of what you hear...");
        Description.setBounds(330, 70, 600, 200);
        Description.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        inGamePanel.add(Description);
        panel.add(Play);
        add(titles);
        add(panel);
        add(inGamePanel);
    }

    private void setWhiteNoteDetails(JButton key, int x, int y, int z) {
        key.setText(sound.name());
        key.setName(Integer.toString(sound.getMidi()));
        key.setBounds(x, y, 100, 300);
        key.setBackground(Color.white);
        key.setFocusable(false);
        key.setVerticalAlignment(JButton.BOTTOM);
        Keyboard.add(key, Integer.valueOf(z));
        key.addActionListener(this);
    }

    private void setBlackNoteDetails(JButton key, int x, int y, int z) {
        key.setText(sound.name());
        key.setName(Integer.toString(sound.getMidi()));
        key.setBounds(x, y, 52, 200);
        key.setBackground(Color.black);
        key.setFocusable(false);
        key.setForeground(Color.white);
        key.setVerticalAlignment(JButton.BOTTOM);
        Keyboard.add(key, Integer.valueOf(z));
        key.addActionListener(this);
    }

    private void setCheckBox(JCheckBox box, int x, int y, JPanel panel) {
        box.setText(sound.name());
        box.setName(Integer.toString(sound.getMidi()));
        box.setBounds(x, y, 45, 20);
        box.setFocusable(false);
        panel.add(box);
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
            Gameplay.NewGame(lbScore);
        }

        //โซนนี้คือเล่นโน้ตแล้วส่งโน้ตไปเช็ค
        for (int i = 0; i < 12; i++) {
            if (source == key[i]) {
                Gameplay.notePlay(midiChannel, panel, Integer.valueOf(key[i].getName()));
                Gameplay.CountScore(lbScore);
            }
        }
    }
}