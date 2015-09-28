package fate.webapp.blog.utils;
/**
 * byte������.
 */
public class Bytes {
    /**
     * ����String.subString�Ժ��ִ���������⣨��һ��������Ϊһ���ֽ�)������� ���ֵ��ַ�ʱ�����������ֵ������£�
     * 
     * @param src Ҫ��ȡ���ַ�
     * @param start_idx ��ʼ��꣨���������)
     * @param end_idx ��ֹ��꣨��������꣩
     * @return
     */
    public static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }
}
