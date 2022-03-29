package cn.cary.codebase.easyrule.service;

import cn.cary.codebase.easyrule.PackageWorker;
import cn.cary.codebase.easyrule.SpBeanRule;
import cn.cary.codebase.easyrule.entity.RuleEntity;
import cn.cary.codebase.easyrule.entity.RulePackage;
import cn.cary.codebase.easyrule.listener.RuleExecuteListener;
import cn.cary.codebase.easyrule.listener.RulePackageListener;
import cn.cary.codebase.easyrule.mapper.RuleMapper;
import cn.cary.codebase.easyrule.mapper.RulePackageMapper;
import cn.cary.codebase.util.SpringContextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.spel.SpELRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rwe
 * @version 创建时间：2021年4月27日 下午1:53:07 类说明
 */

@Service
public class RuleService {

	@Autowired
	RuleMapper ruleMapper;

	@Autowired
	RulePackageMapper rulePackageMapper;

	
	@Autowired
	RuleExecutor ruleExcutor;
	
	@Autowired
	RulePackageListener rulePackageListener;
	
	@Autowired
	RuleExecuteListener ruleExecuteListener;

	public String submit(String sourceId, String packageCode, String json) throws ServerException {
		PackageWorker worker = getWorker(sourceId, packageCode, json);
		return ruleExcutor.excute(worker);
	}

	private PackageWorker getWorker(String sourceId, String packageCode, String json) throws ServerException {
		RulePackage pack = rulePackageMapper.selectById(packageCode);
		if (pack == null) {
		//	throw new ApiException();
		}
		RulesEngineParameters parameters = new RulesEngineParameters().priorityThreshold(pack.getRulePriorityThreshold())
				.skipOnFirstAppliedRule(pack.getSkipOnFirstAppliedRule()).skipOnFirstFailedRule(pack.getSkipOnFirstFailedRule())
				.skipOnFirstNonTriggeredRule(pack.getSkipOnFirstNonTriggeredRule());

		DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
		rulePackageListener.setPack(pack);
		rulePackageListener.setSourceId(sourceId);
		rulesEngine.registerRulesEngineListener(rulePackageListener);
		rulesEngine.registerRuleListener(ruleExecuteListener);
		
		JSONObject o = JSON.parseObject(json);
		Facts facts = new Facts();
		for (String key : o.keySet()) {
			facts.put(key, o.get(key));
		}

		Rules rules = new Rules();
		List<String> tactics = pack.getTactics();
		if (tactics == null || tactics.size() == 0) {
			throw new ServerException("规则集中没有定义规则");
		}
		List<RuleEntity> ruleEntities = ruleMapper
				.selectList(Wrappers.<RuleEntity>lambdaQuery().in(RuleEntity::getCode, tactics));
		List<OrderedRule> orders = ruleEntities.stream()
				.map(entity -> new OrderedRule(entity, tactics.indexOf(entity.getCode()))).collect(Collectors.toList());
		List<Rule> ruleList = orders.stream().map(order -> {
			RuleEntity entity = order.getEntity();
			int priority = order.getPriority();
//				String code = entity.getCode();
			String name = entity.getName();
			String desc = entity.getDescription();

			String condition = entity.getExpression();
			List<String> actions = entity.getAction();
			if (actions == null || actions.size() == 0) {
				return EMPTY_RULE;
			}
			RuleEntity.RuleType type = entity.getType();
			Rule r = EMPTY_RULE;
			switch (type) {
			case CODE:
				Object bean = SpringContextUtils.getBeanById(actions.get(0));
				SpBeanRule beanRule = new SpBeanRule(bean).name(name).description(desc).priority(priority);
				return beanRule;
			case SPEL:
				SpELRule spel = new SpELRule().name(name).description(desc).priority(priority).when(condition);
				for (String action : actions) {
					spel = spel.then(action);
				}
				return spel;
			case MVEL:
				MVELRule mvel = new MVELRule().name(name).description(desc).priority(priority).when(condition);
				for (String action : actions) {
					mvel = mvel.then(action);
				}
				return mvel;
			case GROUP:

			default:

			}
			return r;
		}).collect(Collectors.toList());
		rules.register(ruleList.toArray());
		
		return new PackageWorker(rulesEngine, facts, rules);
	}

	private static final Rule EMPTY_RULE = new Rule() {

		@Override
		public int compareTo(Rule o) {
			return 0;
		}

		@Override
		public boolean evaluate(Facts facts) {
			return true;
		}

		@Override
		public void execute(Facts facts) throws Exception {

		}

	};

	private class OrderedRule {
		private RuleEntity entity;
		private int priority;

		public OrderedRule(RuleEntity entity, int priority) {
			this.entity = entity;
			this.priority = priority;
		}

		public RuleEntity getEntity() {
			return entity;
		}

		public int getPriority() {
			return priority;
		}

	}
}
