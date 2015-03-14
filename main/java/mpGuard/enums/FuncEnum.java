package mpGuard.enums;

public class FuncEnum {

	public FuncEnum(){}

    public EnumFukuroValue getFukuroValueFromId(int par1)
    {
    	EnumFukuroValue[] box = EnumFukuroValue.values();

    	for(int cnt = 0; cnt < box.length; cnt++)
    	{
    		if(box[cnt].getId() == par1)
    		{
    			return box[cnt];
    		}
    	}

    	return EnumFukuroValue.VALUE1;
    }

}
