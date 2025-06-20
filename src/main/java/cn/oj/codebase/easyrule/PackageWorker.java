package cn.oj.codebase.easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;

/**
 * @author rwe
 * @version 创建时间：2021年5月18日 上午12:14:49 类说明
 */

public class PackageWorker {

	private RulesEngine engine;

	private Facts facts;

	private Rules rules;

	public PackageWorker(RulesEngine engine, Facts facts, Rules rules) {
		this.engine = engine;
		this.facts = facts;
		this.rules = rules;
	}

	public RulesEngine getEngine() {
		return engine;
	}

	public Facts getFacts() {
		return facts;
	}

	public Rules getRules() {
		return rules;
	}

}
