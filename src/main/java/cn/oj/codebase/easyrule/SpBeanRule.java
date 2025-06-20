package cn.oj.codebase.easyrule;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author rwe
 * @version 创建时间：2021年5月17日 上午10:12:40 Spring Bean Rule
 */

public class SpBeanRule extends BasicRule {
	
	private static final Logger logger = LoggerFactory.getLogger(SpBeanRule.class);

	private Condition condition = Condition.FALSE;
	private final List<Action> actions = new ArrayList<>();

	public SpBeanRule(final Object rule) {
//		RuleDefinitionValidator ruleDefinitionValidator = new RuleDefinitionValidator();
//		ruleDefinitionValidator.validateRuleDefinition(rule);
//		Method[] methods = rule.getClass().getMethods();
//		// 寻找 condition 方法
//		for (Method method : methods) {
//			if (method.isAnnotationPresent(org.jeasy.rules.annotation.Condition.class)
//					&& method.getReturnType().equals(Boolean.TYPE)) {
//				condition = facts -> {
//					try {
//						List<Object> actualParameters = getActualParameters(method, facts);
//						Object result = method.invoke(rule, actualParameters.toArray());
//						return (boolean) result;
//					} catch (NoSuchFactException | IllegalAccessException | InvocationTargetException
//							| IllegalArgumentException e) {
//						logger.error(e.getMessage(), e);
//						return false;
//					}
//				};
//				break;
//			}
//		}
//		// 寻找 action 方法
//		List<ActionMethodOrderBean> methodBeans = new ArrayList<>();
//		for (Method method : methods) {
//			if (method.isAnnotationPresent(org.jeasy.rules.annotation.Action.class)) {
//				org.jeasy.rules.annotation.Action actionAnnotation = method
//						.getAnnotation(org.jeasy.rules.annotation.Action.class);
//				int order = actionAnnotation.order();
//				methodBeans.add(new ActionMethodOrderBean(method, order));
//			}
//		}
//		methodBeans.sort((b1, b2) -> {
//			return b1.compareTo(b2);
//		});
//		for (ActionMethodOrderBean actionMethodBean : methodBeans) {
//			Method actionMethod = actionMethodBean.getMethod();
//			Action action = facts -> {
//				List<Object> actualParameters = getActualParameters(actionMethod, facts);
//				actionMethod.invoke(rule, actualParameters.toArray());
//			};
//			actions.add(action);
//		}
	}

	public SpBeanRule name(String name) {
		this.name = name;
		return this;
	}

	public SpBeanRule description(String description) {
		this.description = description;
		return this;
	}

	public SpBeanRule priority(int priority) {
		this.priority = priority;
		return this;
	}

	@Override
	public boolean evaluate(Facts facts) {
		return condition.evaluate(facts);
	}

	@Override
	public void execute(Facts facts) throws Exception {
		for (Action action : actions) {
			action.execute(facts);
		}
	}

	private List<Object> getActualParameters(Method method, Facts facts) {
		List<Object> actualParameters = new ArrayList<>();
//		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//		for (Annotation[] annotations : parameterAnnotations) {
//			if (annotations.length == 1) {
//				String factName = ((Fact) (annotations[0])).value(); // validated upfront.
//				Object fact = facts.get(factName);
//				if (fact == null && !facts.asMap().containsKey(factName)) {
//					throw new NoSuchFactException(format("No fact named '%s' found in known facts: %n%s", factName, facts),
//							factName);
//				}
//				actualParameters.add(fact);
//			} else {
//				actualParameters.add(facts); // validated upfront, there may be only one parameter not annotated and which is
//																			// of type Facts.class
//			}
//		}
		return actualParameters;
	}
}
