public class ThreeA {

    public static void main(String[] args) {
        StringAnalyzer stringAnalyzer = new StringAnalyzer("My name is NetworkCrazy");
        stringAnalyzer.printBasicInfo();
        StringBufferExample stringBufferExample = new StringBufferExample("Happy new year!");
        stringBufferExample.performBufferOperations();
    }
}
class StringAnalyzer {
    private String name;

    public StringAnalyzer(String name) {
        this.name = name;
    }

    /** 打印字符串的基本信息 */
    public void printBasicInfo() {
        System.out.println("原字符串: \"" + name + "\"");
        System.out.println("字符串长度: " + name.length());
        System.out.println("第一个字符: " + name.charAt(0));
        System.out.println("最后一个字符: " + name.charAt(name.length() - 1));
        System.out.println("子字符串 (从索引 11 开始): " + name.substring(11));
        int lastIndex = name.lastIndexOf('e');
        System.out.println("字符 'e' 最后出现的位置: " + lastIndex);
        System.out.println("该位置的字符: " + name.charAt(lastIndex));
        System.out.println();
    }
}

class StringBufferExample {
    private StringBuffer buffer;

    public StringBufferExample(String content) {
        this.buffer = new StringBuffer(content);
    }

    /** 执行 StringBuffer 操作 */
    public void performBufferOperations() {
        System.out.println("初始内容: " + buffer.toString());
        buffer.setCharAt(6, 'X');
        System.out.println("替换字符后的内容: " + buffer.toString());
        char charAtPos = buffer.charAt(6);
        System.out.println("第 6 个字符是: " + charAtPos);
        buffer.insert(5, 'Y');
        System.out.println("插入字符后的内容: " + buffer.toString());
        buffer.append("!!!");
        System.out.println("追加后的内容: " + buffer.toString());
    }
}
