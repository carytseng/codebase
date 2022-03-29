package cn.cary.codebase.easyrule.listener;

import cn.cary.codebase.easyrule.RuleStatus;
import cn.cary.codebase.easyrule.entity.RuleInstance;
import cn.cary.codebase.easyrule.mapper.RuleInstanceMapper;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
* @author rwe
* @version 创建时间：2021年5月18日 下午4:13:58
* 类说明
*/

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RuleExecuteListener implements RuleListener {
	
	@Autowired
	RuleInstanceMapper ruleInstanceMapper;
	
	private Long packageInstanceId;
	
	private RuleInstance instance;

	
	@Override
	public boolean beforeEvaluate(Rule rule, Facts facts) {
		RuleInstance instance = new RuleInstance();
		Long packgeInstanceId = facts.get(RulePackageListener.PACKAGE_INSTANCE_ID_FACT_KEY);
		instance.setPackageInstanceId(packgeInstanceId);
		instance.setName(rule.getName());
		instance.setDescription(rule.getDescription());
		instance.setPriority(rule.getPriority());
		instance.setStatus(RuleStatus.START);
		instance.setInput(facts.asMap());
		instance.setStartTime(new Date());
		
		ruleInstanceMapper.insert(instance);
		this.instance = instance;
		return true;
	}

	@Override
	public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
		if(instance != null && !evaluationResult) {
			instance.setStatus(RuleStatus.NEEDNOT);
			ruleInstanceMapper.updateById(instance);
		}
	}

	@Override
	public void onEvaluationError(Rule rule, Facts facts, Exception exception) {
		endRule(rule, facts, exception);
	}

	@Override
	public void beforeExecute(Rule rule, Facts facts) {
		if (instance != null) {
			instance.setStatus(RuleStatus.PROCESSING);
			ruleInstanceMapper.updateById(instance);
		}
	}

	@Override
	public void onSuccess(Rule rule, Facts facts) {
		endRule(rule, facts, null);
	}

	@Override
	public void onFailure(Rule rule, Facts facts, Exception exception) {
		endRule(rule,facts, exception);
	}

	
	private void endRule(Rule rule, Facts facts, Exception exception) {
		if (instance != null) {
			if(exception != null) {
				instance.setStatus(RuleStatus.FAILED);
			}
			else {
				instance.setStatus(RuleStatus.FINISHED);
			}
			instance.setEndTime(new Date());
			instance.setOutput(facts.asMap());
			ruleInstanceMapper.updateById(instance);
		}
	}

	public Long getPackageInstanceId() {
		return packageInstanceId;
	}

	public void setPackageInstanceId(Long packageInstanceId) {
		this.packageInstanceId = packageInstanceId;
	}
	
	
}
