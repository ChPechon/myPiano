package myPiano.src;

import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import  javax.sound.midi.*;

public class Piano extends JFrame implements ActionListener {
    private JLayeredPane Keyboard;
    private Synthesizer synthesizer;
    private MidiChannel midiChannel;
    private JPanel panel;
    private JLabel titles;
    private JButton C, D, E, F, G, A, B;
    private JButton Db, Eb, Gb, Ab, Bb;
    private JButton Play; // ปุ่ม Play

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
        titles = new JLabel("Ear Training");
        C = new JButton("C");
        Db = new JButton("Db");
        D = new JButton("D");
        Eb = new JButton("Eb");
        E = new JButton("E");
        F = new JButton("F");
        Gb = new JButton("Gb");
        G = new JButton("G");
        Ab = new JButton("Ab");
        A = new JButton("A");
        Bb = new JButton("Bb");
        B = new JButton("B");
        Play = new JButton("Play");
        Play.setBounds(400, 550, 120, 40);
        add(Play);
        Play.addActionListener(this);

        panel.setBounds(0, 0, 500, 500);
        panel.setVisible(true);
        panel.setLayout(null);
        panel.setBackground(Color.gray);
        titles.setVisible(true);
        titles.setBounds(10, 10, 200, 200);

        //ตรงนี้แก้โค้ดที่เขียนซ้ำ ๆ ลงไปใน methods แก้ง่ายขึ้นเยอะ
        setWhiteNoteDetails(C, 100, 200, 1);
        setWhiteNoteDetails(D, 200, 200, 2);
        setWhiteNoteDetails(E, 300, 200, 3);
        setWhiteNoteDetails(F, 400, 200, 4);
        setWhiteNoteDetails(G, 500, 200, 5);
        setWhiteNoteDetails(A, 600, 200, 6);
        setWhiteNoteDetails(B, 700, 200, 7);

        setBlackNoteDetails(Db, 174, 200, 8);
        setBlackNoteDetails(Eb, 274, 200, 9);
        setBlackNoteDetails(Gb, 474, 200, 10);
        setBlackNoteDetails(Ab, 574, 200, 11);
        setBlackNoteDetails(Bb, 674, 200, 12);
        panel.add(titles);
        add(panel);
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
    

    @Override
    public void actionPerformed(ActionEvent ev) {
        
        JButton source = (JButton)ev.getSource();
        if (source == Play) { // ถ้ากด play
            Gameplay.randomNote(midiChannel); // ก็สุ่มโน้ต
            Gameplay.IsInGame = true; // แล้วก็เปิดเกม
            Play.setVisible(false); // ปิดปุ่มไปซะ
        }

        //โซนนี้คือเล่นโน้ตแล้วส่งโน้ตไปเช็ค
        if (source == C) Gameplay.notePlay(midiChannel, Play, Keys.C.getMidi()); 
        if (source == Db) Gameplay.notePlay(midiChannel, Play, Keys.Db.getMidi());
        if (source == D) Gameplay.notePlay(midiChannel, Play, Keys.D.getMidi());
        if (source == Eb) Gameplay.notePlay(midiChannel, Play, Keys.Eb.getMidi());
        if (source == E) Gameplay.notePlay(midiChannel, Play, Keys.E.getMidi());
        if (source == F) Gameplay.notePlay(midiChannel, Play, Keys.F.getMidi());
        if (source == Gb) Gameplay.notePlay(midiChannel, Play, Keys.Gb.getMidi());
        if (source == G) Gameplay.notePlay(midiChannel, Play, Keys.G.getMidi());
        if (source == Ab) Gameplay.notePlay(midiChannel, Play, Keys.Ab.getMidi());
        if (source == A) Gameplay.notePlay(midiChannel, Play, Keys.A.getMidi());
        if (source == Bb) Gameplay.notePlay(midiChannel, Play, Keys.Bb.getMidi());
        if (source == B) Gameplay.notePlay(midiChannel, Play, Keys.B.getMidi());
    }
}