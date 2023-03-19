package myPiano;

enum Keys { // ใช้ enum จัดเรียงโน้ตใหม่เป็นกลุ่มคีย์ขาวแล้วค่อยคีย์ดำจากเดิมเป็นตามเสียงเพื่อให้ง่ายต่อการ loop
    C(60), D(62), E(64), F(65), G(67), A(69),
    B(71), Db(61), Eb(63), Gb(66), Ab(68), Bb(70);

    private int midi;

    Keys(int midi) {
        this.midi = midi;
    }

    public int getMidi() {
        return (midi);
    }
}
