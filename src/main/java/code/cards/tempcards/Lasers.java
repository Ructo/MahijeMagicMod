package code.cards.tempcards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;

public class Lasers extends AbstractEasyCard {
    public static final String ID = makeID("Lasers");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Lasers() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        baseDamage = 2; // Base damage could be adjusted as needed
        baseMagicNumber = magicNumber = 2; // Number of times the card hits
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(null); // Ensures damage is recalculated with Dexterity bonus before use
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int dexterityAmount = 0;
        AbstractPower dexterityPower = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if (dexterityPower != null) {
            dexterityAmount = dexterityPower.amount;
        }

        // Temporarily negate Strength effect for this damage calculation if needed
        int originalStrength = 0;
        AbstractPower strengthPower = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strengthPower != null) {
            originalStrength = strengthPower.amount;
            strengthPower.amount = 0; // Temporarily set player's Strength to 0
        }

        this.baseDamage += dexterityAmount; // Apply Dexterity bonus
        super.calculateCardDamage(mo); // Recalculate damage with updated baseDamage and no Strength bonus
        this.baseDamage -= dexterityAmount; // Revert baseDamage back to original

        if (strengthPower != null) {
            strengthPower.amount = originalStrength; // Restore original Strength
        }
    }

    public AbstractCard makeCopy() {
        return new Lasers();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // Increases the number of hits
            // Optionally, update the upgrade effect here
            this.rawDescription = "Upgrade description here."; // Optionally update the description for the upgraded card
            initializeDescription();
        }
    }
}
