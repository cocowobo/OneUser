package com.peipao8.vehiclelock.LYangCode.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;

import java.util.List;
import java.util.Map;

/**
 * 注意事项，fastjson解析的类对象的内部成员变量必须是public 修饰的。
 * JSON中的键值对就是Java中的Map，JSON中的数组就是Java中的List
 */
public class FastJsonUtils {

	/**
	 * 功能描述：把java对象（可以是集合对象）转换成JSON数据转换成普通字符串，
	 *
	 * @param jsonObj JSON数据
	 * @throws Exception
	 * @author myclover
	 */
	public static String getObgString(Object jsonObj) {
		return JSON.toJSONString (jsonObj);
	}

	/**
	 * 功能描述：把JSON数据转换成普通字符串列表
	 *
	 * @param jsonData JSON数据
	 * @throws Exception
	 * @author myclover
	 */
	public static List<String> getStringList(String jsonData) {
		List<String> stringsList = null;
		try {
			stringsList = JSON.parseArray (jsonData, String.class);
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getStringList()---","错误如下："+e);
			e.printStackTrace ();
		} return stringsList;
	}

	/**
	 * 功能描述：把JSON数据转换成指定的java对象
	 *
	 * @param jsonData JSON数据
	 * @param clazz 指定的java对象
	 * @throws Exception
	 * @author myclover
	 */
	public static <T> T getSingleBean(String jsonData, Class<T> clazz) {
		T t  = null;
		try {
			t =   JSON.parseObject (jsonData, clazz); return t;
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getSingleBean()---","错误如下："+e);
			e.printStackTrace ();
		} return t;
	}

	/**
	 * 功能描述：把JSON数据转换成指定的java对象列表
	 *
	 * @param jsonData JSON数据
	 * @param clazz 指定的java对象
	 * @throws Exception
	 * @author myclover
	 */
	public static <T> List<T> getBeanList(String jsonData, Class<T> clazz) {
		List<T> ts = null;
		try {
			ts = JSON.parseArray (jsonData, clazz);
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getBeanList()---","错误如下："+e);
			e.printStackTrace ();
		}
		return ts;
	}

	/**
	 * 功能描述：把JSON字符串数据转换成制定的java对象Map集合
	 * 方法封装思想没问题奇怪的是为什么不行，奇怪的class 类名变化
	 *  @param jsonData JSON数据
	 * @throws Exception
	 * @author myclover
	 */
	public static  <T> Map<String, T> getBeanMapOld(String jsonData, final Class<T> clazz) {
		Map<String, T> maps = null ;
		try {
			maps = JSON.parseObject (jsonData, new TypeReference<Map<String,T>> () {});
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getBeanMap()---","错误如下："+e);
			e.printStackTrace ();
		}
		return maps ;
	}

	/**
	 * 功能描述：把JSON字符串数据转换成制定的java对象Map集合
	 * @param jsonData JSON数据
	 * @throws Exception
	 * @author myclover
	 */
	public static  <T> Map<String, T> getBeanMap(String jsonData,TypeReference<Map<String, T>> typeReference) {
		Map<String, T> maps = null ;
		try {
			maps = JSON.parseObject (jsonData, typeReference);
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getBeanMap()---","错误如下："+e);
			e.printStackTrace ();
		}
		return maps ;
	}

	/**
	 * 功能描述：把JSON数据转换成较为复杂的java对象列表
	 *
	 * @param jsonData JSON数据
	 * @throws Exception
	 * @author myclover
	 */
	public static List<Map<String, Object>> getBeanMapList(String jsonData) {
		List<Map<String, Object>> maps = null ;
		try {
			maps = JSON.parseObject (jsonData, new TypeReference<List<Map<String, Object>>> () {
			});
		} catch (Exception e) {
			LYangLogUtil.e ("FastJsonUtils.getBeanMapList()---","错误如下："+e);
			e.printStackTrace ();
		}
		return maps ;
	}

	/**
	 * 将网络请求下来的数据用fastjson处理空的情况，并将时间戳转化为标准时间格式
	 *
	 * @param result
	 */
	public static String dealResponseResult(String result) {
		result = JSONObject.toJSONString (result, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteSlashAsSpecial, SerializerFeature.WriteTabAsSpecial);
		return result;
	}


	/**扩充方法*/
	public void test3(String jsonStr) {

		Map<String, Object> map1 = JSON.parseObject(jsonStr);//返回JSONObject，JSONObject实现Map<String, Object>接口

		//法二
		Map<String, Object> map2 = (Map<String, Object>)JSON.parse(jsonStr);

		for (String key : map1.keySet()) {
			System.out.println(key + ":" + map1.get(key));
		}

	}


	/**测试一，具体对象转字符串，在转具体对象*/
	public void test1() {
		//		Employee e = new Employee("001", "张三", 23, new Date());

		//序列化
		//		String jsonStr = JSON.toJSONString(e);
		//		System.out.println(jsonStr);

		//反序列化
		//		Employee emp = JSON.parseObject(jsonStr, Employee.class);
		//		System.out.println(emp.getName());

	}

