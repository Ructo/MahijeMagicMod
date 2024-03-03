package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;

import static code.ModFile.makeID;

public class SupertasterPower extends AbstractEasyPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID("SupertasterPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private boolean hasModifiedBuffThisTurn = false;
    private boolean hasModifiedDebuffThisTurn = false;

    public SupertasterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        hasModifiedBuffThisTurn = false;
        hasModifiedDebuffThisTurn = false;
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target != owner) return true;

        boolean isBuff = power.type == PowerType.BUFF || power instanceof StrengthPower || power instanceof DexterityPower;
        boolean isDebuff = power.type == PowerType.DEBUFF;
        boolean shouldModify = (isBuff && !hasModifiedBuffThisTurn) || (isDebuff && !hasModifiedDebuffThisTurn);

        if (shouldModify) {
            if (isBuff) {
                hasModifiedBuffThisTurn = true;
            } else if (isDebuff) {
                hasModifiedDebuffThisTurn = true;
            }

            // Apply an extra stack for the buff or worsen the debuff.
            if (power instanceof StrengthPower || power instanceof DexterityPower) {
                // Special handling for Strength and Dexterity to allow negative adjustments
                if (power.amount < 0) {
                    return true; // Let the debuff apply normally then modify it
                }
            }

            power.amount += (isDebuff && power.amount < 0) ? -amount : amount; // Worsen debuffs or enhance buffs
            power.updateDescription();
        }

        return true;
    }

    @Override
    public void updateDescription() {
        description = CardCrawlGame.languagePack.getPowerStrings(ID).DESCRIPTIONS[0];
    }

}
