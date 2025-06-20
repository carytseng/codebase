package cn.oj.codebase.easyrule.listener;

import cn.oj.codebase.easyrule.RuleProperties;
import cn.oj.codebase.easyrule.entity.RuleEntity;
import cn.oj.codebase.easyrule.mapper.RuleMapper;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;

/**
* @author rwe
* @version 创建时间：2021年4月28日 下午4:04:10
* 读取注解到数据库
*/

@Repository
public class RuleAnnotationRefreshListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	RuleMapper ruleMapper;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(Rule.class);
		for(Map.Entry<String,Object> entry : beans.entrySet()) {
			String key = entry.getKey();
			Object o = entry.getValue();
			Class<?> clazz = o.getClass();
			Rule rule = clazz.getAnnotation(Rule.class);
			String ruleName = rule.name();
			String ruleDesc = rule.description();
			int rulePriority = rule.priority();
			
			
			RuleEntity entity = ruleMapper.selectById(key);
			if(entity != null) {
				entity.setName(ruleName);
				entity.setDescription(ruleDesc);
				entity.setPriority(rulePriority);
				entity.setAction(Arrays.asList(key));
				setProperties(entity, clazz);
				ruleMapper.updateById(entity);
			}
			else {
				entity = new RuleEntity(key, ruleName, ruleDesc, rulePriority, RuleEntity.RuleType.CODE);
				entity.setAction(Arrays.asList(key));
				setProperties(entity, clazz);
				ruleMapper.insert(entity);
			}
			
		}
		
	}

	private void setProperties(RuleEntity entity, Class<?> clazz) {
		if(clazz.isAnnotationPresent(RuleProperties.class)) {
			RuleProperties properties = clazz.getAnnotation(RuleProperties.class);
			entity.setInput(properties.input());
			entity.setOutput(properties.output());
			entity.setTags(String.join(" ", properties.tags()));
		}
	}
}
