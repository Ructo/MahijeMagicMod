package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.actions.EasyModalChoiceAction;
import code.cards.democards.complex.InlinePowerDemo;
import code.powers.DexFixation;
import code.powers.StrFixation;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;
import static code.util.Wiz.atb;

@NoPools
@NoCompendium
public class HyperFocus extends AbstractEasyCard {
    public final static String ID = makeID("HyperFocus");

    public HyperFocus() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Check if the player already has StrFixation or DexFixation and remove them
        if (p.hasPower(StrFixation.POWER_ID)) {
            atb(new RemoveSpecificPowerAction(p, p, StrFixation.POWER_ID));
        }
        if (p.hasPower(DexFixation.POWER_ID)) {
            atb(new RemoveSpecificPowerAction(p, p, DexFixation.POWER_ID));
        }

        // After checking and removing, present the choice
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new EasyModalChoiceCard(
                cardStrings.EXTENDED_DESCRIPTION[0],
                cardStrings.EXTENDED_DESCRIPTION[1],
                () -> applyToSelf(new StrFixation(p, magicNumber))
        ));
        easyCardList.add(new EasyModalChoiceCard(
                cardStrings.EXTENDED_DESCRIPTION[2],
                cardStrings.EXTENDED_DESCRIPTION[3],
                () -> applyToSelf(new DexFixation(p, magicNumber))
        ));

        // Queue the action to present the choices
        atb(new EasyModalChoiceAction(easyCardList));
    }


    public void upp() {
        upgradeBaseCost(0);
    }
}

