package sample.application.calculator2;

public class Ac extends AbstractLogic {

	@Override
	public void doSomething(Calculator2Activity ca) {
		ca.strTemp = "";
		ca.strResult = "0";
		ca.operator = 0;

		//ca.showNumber(ca.strTemp);

	}

}
