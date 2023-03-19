package myPiano.src;

import java.util.ArrayList;
import java.util.Random;
import javax.sound.midi.MidiChannel;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameplay {
    static int CurNote;
    static boolean IsInGame = false;
    static ArrayList<Integer> noteBox = new ArrayList<>();
    static int score;

    public Gameplay() {
        
    }

    public static void addNoteToBox(int midi) {
        noteBox.add(midi);
    }

    public static void randomNote(MidiChannel midiChannel) {
        CurNote = noteBox.get(new Random().nextInt(noteBox.size()));
        midiChannel.noteOn(CurNote, 100); // เล่นโน้ตที่สุ่มมาให้ฟังรอบนึง
    }

    public static void NewGame(JLabel lbScore){
        score = 0;
        CountScore(lbScore);
    }

    public static void CountScore(JLabel lbScore){
        lbScore.setText("" + Gameplay.score);
    }

    public static void notePlay(MidiChannel midiChannel, JPanel panel, int midi) {
        midiChannel.noteOn(midi, 100);
        if (IsInGame == true && midi == CurNote) { //อยู่ในเกมมั้ย && กดโน้ตตัวเดียวกับที่สุ่มรึเปล่า
            panel.setVisible(true); // ถ้าใช่ก็ปล่อยปุ่ม Play ออกมา
            noteBox.clear();
            score += 1;
            IsInGame = false; // แล้วก็ปิดเกมด้วย
        } else if (IsInGame == true) { // ถ้าผิดแล้วยังอยู่ในเกม

            // โซนนี้เป็นคำสั่ง Delay ใช้เรื่อง Thread
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midiChannel.noteOn(CurNote, 100); // กดผิดก็ให้ฟังใหม่อีกสักรอบ
        }
    }
}