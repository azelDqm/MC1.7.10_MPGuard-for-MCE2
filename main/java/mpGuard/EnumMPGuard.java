package mpGuard;

public enum EnumMPGuard {

	AllLost(0, "AllLost"),
	NoLost(1, "NoLost"),
	ItemLost(2, "ItemLost"),
	RandomLost(3, "RandomLost"),
	RandomItem(4, "RandomItem");

    private final int mode;       //ロストモード
    private final String modeName;       //属性

    private EnumMPGuard(int mode, String modeName)
    {
    	this.mode = mode;
    	this.modeName = modeName;
    }

    public int Mode()
    {
    	return this.mode;
    }

    public String ModeName()
    {
    	return this.modeName;
    }
}
