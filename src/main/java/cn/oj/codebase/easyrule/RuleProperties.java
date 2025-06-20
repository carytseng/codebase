package cn.oj.codebase.easyrule;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
/**
* @author rwe
* @version 创建时间：2021年4月30日 上午8:41:23
* 规则属性
*/

public @interface RuleProperties {

	/**
	 * 输入
	 * @return
	 */
	String input() default "";
	
	/**
	 * 输出
	 * @return
	 */
	String output() default "";
	
	/**
	 * 标签
	 * @return
	 */
	String[] tags() default {};
}
