package code.cards.tempcards;

import code.cards.AbstractEasyCard;
import code.cards.cardvars.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import static code.ModFile.makeID;

public class LaserEyes extends AbstractEasyCard {
    public static final String ID = makeID("LaserEyes");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public LaserEyes() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 16; // Base damage for the card
        this.exhaust = true; // The card exhausts after use
        tags.add(CustomTags.LASERS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dexterityBonus = getDexterityBonus(p);
        updateDescription(dexterityBonus);
        addToBot(new DamageAction(m, new DamageInfo(p, baseDamage + dexterityBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new DamageAction(m, new DamageInfo(p, baseDamage + dexterityBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LaserEyes();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    private int getDexterityBonus(AbstractPlayer player) {
        AbstractPower dexterityPower = player.getPower(DexterityPower.POWER_ID);
        return dexterityPower != null ? dexterityPower.amount : 0;
    }

    private void updateDescription(int dexterityBonus) {
        this.rawDescription = cardStrings.DESCRIPTION
                .replace("!D!", String.valueOf(baseDamage + dexterityBonus));
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        int dexterityBonus = getDexterityBonus(AbstractDungeon.player);
        // Temporarily increase damage for the power application
        this.baseDamage += dexterityBonus;
        super.applyPowers();
        // Revert the temporary damage increase after powers are applied
        this.baseDamage -= dexterityBonus;

        // Ensure the description is updated with the latest values
        updateDescription(dexterityBonus);
    }
}
