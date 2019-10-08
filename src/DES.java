public class DES {
    public static byte[] encryptBytes(byte[] text, long key) {
        long[] message = Helper.byteToLongs(text, true);
        for (int i = 0; i < message.length; i++) {
            message[i] = DESAlgorithm.encrypt(message[i], key);
        }
        return Helper.longToBytes(message, false);
    }

    public static byte[] decryptBytes(byte[] text, long key) {
        long[] message = Helper.byteToLongs(text, false);
        for (int i = 0; i < message.length; i++) {
            message[i] = DESAlgorithm.decrypt(message[i], key);
        }
        return Helper.longToBytes(message, true);
    }
}
