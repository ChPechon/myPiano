package myPiano.src;

enum Keys { // ใช้ enum ให้โค้ดอ่านง่ายขึ้น ตรงไหนที่ใส่เลข midi ก็เปลี่ยนเป็นชื่อโน้ตได้เลยไม่งงแล้ว
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
