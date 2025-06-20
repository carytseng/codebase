package cn.oj.codebase.easyrule.service;

import cn.oj.codebase.easyrule.PackageWorker;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

/**
* @author rwe
* @version 创建时间：2021年5月18日 上午12:30:01
* 类说明
*/

@Service
public class RuleExecutor {

	public String excute(PackageWorker worker) {
		worker.getEngine().fire(worker.getRules(), worker.getFacts());
		return JSON.toJSONString(worker.getFacts().asMap());
	}
}
