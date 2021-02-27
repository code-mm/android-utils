package com.shuangyangad.sdk.mta.utils;


public class EncryptionUtils {

    /**
     * 编码
     *
     * @param src
     * @return
     */
    public static String encode(String src) {
        if (src == null || src.trim().length() == 0) {
            return null;
        }
        byte[] src_byts = src.getBytes();
        //自定义加密
        CustomEncrpyUtil.encodeBytes(src_byts);
        //base64加密

        return Base64Utils.encodeFetchString(src_byts);
    }


    /**
     * 解码
     *
     * @param e64Str
     * @return
     */
    public static String decode(String e64Str) {
        if (e64Str == null || e64Str.trim().length() == 0) {
            return null;
        }
        byte[] e64Bytes = Base64Utils.decode(e64Str);
        //自定义解密
        CustomEncrpyUtil.decodeBytes(e64Bytes);
        //转为字符串
        String desrc = new String(e64Bytes);
        return desrc;
    }


    private static class CustomEncrpyUtil {
        private static final int KEY = 119;
        private static int PIECE_LENGTH = 20;
        private static int SKIP = 4;


        public static void encodeBytes(byte bytes[]) {
            int key = KEY;
            int pieceLen = PIECE_LENGTH;
            int skip = SKIP;
            int bytesLen = bytes.length;
            int i = 0;
            int offset = 1;
            do {
                for (int j = 0; j < pieceLen; ++j) {
                    int tmp29_27 = i;
                    byte[] tmp29_26 = bytes;
                    if (offset > 1) {
                        tmp29_26[tmp29_27] = (byte) (tmp29_26[tmp29_27] ^ key + offset);
                    } else {
                        tmp29_26[tmp29_27] = (byte) (tmp29_26[tmp29_27] ^ key);
                    }

                    ++i;
                    if (i >= bytesLen) {
                        break;
                    }
                }
                i += skip;
            }
            while (i < bytesLen);
        }

        public static byte[] decodeBytes(byte bytes[]) {
            int key = KEY;
            int pieceLen = PIECE_LENGTH;
            int skip = SKIP;
            int bytesLen = bytes.length;
            int i = 0;
            do {
                for (int j = 0; j < pieceLen; ++j) {
                    int tmp29_27 = i;
                    bytes[tmp29_27] = (byte) ((bytes[tmp29_27] ^ key));
                    ++i;
                    if (i >= bytesLen) {
                        break;
                    }
                }
                i += skip;
            }
            while (i < bytesLen);
            return bytes;
        }
    }
}
