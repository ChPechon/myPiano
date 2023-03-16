package myPiano.src;

enum Keys { // ใช้ enum ให้โค้ดอ่านง่ายขึ้น ตรงไหนที่ใส่เลข midi ก็เปลี่ยนเป็นชื่อโน้ตได้เลยไม่งงแล้ว
    C(60), Db(61), D(62), Eb(63), E(64), F(65),
    Gb(66), G(67), Ab(68), A(69), Bb(70), B(71);

    private int midi;

    Keys(int midi) {
        this.midi = midi;
    }

    public int getMidi() {
        return (midi);
    }
}
