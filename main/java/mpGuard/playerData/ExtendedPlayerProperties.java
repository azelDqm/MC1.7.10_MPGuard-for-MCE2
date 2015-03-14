package mpGuard.playerData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayerProperties implements IExtendedEntityProperties {
   /* MOD固有の文字列。EntityPlayerに登録時に使用。
   MOD内で複数のIExtendedEntityPropertiesを使う場合は、別の文字列をそれぞれ割り当てること。*/
    public final static String EXT_PROP_NAME = "MCEBankPlayerData";

    private int playerMP;
    private int fukuroSetMP;


    /*EntityPlayerインスタンスから外部保存時の固有文字列を返す
    *1.7ではusername変数が使えないので、コマンド送信時の名前で代用 */
    private static String getSaveKey(EntityPlayer player) {
        return player.getCommandSenderName() + ":" + EXT_PROP_NAME;
    }

    /*EntityPlayerにIExtendedEntityPropertiesを登録。登録文字列はMOD固有のものを割り当てること*/
    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayerProperties());
    }
    /*IExtendedEntityPropertiesをEntityPlayerインスタンスから取得する*/
    public static ExtendedPlayerProperties get(EntityPlayer player) {
        return (ExtendedPlayerProperties)player.getExtendedProperties(EXT_PROP_NAME);
    }


    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("playerMP", this.playerMP);
        nbt.setInteger("fukuroSetMP", this.fukuroSetMP);

        compound.setTag(EXT_PROP_NAME, nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);

        this.playerMP = nbt.getInteger("playerMP");
        this.fukuroSetMP = nbt.getInteger("fukuroSetMP");

    }

    @Override
    /*初期化メソッド。今のところ使う必要はない。*/
    public void init(Entity entity, World world) {}

    /*ServerのIExtendedEntityPropertiesを読み込んで、Clientに送信するメソッド*/
    public void loadProxyData(EntityPlayer player) {
        ExtendedPlayerProperties playerData = ExtendedPlayerProperties.get(player);
        NBTTagCompound savedData = PlayerDataHandler.getEntityData(getSaveKey(player));
        if (savedData != null) { playerData.loadNBTData(savedData); }
        PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(player), (EntityPlayerMP)player);
    }

    /*以降、各変数のGetterおよびSetter。
    * 使い方としては、EntityPlayerのインスタンスが取得できるメソッド内で、
    * ExtendedPlayerProperties.get(playerインスタンス).setSampleInt(sample)
    * と呼び出す。*/
    public int getPlayerMP() {
        return playerMP;
    }
    public void setPlayerMP(int par1) {
        this.playerMP = par1;
    }

    public int getFukuroSetMP() {
        return fukuroSetMP;
    }
    public void setFukuroSetMP(int par1) {
        this.fukuroSetMP = par1;
    }

}