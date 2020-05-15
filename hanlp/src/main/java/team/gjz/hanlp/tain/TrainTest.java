package team.gjz.hanlp.tain;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrainTest {
    private static String fileRoot;

    static {
        try (InputStream resourceAsStream = TrainTest.class.getClassLoader().getResourceAsStream("hanlp.properties");) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            fileRoot = properties.getProperty("root") + "/";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        PerceptronSegmenter perceptronSegmenter = new PerceptronSegmenter(fileRoot + "data/model/perceptron/large/cws.bin");
        boolean flag = true;
        int learncount = 0;
        String string0 = perceptronSegmenter.segment("北京六合永兴商贸中心").toString();
        System.out.println(string0);
        while (flag) {
            learncount++;
            perceptronSegmenter.learn("北京 六合 永兴 商贸 中心");
            String string = perceptronSegmenter.segment("北京六合永兴商贸中心").toString();
            System.out.println(string);
            flag = !string.equals("[北京, 六合, 永兴, 商贸, 中心]");
        }
        System.out.println(learncount);
        System.out.println(perceptronSegmenter.segment("北京六合永兴商贸中心"));
        perceptronSegmenter.getModel().save("C:\\programs\\MyProgrom\\works\\src\\main\\resources\\cws.bin");
        PerceptronLexicalAnalyzer segment = (PerceptronLexicalAnalyzer) new PerceptronLexicalAnalyzer("C:\\programs\\MyProgrom\\works\\src\\main\\resources\\cws.bin",
                HanLP.Config.PerceptronPOSModelPath,
                HanLP.Config.PerceptronNERModelPath).enableCustomDictionary(false);
        System.out.println(segment.analyze("北京六合永兴商贸中心"));
    }
    @Test
    public void test() throws IOException {
        PerceptronLexicalAnalyzer segment = (PerceptronLexicalAnalyzer)new PerceptronLexicalAnalyzer().enableCustomDictionary(false);
        Sentence termlist = segment.analyze("北京沙河鑫万全商贸中心");
        System.out.println(termlist.toString());
    }
}
//北京六合永兴商贸中心