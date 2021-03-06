package mpGuard.playerData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessagePlayerProperties implements IMessage {

    public NBTTagCompound data;

    public MessagePlayerProperties(){}

    public MessagePlayerProperties(EntityPlayer entityPlayer) {
        this.data = new NBTTagCompound();
        //EntityPlayerからIExtendedEntityPropertiesを取得。
        ExtendedPlayerProperties.get(entityPlayer).saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }
}