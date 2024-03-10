package code.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import code.cards.cardvars.CustomTags;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static code.ModFile.makeID;

public class RefractedPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID("RefractedPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private boolean playedLasersThisTurn = false;

    public RefractedPower(AbstractCreature owner) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, 1);
    }


    @Override
    public void atStartOfTurn() {
        this.playedLasersThisTurn = false; // Reset flag at start of turn
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!playedLasersThisTurn) {
            // Remove Blur and Thorns if no Lasers cards were played
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, BlurPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ThornsPower.POWER_ID));
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.hasTag(CustomTags.LASERS)) {
            playedLasersThisTurn = true;
            // Apply Blur and Thorns when a Lasers card is played
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new BlurPower(owner, 0), this.amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ThornsPower(owner, 0), this.amount));
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
