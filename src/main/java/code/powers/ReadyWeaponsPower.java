package code.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import static code.ModFile.makeID;

public class ReadyWeaponsPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID("ReadyWeaponsPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public ReadyWeaponsPower(AbstractCreature owner) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, -1);

    }

    @Override
    public void updateDescription() {
        this.description = "Weapons Ready. Hyper Electro Beam will consume this power.";
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (owner.hasPower(this.ID)) {
                // Add 6 Void cards to the draw pile
                addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 6, true, true));
                // Remove this power
                addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
            }
        }
    }
}