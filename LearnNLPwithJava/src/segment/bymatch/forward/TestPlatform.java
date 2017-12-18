package segment.bymatch.forward;

public class TestPlatform {
    public static void main(String[] args) {
        System.out.println(findChineseStrNumber("啊"));
        System.out.println(findChineseStrNumber("万"));
        System.out.println(compareChineseStr("啊","万"));
        System.out.println('啊'-'万');

    }

    public static int compareChineseStr(String firstStr, String secondStr) {
        return firstStr.compareTo(secondStr);
    }



    public static int findChineseStrNumber(String checkString){
        return checkString.codePointAt(0);
    }

}
