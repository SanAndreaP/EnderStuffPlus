package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

        ESPModRegistry.sendPacketSrv("enderGuiAction", ((Entity) this.enderPet).entityId, (byte) button.id);
        this.mc.thePlayer.closeScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        String title = "";
        int color = 0xFFFFFF;
        String petName = this.enderPet.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET
                                                           : this.enderPet.getName();

        this.drawDefaultBackground();

        if( this.enderPet.getEntity() instanceof EntityEnderMiss ) {
            title = SAPUtils.getTranslated("enderstuffplus.guipet.title.miss", petName);
            color = 0xFF9090;
        } else if( this.enderPet.getEntity() instanceof EntityEnderAvis ) {
            title = SAPUtils.getTranslated("enderstuffplus.guipet.title.avis", petName);
            color = 0xFF00FF;
        }

        this.fontRenderer.drawString(title, (this.width - this.getRealStringWidth(title)) / 2, this.yPos + 10, color);

        this.drawGradientRect((this.width - 205) / 2 - 5, 0, (this.width - 205) / 2 - 4, this.height / 2,
                              0x00000000 + color, 0xFF000000 + color);
        this.drawGradientRect((this.width - 205) / 2 - 5, this.height / 2, (this.width - 205) / 2 - 4, this.height,
                              0xFF000000 + color, 0x00000000 + color);
        this.drawGradientRect((this.width + 205) / 2 + 4, 0, (this.width + 205) / 2 + 5, this.height / 2,
                              0x00000000 + color, 0xFF000000 + color);
        this.drawGradientRect((this.width + 205) / 2 + 4, this.height / 2, (this.width + 205) / 2 + 5, this.height,
                              0xFF000000 + color, 0x00000000 + color);
        GL11.glColor4f(1F, 1F, 1F, 0F);

        this.fontRenderer.drawString(SAPUtils.getTranslated("enderstuffplus.guipet.name"),
                                     (this.width - 200) / 2, 20 + this.yPos, 0xFFFFFF);

        this.nameTxt.drawTextBox();

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    private int getRealStringWidth(String str) {
        String strippedString = str.replaceAll("\247.", "");
        return this.fontRenderer.getStringWidth(strippedString);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        String name = this.enderPet.getName();
        boolean canMount = true;
        int color = 0xFFFFFF;

        this.yPos = (this.height - 156) / 2;
        this.nameTxt = new GuiTextField(this.fontRenderer, (this.width - 198) / 2, this.yPos + 30, 198, 15);

        this.nameTxt.setText(name.isEmpty() ? SAPUtils.getTranslated("enderstuffplus.guipet.rename") : name);

        if( this.enderPet.getEntity() instanceof EntityEnderMiss ) {
            color = 0xFF9090;
        } else if( this.enderPet.getEntity() instanceof EntityEnderAvis ) {
            color = 0xFF00FF;
        }

        GuiButton mountBtn = new GuiButtonPetGUI(0, (this.width - 200) / 2, this.yPos + 55, 200, 15,
                                                 SAPUtils.getTranslated("mount"), color).setBGAlpha(128);

        if( this.enderPet.isSitting() ) {
            canMount = false;
        } else if( this.enderPet.getEntity() instanceof EntityEnderAvis
                   && !((EntityEnderAvis) this.enderPet.getEntity()).isSaddled() ) {
            canMount = false;
        }

        mountBtn.enabled = canMount;

        this.buttonList.add(mountBtn);

        GuiButton sitStandBtn = new GuiButtonPetGUI(1, (this.width - 200) / 2, this.yPos + 71, 200, 15,
                                             this.enderPet.isSitting() ? SAPUtils.getTranslated("enderstuffplus.guipet.standUp")
                                                                       : SAPUtils.getTranslated("enderstuffplus.guipet.sit"),
                                             color).setBGAlpha(128);
        this.buttonList.add(sitStandBtn);

        GuiButton stayFollowBtn = new GuiButtonPetGUI(2, (this.width - 200) / 2, this.yPos + 87, 200, 15,
                                             this.enderPet.isFollowing() ? SAPUtils.getTranslated("enderstuffplus.guipet.stay")
                                                                         : SAPUtils.getTranslated("enderstuffplus.guipet.follow"),
                                             color).setBGAlpha(128);

        stayFollowBtn.enabled = !this.enderPet.isSitting();

        this.buttonList.add(stayFollowBtn);

        GuiButton eggBtn = new GuiButtonPetGUI(3, (this.width - 200) / 2, this.yPos + 103, 200, 15,
                                               SAPUtils.getTranslated("enderstuffplus.guipet.putIntoEgg"), color).setBGAlpha(128);
        eggBtn.enabled = this.owner.inventory.hasItemStack(new ItemStack(Item.egg)) || this.owner.capabilities.isCreativeMode;
        this.buttonList.add(eggBtn);

        GuiButton closeBtn = new GuiButtonPetGUI(4, (this.width - 200) / 2, this.yPos + 139, 200, 15,
                                                 SAPUtils.getTranslated("enderstuffplus.guipet.close"), 0xA0A0A0).setBGAlpha(128);
        this.buttonList.add(closeBtn);
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        this.nameTxt.textboxKeyTyped(key, keyCode);

        if( (keyCode == 28 || keyCode == 1) && this.nameTxt.isFocused() ) {
            this.nameTxt.setFocused(false);
            this.writeEntityName();

            if( this.nameTxt.getText().isEmpty() ) {
                this.nameTxt.setText(SAPUtils.getTranslated("enderstuffplus.guipet.rename"));
            }
        } else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode)
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
                && this.nameTxt.getText().equals(SAPUtils.getTranslated("enderstuffplus.guipet.rename")) ) {
            this.nameTxt.setText("");
        }
    }

    private void writeEntityName() {
        if( this.nameTxt.getText().isEmpty()
                || this.nameTxt.getText().equals(SAPUtils.getTranslated("enderstuffplus.guipet.rename")) ) {
            return;
        }

        ESPModRegistry.sendPacketSrv("setEnderName", ((Entity) this.enderPet).entityId, this.nameTxt.getText());
    }
}
