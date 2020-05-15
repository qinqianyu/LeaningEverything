package team.gjz.hanlp.tain;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description:
 * @author: jxk
 * @create: 2020-04-10 15:38
 **/
public class TrainEnterprise {
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

    @Test
    public void test() throws IOException {
        PerceptronLexicalAnalyzer analyzer = new PerceptronLexicalAnalyzer(fileRoot + "data/model/perceptron/large/cws.bin",
                HanLP.Config.PerceptronPOSModelPath,
                HanLP.Config.PerceptronNERModelPath);

        System.out.println(analyzer.analyze("海淀奔驰女告民警"));

        // 支持在线学习
        analyzer.learn("海淀/ns 奔驰/nz 女/b 告/v 民警/n");
        // 学习到新知识
        System.out.println(analyzer.analyze("海淀奔驰女告民警"));
        // 还可以举一反三
      //  System.out.println(analyzer.analyze("主席和特朗普通电话"));

        // 知识的泛化不是死板的规则，而是比较灵活的统计信息
      //  System.out.println(analyzer.analyze("我在浙江金华出生"));
      //  analyzer.learn("在/p 浙江/ns 金华/ns 出生/v");
       // System.out.println(analyzer.analyze("我在四川金华出生，我的名字叫金华"));

    }
}
