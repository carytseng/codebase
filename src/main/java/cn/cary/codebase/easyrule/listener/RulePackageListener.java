package cn.cary.codebase.easyrule.listener;

import cn.cary.codebase.easyrule.RuleStatus;
import cn.cary.codebase.easyrule.entity.RulePackage;
import cn.cary.codebase.easyrule.entity.RulePackageInstance;
import cn.cary.codebase.easyrule.mapper.RulePackageInstanceMapper;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
* @author rwe
* @version 创建时间：2021年5月18日 上午8:36:38
* 类说明
*/

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RulePackageListener implements RulesEngineListener {
	
	public final static String PACKAGE_INSTANCE_ID_FACT_KEY = "__packageInstanceId";

	@Autowired
	RulePackageInstanceMapper rulePackageInstanceMapper;
	
	private String sourceId;
	
	private RulePackage pack;
	
	private RulePackageInstance ins = null;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public RulePackage getPack() {
		return pack;
	}

	public void setPack(RulePackage pack) {
		this.pack = pack;
	}

	@Override
	public void beforeEvaluate(Rules rules, Facts facts) {
		RulePackageInstance instance = new RulePackageInstance();
		instance.setCode(pack.getCode());
		instance.setName(pack.getName());
		instance.setDescription(pack.getDescription());
		instance.setRulePriorityThreshold(pack.getRulePriorityThreshold());
		instance.setSkipOnFirstAppliedRule(pack.getSkipOnFirstAppliedRule());
		instance.setSkipOnFirstFailedRule(pack.getSkipOnFirstFailedRule());
		instance.setSkipOnFirstNonTriggeredRule(pack.getSkipOnFirstNonTriggeredRule());
		instance.setStatus(RuleStatus.PROCESSING);
		instance.setStartTime(new Date());
		instance.setSourceId(sourceId);
		instance.setInput(facts.asMap());
		rulePackageInstanceMapper.insert(instance);
		facts.put(PACKAGE_INSTANCE_ID_FACT_KEY, instance.getId());
		ins = instance;
	}

	@Override
	public void afterExecute(Rules rules, Facts facts) {
		if (ins != null) {
			ins.setStatus(RuleStatus.FINISHED);
			ins.setEndTime(new Date());
			ins.setOutput(facts.asMap());
			rulePackageInstanceMapper.updateById(ins);
		}
	}
}
