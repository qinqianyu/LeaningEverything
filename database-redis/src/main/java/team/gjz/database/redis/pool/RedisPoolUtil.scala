package team.gjz.database.redis.pool

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool

object RedisPoolUtil extends Serializable {
  @transient private var pool: JedisPool = _

  /**
    * 读取jedis配置信息, 触发jedis初始化
    * 可以写配置类
    */
  def initJedi(): Unit = {

    val maxTotal = 50
    val maxIdle = 30
    val minIdle = 10
    val redisHost = "192.168.20.138"
    val redisPort = 6379
    val redisTimeout = 100
    //     val redisPassword = "root123456"
    val redisPassword = null
    makePool(redisHost, redisPort, redisTimeout, redisPassword, maxTotal, maxIdle, minIdle)
  }

  def makePool(redisHost: String, redisPort: Int, redisTimeout: Int, redisPassword: String, maxTotal: Int, maxIdle: Int, minIdle: Int): Unit
  = {
    init(redisHost, redisPort, redisTimeout, redisPassword, maxTotal, maxIdle, minIdle, testOnBorrow = true, testOnReturn = false, 10000)
  }

  /**
    * 初始化jedis连接池
    *
    * @param redisHost     host
    * @param redisPort     端口
    * @param redisTimeout  连接redis超时时间
    * @param redisPassword redis密码
    * @param maxTotal      总的连接数
    * @param maxIdle       最大空闲连接数
    * @param minIdle       最小空闲连接数
    * @param testOnBorrow  在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    * @param testOnReturn   测试连接是否可用
    * @param maxWaitMillis  最大等待时间
    */
  def init(redisHost: String, redisPort: Int, redisTimeout: Int, redisPassword: String, maxTotal: Int, maxIdle: Int, minIdle: Int, testOnBorrow: Boolean, testOnReturn: Boolean, maxWaitMillis: Long): Unit = {
    if (pool == null) {
      val poolConfig = new GenericObjectPoolConfig()
      poolConfig
        .setMaxTotal(maxTotal)
      poolConfig
        .setMaxIdle(maxIdle)
      poolConfig
        .setMinIdle(minIdle)
      poolConfig
        .setTestOnBorrow(testOnBorrow)
      poolConfig
        .setTestOnReturn(testOnReturn)
      poolConfig
        .setMaxWaitMillis(maxWaitMillis)
      pool = new JedisPool(poolConfig, redisHost, redisPort, redisTimeout, redisPassword)
      val hook = new Thread {
        override def run(): Unit = pool.destroy()
      }
      sys.addShutdownHook(hook.run())
    }
  }

  def getPool: JedisPool
  = {
    if (pool == null) {
      initJedi()
    }
    pool
  }
}
