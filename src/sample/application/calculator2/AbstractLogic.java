package sample.application.calculator2;

import sample.application.calculator2.Calculator2Activity;
import sample.application.calculator2.FunctionLogic;


public abstract class AbstractLogic implements FunctionLogic {

	@Override
	public void doFunction(Calculator2Activity ca) {
		// TODO 自動生成されたメソッド・スタブ
		//this.beforeDoSomething(ca);
		this.doSomething(ca);
		ca.showNumber(ca.strTemp);
		//this.afterDoSomething3(ca);
	}

	//public abstract void beforeDoSomething(CalculatorActivity ca);
	public abstract void doSomething(Calculator2Activity ca);
	//public abstract void afterDoSomething3(CalculatorActivity ca);

}
