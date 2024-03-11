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
    }

    @Override
    public void atStartOfTurn() {
        hasModifiedBuffThisTurn = false;
        hasModifiedDebuffThisTurn = false;
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target != owner) return true;

        boolean isBuff = power.type == PowerType.BUFF;
        boolean isDebuff = power.type == PowerType.DEBUFF;

        if (isBuff && !hasModifiedBuffThisTurn) {
            hasModifiedBuffThisTurn = true;
            power.amount += amount;
            power.updateDescription();
            return true;
        }

        if (isDebuff && !hasModifiedDebuffThisTurn) {
            hasModifiedDebuffThisTurn = true;
            if (power.amount < 0) {
                power.amount -= amount;
            }
            power.updateDescription();
            return true;
        }
        return true;
    }

    @Override
    public void updateDescription() {
        int buffIncreaseAmount = hasModifiedBuffThisTurn ? 0 : amount; // If buff was modified, no more increase this turn
        int debuffIncreaseAmount = hasModifiedDebuffThisTurn ? 0 : amount; // If debuff was modified, no more increase this turn
        description = DESCRIPTIONS[0] + buffIncreaseAmount + DESCRIPTIONS[1] + debuffIncreaseAmount + ".";
    }

}