	/**测试二，序列化具体list集合对象为字符串，再转具体list集合对象两种方法转回来*/
	public void test2() {
		//生成集合对象
		//		Employee e = new Employee("001", "张三", 23, new Date());
		//		Employee e2 = new Employee("002", "李四", 29, new Date());
		//		List<Employee> emps = new ArrayList<Employee>();
		//		emps.add(e);
		//		emps.add(e2);

		//fastjson序列化list， 返回来的是一个json数组，由[]包含两个json
		//		String jsonArryStr = JSON.toJSONString(emps);
		//		System.out.println(jsonArryStr);

		// //反序列化

		//法一
		// List<Employee> empList = JSON.parseObject(jsonArryStr, new TypeReference<List<Employee>>(){} );

		//法二
		//		List<Employee> empList = JSON.parseArray(jsonArryStr,Employee.class);

	}

	/**测试三，序列化具体的包含list集合的复杂对象为字符串，再转具体的包含list集合的复杂对象两种方法转回来
	 *  还有json字符串转map两种方法。
	 * */
	public void test3() {
		//生成集合对象
		//		Employee e = new Employee("001", "张三", 23, new Date());
		//		Employee e2 = new Employee("002", "李四", 29, new Date());
		//		List<Employee> emps = new ArrayList<Employee>();
		//		emps.add(e);
		//		emps.add(e2);
		//		Dept dept = new Dept("d001", "研发部", emps);

		//序列化
		//		String jsonStr = JSON.toJSONString(dept);
		//		System.out.println(jsonStr);

		//反序列化
		//		Dept d = JSON.parseObject(jsonStr, Dept.class);
		//		System.out.println(d.getName());

		//json转map
		//法一
		//		Map<String, Object> map1 = JSON.parseObject(jsonStr);//返回JSONObject，JSONObject实现Map<String, Object>接口

		//法二
		// Map<String, Object> map1 = (Map<String, Object>)JSON.parse(jsonStr);
	}

	/**测试四，序列化中的 parseObject方法体验，
	 * 和test1，比较，打印结果没有类名打头。
	 * */
	public void test4() {

		//		Employee e = new Employee("001", "张三", 23, new Date());

		//序列化
		//		String jsonStr = JSON.toJSONString(e);
		//		System.out.println(jsonStr);

		//反序列化 (可以和test1比较)
		//		JSONObject emp = JSON.parseObject(jsonStr, JSONObject.class);
		//		System.out.println(emp);

		//再放一个Employee不存在的字段
		//		emp.put("salary", "8000");
		//
		//		System.out.println(emp.toJSONString());
		//		System.out.println(emp.get("salary"));

	}

	//	//fastjson序列化字符串
	//
	//	@Test
	//
	//	public void test5(){
	//
	//		List<String> strs = new ArrayList<String>();
	//
	//		strs.add("hello");
	//
	//		strs.add("world");
	//
	//		strs.add("banana");
	//
	//		//序列化
	//
	//		String jsonStr = JSON.toJSONString(strs);
	//
	//		System.out.println(jsonStr);
	//
	//		//反序列化
	//
	//		List<String> strList = JSON.parseObject(jsonStr, new TypeReference<List<String>>(){} );
	//
	//		// List<String> strList = JSON.parseArray(jsonStr, String.class);//等同于上一句
	//
	//		for (String str : strList) {
	//
	//			System.out.println(str);
	//
	//		}
	//
	//	}
	//
	//	//fastjson过滤字段
	//
	//	@Test
	//
	//	public void test6() {
	//
	//		Employee e = new Employee("001", "张三", 23, new Date());
	//
	//		Employee e2 = new Employee("002", "李四", 29, new Date());
	//
	//		List<Employee> emps = new ArrayList<Employee>();
	//
	//		emps.add(e);
	//
	//		emps.add(e2);
	//
	//		//构造过滤器
	//
	//		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Employee.class, "id", "age");
	//
	//		String jsonStr =JSON.toJSONString(emps, filter);
	//
	//		System.out.println(jsonStr);
	//
	//	}
	//
	//	//fastjson 日期处理
	//
	//	@Test
	//
	//	public void test7(){
	//
	//		Date date = new Date();
	//
	//		String dateStr = JSON.toJSONString(date);
	//
	//		System.out.println(dateStr);
	//
	//		String dateStr2 = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss");
	//
	//		System.out.println(dateStr2);
	//
	//		//序列化实体
	//
	//		Employee emp = new Employee("001", "张三", 23, new Date());
	//
	//		//法一
	//
	//		String empStr = JSON.toJSONStringWithDateFormat(emp, "yyyy-MM-dd HH:mm:ss");
	//
	//		System.out.println(empStr);
	//
	//		//法二
	//
	//		String empStr2 = JSON.toJSONString(emp, SerializerFeature.WriteDateUseDateFormat);
	//
	//		System.out.println(empStr2);
	//
	//		//法三
	//
	//		SerializeConfig config = new SerializeConfig();
	//
	//		config.put(Date.class, new SimpleDateFormatSerializer("yyyy年MM月dd日 HH时mm分ss秒"));
	//
	//		String empStr3 = JSON.toJSONString(emp, config);
	//
	//		System.out.println(empStr3);
	//
	//	}
	//
	//	//fastjson 去掉值的双引号 实现JSONAware接口
	//
	//
	//	public void test8(){
	//
	//		//见同级目录的Function.java
	//
	//	}
	//
	//	//fastjson 注解形式  (别名命名, 过滤字段, 日期格式)
	//
	//
	//	public void test9(){
	//
	//		Student stu = new Student("001", "张三", 23, new Date());
	//
	//		String jsonStr = JSON.toJSONString(stu);
	//
	//		System.out.println(jsonStr);
	//
	//	}
}
