package mpGuard.enums;

public enum EnumFukuroValue {

	VALUE1(1, 1),
	VALUE10(2, 10),
	VALUE100(3, 100),
	VALUE1000(4, 1000),
	VALUE10000(5, 10000),
	VALUE100000(6, 100000),
	VALUE1000000(7, 1000000),
	VALUE10000000(8, 10000000);

    private final int id;       //ロストモード
    private final int setValue;       //属性

    private EnumFukuroValue(int mode, int setValue)
    {
    	this.id = mode;
    	this.setValue = setValue;
    }

    public int getId()
    {
    	return this.id;
    }

    public int getValue()
    {
    	return this.setValue;
    }

}