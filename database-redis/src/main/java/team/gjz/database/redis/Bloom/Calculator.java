package team.gjz.database.redis.Bloom;

/**
 * 计算啊布隆过滤器所需空间大小和最佳哈希函数的数目
 */
public class Calculator {
    public static void main(String[] args) {
        double size = 30*10000*365*2.7;
        double[] f = {0.1, 0.01, 0.001};
        for (double tmp : f) {
            System.out.println("概率-->"+tmp+"集合大小-->"+size);
            System.out.println(hashmenmber(tmp,false));
            System.out.println(hashmenmber(tmp,true));
            System.out.println(bitsize(size, tmp,true));
            System.out.println(bitsize(size, tmp,false));
        }
    }
    /**
     * @param value 期望的错误率(假阳性)
     * @param toint 是否取整(般向上取整)
     * @return 最佳哈希函数的数目
     */
    private static double hashmenmber(double value, boolean toint) {
        double result = -Math.log(value) / Math.log(2);
        if (toint)
            return Math.ceil(result);
        else return result;
    }

    /**
     * @param size  估计的集合元素总数
     * @param value 期望的错误率(假阳性)
     * @param toM 是否换算成兆B
     * @return 需要的bit数组的大小
     */
    private static long bitsize(double size, double value,boolean toM) {
        if (toM)
            return (long) Math.ceil(-Math.log(value) * size / Math.pow(Math.log(2), 2)/8/1024/1024);
        return (long) Math.ceil(-Math.log(value) * size / Math.pow(Math.log(2), 2));
    }
}
