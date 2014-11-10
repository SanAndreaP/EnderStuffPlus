package de.sanandrew.mods.enderstuffp.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiEnderPet
    extends GuiScreen
{
    private IEnderPet enderPet;
    private GuiTextField nameTxt;
    private EntityPlayer owner;
    private int yPos;

    public GuiEnderPet(IEnderPet pet, EntityPlayer player) {
        this.enderPet = pet;
        this.owner = player;
        this.allowUserInput = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if( button.id == 4 ) {
            this.writeEntityName();
            this.mc.thePlayer.closeScreen();
            return;
        }

//        ESPModRegistry.sendPacketSrv("enderGuiAction", ((Entity) this.enderPet).getEntityId(), (byte) button.id);
        this.mc.thePlayer.closeScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        int color = this.enderPet.getGuiColor();
        String petName = this.enderPet.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET : this.enderPet.getName();
        String title = SAPUtils.translatePostFormat(this.enderPet.getGuiTitle(), petName);

        this.drawDefaultBackground();

//        if( this.enderPet.getEntity() instanceof EntityEnderMiss ) {
//            title = , petName);
//            color = ;
//        }
//        else if( this.enderPet.getEntity() instanceof EntityEnderAvis ) {
//            title = SAPUtils.translatePostFormat("enderstuffplus.guipet.title.avis", petName);
//            color = 0xFF00FF;
//        }

        this.fontRendererObj.drawString(title, (this.width - this.getRealStringWidth(title)) / 2, this.yPos + 10, color);

        this.drawGradientRect((this.width - 205) / 2 - 5, 0, (this.width - 205) / 2 - 4, this.height / 2, color, 0xFF000000 + color);
        this.drawGradientRect((this.width - 205) / 2 - 5, this.height / 2, (this.width - 205) / 2 - 4, this.height, 0xFF000000 + color, color);
        this.drawGradientRect((this.width + 205) / 2 + 4, 0, (this.width + 205) / 2 + 5, this.height / 2, color, 0xFF000000 + color);
        this.drawGradientRect((this.width + 205) / 2 + 4, this.height / 2, (this.width + 205) / 2 + 5, this.height, 0xFF000000 + color, color);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);

        this.fontRendererObj.drawString(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.name"), (this.width - 200) / 2, 20 + this.yPos, 0xFFFFFF);

        this.nameTxt.drawTextBox();

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    private int getRealStringWidth(String str) {
        String strippedString = str.replaceAll("\247.", "");
        return this.fontRendererObj.getStringWidth(strippedString);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        String name = this.enderPet.getName();
        boolean canMount = this.enderPet.canMount();
        int color = this.enderPet.getGuiColor();

        this.yPos = (this.height - 156) / 2;
        this.nameTxt = new GuiTextField(this.fontRendererObj, (this.width - 198) / 2, this.yPos + 30, 198, 15);

        this.nameTxt.setText(name.isEmpty() ? SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.rename") : name);

//        if( this.enderPet.getEntity() instanceof EntityEnderMiss ) {
//            color = 0xFF9090;
//        }
//        else if( this.enderPet.getEntity() instanceof EntityEnderAvis ) {
//            color = 0xFF00FF;
//        }

        GuiButton mountBtn = new GuiButtonPetGUI(0, (this.width - 200) / 2, this.yPos + 55, 200, 15, SAPUtils.translate("mount"), color).setBGAlpha(128);

//        if( this.enderPet.isSitting() ) {
//            canMount = false;
//        } else if( this.enderPet.getEntity() instanceof EntityEnderAvis && !((EntityEnderAvis) this.enderPet.getEntity()).isSaddled() ) {
//            canMount = false;
//        }

        mountBtn.enabled = canMount;

        this.buttonList.add(mountBtn);

        GuiButton sitStandBtn = new GuiButtonPetGUI(1, (this.width - 200) / 2, this.yPos + 71, 200, 15,
                                             this.enderPet.isSitting() ? SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.standUp")
                                                                       : SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.sit"),
                                             color).setBGAlpha(128);
        this.buttonList.add(sitStandBtn);

        GuiButton stayFollowBtn = new GuiButtonPetGUI(2, (this.width - 200) / 2, this.yPos + 87, 200, 15,
                                             this.enderPet.isFollowing() ? SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.stay")
                                                                         : SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.follow"),
                                             color).setBGAlpha(128);

        stayFollowBtn.enabled = !this.enderPet.isSitting();

        this.buttonList.add(stayFollowBtn);

        GuiButton eggBtn = new GuiButtonPetGUI(3, (this.width - 200) / 2, this.yPos + 103, 200, 15,
                                               SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.putIntoEgg"), color).setBGAlpha(128);
        eggBtn.enabled = this.owner.inventory.hasItemStack(new ItemStack(Items.egg)) || this.owner.capabilities.isCreativeMode;
        this.buttonList.add(eggBtn);

        GuiButton closeBtn = new GuiButtonPetGUI(4, (this.width - 200) / 2, this.yPos + 139, 200, 15,
                                                 SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.close"), 0xA0A0A0).setBGAlpha(128);
        this.buttonList.add(closeBtn);
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        this.nameTxt.textboxKeyTyped(key, keyCode);

        if( (keyCode == 28 || keyCode == 1) && this.nameTxt.isFocused() ) {
            this.nameTxt.setFocused(false);
            this.writeEntityName();

            if( this.nameTxt.getText().isEmpty() ) {
                this.nameTxt.setText(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.rename"));
            }
        } else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
                   && !this.nameTxt.isFocused() ) {
            this.writeEntityName();
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
        super.mouseClicked(mouseX, mouseY, mouseBtn);
        this.nameTxt.mouseClicked(mouseX, mouseY, mouseBtn);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.nameTxt.updateCursorCounter();

        if( this.nameTxt.isFocused()
                && this.nameTxt.getText().equals(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.rename")) ) {
            this.nameTxt.setText("");
        }
    }

    private void writeEntityName() {
        if( this.nameTxt.getText().isEmpty() || this.nameTxt.getText().equals(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".guipet.rename")) ) {
            return;
        }

//        ESPModRegistry.sendPacketSrv("setEnderName", ((Entity) this.enderPet).getEntityId(), this.nameTxt.getText());
    }
}
